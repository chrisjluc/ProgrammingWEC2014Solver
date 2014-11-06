import models.Node;
import models.Point;

import java.util.*;

/**
 * Created by chrisjluc on 2014-10-26.
 */
public class CityMap {

    HashMap<Point, Node> nodesByNodeKey = new HashMap<Point, Node>();
    Stack<Node> nodesToVisit = new Stack<Node>();
    char[][] mapData;

    /**
     * Find shortest path between two points
     * @param startPoint
     * @param endPoint
     * @return
     */
    public List<Point> findShortestPath(Point startPoint, final Point endPoint){
        return findShortestPath(startPoint, new ArrayList<Point>(Arrays.asList(endPoint)));
    }
    /**
     * Since the map can be treated as an unweighted graph
     * Use breadth traversal to find the shortest path to the closest given List of points
     * @param startPoint
     * @param endPoints
     * @return
     */
    public List<Point> findShortestPath(Point startPoint, List<Point> endPoints){
        Node start = nodesByNodeKey.get(startPoint);
        if(endPoints.size() == 0)
            return null;

        // Don't have to travel anywhere to reach an endpoint
        if(endPoints.contains(startPoint))
            return new ArrayList<Point>();

        // Convert points to nodes, so they can be found in the map
        List<Node> endNodes = new ArrayList<Node>();
        for(Point p : endPoints)
            endNodes.add(nodesByNodeKey.get(p));


        // Every iteration, add nodes to visit to futureNodesToVisit while iterating through nodesToVisit
        // Breadth search happens within the traversal while loop
        Stack<Node> futureNodesToVisit = new Stack<Node>();
        nodesToVisit = new Stack<Node>();
        nodesToVisit.push(start);

        Node closestNode = null;
        traversal:
        while(true){
            while(!nodesToVisit.isEmpty()) {
                Node currentNode = nodesToVisit.pop();
                for (Node node : currentNode.connectedNodes) {
                    if (node.traversedFrom == null && !futureNodesToVisit.contains(node) && !nodesToVisit.contains(node)) {
                        futureNodesToVisit.push(node);
                        node.traversedFrom = currentNode;
                    }
                    for(Node endNode : endNodes) {
                        if (node.equals(endNode)) {
                            closestNode = endNode;
                            break traversal;
                        }
                    }
                }
            }

            // No more nodes to visit
            if(futureNodesToVisit.isEmpty())
                break;

            nodesToVisit = futureNodesToVisit;
            futureNodesToVisit = new Stack<Node>();
        }

        // Create the path from start to end by traversing the tree in reverse
        List<Point> pointsToTraverse = new ArrayList<Point>();
        Node currentNode = closestNode;
        while(currentNode != start){
            pointsToTraverse.add(0,currentNode.p);
            currentNode = currentNode.traversedFrom;
        }

        resetNodesVisited();
        return pointsToTraverse;
    }

    private void resetNodesVisited(){
        for(Node n : nodesByNodeKey.values())
            n.traversedFrom = null;
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
            Point currentPoint = currentNode.p;
            // Check right
            Point key = new Point(currentPoint.x + 1, currentPoint.y);
            linkNodes(currentNode, key);

            // Check left
            key = new Point(currentPoint.x - 1, currentPoint.y);
            linkNodes(currentNode, key);

            // Check up
            key = new Point(currentPoint.x, currentPoint.y + 1);
            linkNodes(currentNode, key);

            // Check down
            key = new Point(currentPoint.x, currentPoint.y - 1);
            linkNodes(currentNode, key);
        }
        nodesToVisit = null;
    }

    private void linkNodes(Node currentNode, Point key){
        Node candidate;
        if(nodesByNodeKey.containsKey(key))
            candidate = nodesByNodeKey.get(key);
        else{
            char candidateChar = getCharFromData(key);
            if(candidateChar == 'X')
                return;
            else
                candidate = createNode(key);
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
                Node first = new Node(c,new Point(j,i));
                nodesToVisit.push(first);
                nodesByNodeKey.put(new Point(j, i), first);
                return;
            }
        }
    }

    /**
     * Wrapper to get value from mapData
     * @param p
     * @return
     */
    private char getCharFromData(Point p){
        return mapData[p.y][p.x];
    }

    /**
     * Wrapper to create new node
     * @param p
     * @return
     */
    private Node createNode(Point p){
        return new Node(mapData[p.y][p.x], p);
    }
}
