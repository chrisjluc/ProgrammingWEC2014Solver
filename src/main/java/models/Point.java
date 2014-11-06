package models;

/**
 * Created by chrisjluc on 14-11-05.
 */
public class Point {
    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean equals(Object o){
        if(!(o instanceof Point))
            return false;
        Point p = (Point) o;
        if(p.x == x && p.y == y)
            return true;
        return false;
    }

    public int hashCode(){
        return x + 10991*y*x;
    }
}
