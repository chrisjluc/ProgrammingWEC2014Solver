import java.util.HashMap;
import java.util.Stack;

/**
 * Created by chrisjluc on 2014-10-26.
 */
public class CityMap {

    HashMap<NodeKey, Node> nodesByNodeKey = new HashMap<NodeKey, Node>();
    HashMap<Character, Node> specialNodes = new HashMap<Character, Node>();
    Stack<Node> nodesToVisit = new Stack<Node>();

    char[][] data;

    public int findShortestDistance(Character startChar, Character finishChar){
        if(!specialNodes.containsKey(startChar) || !specialNodes.containsKey(finishChar))
            return -1;
        Node start = specialNodes.get(startChar);
        Node finish = specialNodes.get(finishChar);

        // Some Shortest distance graph traversal - Dijkstra

        return -1;
    }

    /**
     * Construct graph from 2D char array
     * Use depth-first traversal via stack to link nodes
     * @param data
     */
    public CityMap(char[][] data) {
        this.data = data;
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
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                char c = data[i][j];
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
     * Wrapper to get value from data
     * @param key
     * @return
     */
    private char getCharFromData(NodeKey key){
        return data[key.row][key.col];
    }

    /**
     * Wrapper to create new node
     * @param key
     * @return
     */
    private Node createNode(NodeKey key){
        return new Node(data[key.row][key.col], key.row, key.col);
    }
}
