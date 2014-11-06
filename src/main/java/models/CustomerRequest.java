package models;

/**
 * Created by chrisjluc on 14-11-05.
 */
public class CustomerRequest {
    public Point pickup;
    public Point dropoff;
    public double customerFee;
    public int id;

    public CustomerRequest(int id, double customerFee, Point pickup, Point dropoff) {
        this.id = id;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.customerFee = customerFee;
    }
}
