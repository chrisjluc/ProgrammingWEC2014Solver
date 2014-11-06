import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import models.CustomerRequest;
import models.Point;
import models.actions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrisjluc on 14-11-06.
 */
public class GreedyOptimizer {

    public static final String MAP_PATH = "/Users/chrisjluc/Documents/ProgrammingWEC2014Tester/generator/test/map1000_1.txt";
    public static final String CUSTOMER_REQUEST_PATH = "/Users/chrisjluc/Documents/ProgrammingWEC2014Tester/generator/test/requests1000_1.txt";
    public static final String ACTION_JSON_PATH = "/Users/chrisjluc/Documents/ProgrammingWEC2014Tester/generator/test/solver_actions_1000_1.txt";

    private CityMap m_cityMap;
    private CustomerRequestArrayList m_customerRequests;
    private Point m_startLocation;
    private List<Action> m_actions;

    public static void main(String... _args){
        GreedyOptimizer g = new GreedyOptimizer();
        g.optimize();
        g.toJSONStringOfActions();
    }

    public GreedyOptimizer(){
        char[][] map = FileParser.parseMapToChar2DArray(MAP_PATH);
        m_cityMap = new CityMap(map);
        List<CustomerRequest> requests = FileParser.parseCustomerRequestsToList(CUSTOMER_REQUEST_PATH);
        m_customerRequests = new CustomerRequestArrayList(requests);
        m_startLocation = FileParser.parseTaxiStartLocationToPoint(CUSTOMER_REQUEST_PATH);
        m_actions = new ArrayList<Action>();
    };

    public void optimize(){
        Point currentLocation = m_startLocation;
        m_actions.add(new StartAction(m_startLocation));
        while(!m_customerRequests.isEmpty()){

            // Find closest pickup location to current location
            List<Point> pathFromCurrentToPickup = m_cityMap.findShortestPath(currentLocation, m_customerRequests.getPickupPoints());

            //Convert to action objects and add
            m_actions.addAll(convertPointListToActionList(pathFromCurrentToPickup));

            // If pathFromCurrentToPickup is empty, pickup location is on current location
            // else it's the last point
            Point pickup = currentLocation;
            if(pathFromCurrentToPickup.size() != 0)
                pickup = pathFromCurrentToPickup.get(pathFromCurrentToPickup.size() - 1);
            CustomerRequest cr = m_customerRequests.findByPickupPoint(pickup);

            m_actions.add(new PickupAction(cr.id));
            List<Point> pathFromPickupToDropoff = m_cityMap.findShortestPath(pickup, cr.dropoff);
            m_actions.addAll(convertPointListToActionList(pathFromPickupToDropoff));
            m_actions.add(new DropoffAction(cr.id));
            currentLocation = cr.dropoff;
            m_customerRequests.removeRequest(cr);
        }
    }

    public void toJSONStringOfActions(){
        TaxiActions taxiActions = new TaxiActions("taxi1",m_actions);
        List<TaxiActions> taxiActionsList = new ArrayList<TaxiActions>();
        taxiActionsList.add(taxiActions);
        File file = new File(ACTION_JSON_PATH);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            writeJSONStream(fos, m_actions);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeJSONStream(FileOutputStream fos, List<Action> actions) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, "UTF-8"));
        Gson gson = new Gson();
        writer.setIndent("  ");
        writer.beginArray();
        for (Action action : actions) {
            gson.toJson(action, Action.class, writer);
        }
        writer.endArray();
        writer.close();
    }

    private List<Action> convertPointListToActionList(List<Point> points){
        List<Action> actions = new ArrayList<Action>();
        for(Point p : points)
            actions.add(new DriveAction(p));
        return actions;
    }

}
