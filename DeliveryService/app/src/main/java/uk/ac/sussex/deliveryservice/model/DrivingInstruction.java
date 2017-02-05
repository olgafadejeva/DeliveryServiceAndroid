package uk.ac.sussex.deliveryservice.model;

import java.io.Serializable;
import java.util.ArrayList;


public class DrivingInstruction implements Serializable{

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public DrivingInstruction() {
        this.directions = new ArrayList<>();
    }

    private ArrayList<String> directions;
}
