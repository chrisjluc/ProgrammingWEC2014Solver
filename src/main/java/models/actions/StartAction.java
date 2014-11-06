package models.actions;

import models.Point;

/**
 * Created by chrisjluc on 14-11-06.
 */
public class StartAction extends Action {

    public StartAction(Point p){
        this.x = p.x;
        this.y = p.y;
        this.action = START;
    }
}
