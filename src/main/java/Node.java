import java.util.HashSet;

/**
 * Created by chrisjluc on 2014-10-26.
 */
public class Node {

    int row;
    int col;
    char c;
    HashSet<Node> connectedNodes;
    boolean visited;

    public Node(char c, int row, int col) {
        this.col = col;
        this.c = c;
        this.row = row;
        connectedNodes = new HashSet<Node>();
    }

    public boolean equals(Object o){
        if(o instanceof Node){}
        else
            return false;

        Node n = (Node) o;
        if(c == n.c && row == n.row && col == n.col)
            return true;
        return false;

    }

    public int hashCode(){
        return ((int) c) + 389^row + 991^col;
    }
}
