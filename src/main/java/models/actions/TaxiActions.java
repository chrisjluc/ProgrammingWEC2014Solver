package models.actions;

import java.util.List;

/**
 * Created by chrisjluc on 14-11-06.
 */
public class TaxiActions {
    private String taxiId;
    private List<Action> actions;
    public TaxiActions(String taxiId, List<Action> actions){
        this.taxiId = taxiId;
        this.actions = actions;
    }
}
