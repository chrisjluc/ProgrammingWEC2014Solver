/**
 * Created by chrisjluc on 2014-10-26.
 */
public class NodeKey {

    int row;
    int col;

    public NodeKey(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public boolean equals(Object o){
        if(o instanceof NodeKey){}
        else
            return false;

        NodeKey n = (NodeKey) o;
        if(row == n.row && col == n.col)
            return true;
        return false;

    }

    public int hashCode(){
        return row + 10991*col;
    }
}
