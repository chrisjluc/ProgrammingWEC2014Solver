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
        CityMap cm = new CityMap(map);
    }
}
