import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import models.CustomerRequest;
import models.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by chrisjluc on 14-11-05.
 */
public class FileParser {

    public static char[][] parseMapToChar2DArray(String path){
        List<char[]> charRowList = new ArrayList<char[]>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                charRowList.add(line.toCharArray());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        int nRows = charRowList.size();
        int nCols = charRowList.get(0).length;
        char[][] ret = charRowList.toArray(new char[nRows][nCols]);
        return ret;
    }

    public static List<CustomerRequest> parseCustomerRequestsToList(String path){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(new FileReader(path));
        }catch(Exception e){
            e.printStackTrace();
        }

        List<CustomerRequest> requests = new ArrayList<CustomerRequest>();


        for (Object o : (JSONArray) obj.get("requests")) {
            JSONObject customerRequest = (JSONObject) o;

            JSONObject pickupObj = (JSONObject) customerRequest.get("pickup");
            Point pickup = new Point(((Long) pickupObj.get("x")).intValue(), ((Long) pickupObj.get("y")).intValue());

            JSONObject dropoffObj = (JSONObject) customerRequest.get("dropoff");
            Point dropoff = new Point(((Long) dropoffObj.get("x")).intValue(), ((Long) dropoffObj.get("y")).intValue());

            double customerFee = (Double) customerRequest.get("customerFee");
            int id = ((Long) customerRequest.get("id")).intValue();
            requests.add(new CustomerRequest(id, customerFee, pickup, dropoff));
        }
        return requests;
    }

    public static Point parseTaxiStartLocationToPoint(String path){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(new FileReader(path));
        }catch(Exception e){
            e.printStackTrace();
        }
        if(obj == null)
            return null;
        JSONObject start = (JSONObject) obj.get("taxiHeadquarter");
        return new Point(((Long) start.get("x")).intValue(), ((Long) start.get("y")).intValue());
    }
}
