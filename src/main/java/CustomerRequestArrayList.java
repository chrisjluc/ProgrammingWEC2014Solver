import models.CustomerRequest;
import models.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrisjluc on 14-11-06.
 */
public class CustomerRequestArrayList extends ArrayList<CustomerRequest> {

    private List<Point> pickupPoints = null;

    public CustomerRequestArrayList(List<CustomerRequest> requests){
       for(CustomerRequest r : requests)
           this.add(r);
    }
    public List<Point> getPickupPoints(){
        if(pickupPoints == null){
            pickupPoints = new ArrayList<Point>();
            for(CustomerRequest cr : this)
                pickupPoints.add(cr.pickup);
        }
        return pickupPoints;
    }

    public void removeRequest(CustomerRequest cr){
        if(pickupPoints != null)
            pickupPoints.remove(cr.pickup);
        this.remove(cr);
    }

    public CustomerRequest findByPickupPoint(Point pickup){
        for(CustomerRequest cr : this)
            if(cr.pickup.equals(pickup))
                return cr;
        return null;
    }
}
