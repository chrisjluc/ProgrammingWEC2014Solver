/**
 * Created by chrisjluc on 2014-10-26.
 */
public class Solver {

    public static void main(String... s){
        char[][] map = {{'X','X','X','X','X','X','X','X'},
                {'X','A',' ',' ',' ',' ','a','X'},
                {'X','X',' ','X','X','b','X','X'},
                {'X','X',' ','X','X',' ','X','X'},
                {'X','T',' ','X','X',' ','X','X'},
                {'X',' ',' ','X','X',' ','X','X'},
                {'X',' ',' ',' ',' ',' ','B','X'},
                {'X','X','X','X','X','X','X','X'}};

        char[][] requests = {{'A','a'},{'B','b'}};

        CityMap cm = new CityMap(map);
        System.out.println(cm.findShortestDistance('a','T'));
        System.out.println(cm.findShortestDistance('a','B'));

    }
}
