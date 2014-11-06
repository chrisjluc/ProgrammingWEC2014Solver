package models;

import java.util.HashSet;

/**
 * Created by chrisjluc on 2014-10-26.
 */
public class Node {


    public Point p;
    public char c;

    public HashSet<Node> connectedNodes;

    /**
     * Used for back-tracking and recreating the path.
     * Don't know what the shortest path is while doing breadth-first search
     */
    public Node traversedFrom = null;

    public Node(char c, Point p) {
        this.c = c;
        this.p = p;
        connectedNodes = new HashSet<Node>();
    }

    public boolean equals(Object o){
        if(o instanceof Node){}
        else
            return false;

        Node n = (Node) o;
        if(c == n.c && p == n.p)
            return true;
        return false;

    }

    public int hashCode(){
        return (((int) c)*3593 + 3583*p.x) * 3597 + 3581*p.y;
    }
}
