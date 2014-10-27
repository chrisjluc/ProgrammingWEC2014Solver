import java.util.HashMap;
import java.util.Stack;

/**
 * Created by chrisjluc on 2014-10-26.
 */
public class CityMap {

    HashMap<NodeKey, Node> nodesByNodeKey = new HashMap<NodeKey, Node>();
    HashMap<Character, Node> specialNodes = new HashMap<Character, Node>();
    Stack<Node> nodesToVisit = new Stack<Node>();
    char[][] mapData;

    /**
     * Since the map can be treated as an unweighted graph
     * Use breadth traversal to find the shortest distance between two paths
     * @param startChar
     * @param endChar
     * @return
     */
    public int findShortestDistance(Character startChar, Character endChar){
        if(!specialNodes.containsKey(startChar) || !specialNodes.containsKey(endChar))
            return -1;
        Node start = specialNodes.get(startChar);
        Node end = specialNodes.get(endChar);

        Stack<Node> futureNodesToVisit = new Stack<Node>();
        nodesToVisit = new Stack<Node>();
        nodesToVisit.push(start);

        int distance = 0;

        traversal:
        while(true){
            distance++;
            while(!nodesToVisit.isEmpty()) {
                Node visitedNode = nodesToVisit.pop();
                visitedNode.visited = true;
                for (Node node : visitedNode.connectedNodes) {
                    if (node.equals(end))
                        break traversal;
                    if (!node.visited && !futureNodesToVisit.contains(node) && !nodesToVisit.contains(node))
                        futureNodesToVisit.push(node);
                }
            }

            if(futureNodesToVisit.isEmpty())
                break;

            nodesToVisit = futureNodesToVisit;
            futureNodesToVisit = new Stack<Node>();
        }
        resetNodesVisited();

        return distance;
    }

    private void resetNodesVisited(){
        for(Node n : nodesByNodeKey.values())
            n.visited = false;
    }

    /**
     * Construct graph from 2D char array
     * Use depth-first traversal via stack to link nodes
     * @param mapData
     */
    public CityMap(char[][] mapData) {
        this.mapData = mapData;
        pushFirstValidNodeToStack();

        while(!nodesToVisit.isEmpty()){
            Node currentNode = nodesToVisit.pop();

            // Check right
            NodeKey key = new NodeKey(currentNode.row, currentNode.col + 1);
            linkNodes(currentNode, key);

            // Check left
            key = new NodeKey(currentNode.row, currentNode.col - 1);
            linkNodes(currentNode, key);

            // Check up
            key = new NodeKey(currentNode.row - 1, currentNode.col);
            linkNodes(currentNode, key);

            // Check down
            key = new NodeKey(currentNode.row + 1, currentNode.col);
            linkNodes(currentNode, key);
        }
        nodesToVisit = null;
    }

    private void linkNodes(Node currentNode, NodeKey key){
        Node candidate;
        if(nodesByNodeKey.containsKey(key))
            candidate = nodesByNodeKey.get(key);
        else{
            char candidateChar = getCharFromData(key);
            if(candidateChar == 'X')
                return;
            else if(candidateChar == ' ')
                candidate = createNode(key);
            else{
                candidate = createNode(key);
                specialNodes.put(candidateChar, candidate);
            }
            nodesByNodeKey.put(key, candidate);
            nodesToVisit.push(candidate);
        }

        if(!currentNode.connectedNodes.contains(candidate))
            currentNode.connectedNodes.add(candidate);

        if(!candidate.connectedNodes.contains(currentNode))
            candidate.connectedNodes.add(currentNode);
    }

    /**
     * Pushes node that's not 'X' to the stack to begin traversal
     */
    private void pushFirstValidNodeToStack(){
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[0].length; j++) {
                char c = mapData[i][j];
                if (c == 'X')
                    continue;
                Node first = new Node(c,i,j);
                nodesToVisit.push(first);
                nodesByNodeKey.put(new NodeKey(i, j), first);
                if (c != ' ')
                    specialNodes.put(c, first);
                return;
            }
        }
    }

    /**
     * Wrapper to get value from mapData
     * @param key
     * @return
     */
    private char getCharFromData(NodeKey key){
        return mapData[key.row][key.col];
    }

    /**
     * Wrapper to create new node
     * @param key
     * @return
     */
    private Node createNode(NodeKey key){
        return new Node(mapData[key.row][key.col], key.row, key.col);
    }
}
