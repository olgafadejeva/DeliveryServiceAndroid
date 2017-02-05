package uk.ac.sussex.deliveryservice.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class RouteViewModel implements Serializable{

    private String status;
    private String deliveryDate;
    private String deliverByDate;
    private int ID;
    private ArrayList<Delivery> deliveries;
    private double overallDistance;
    private double overallTime;
    private PickUpAddress pickUpAddress;
    private Vehicle vehicle;


    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public PickUpAddress getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(PickUpAddress pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public double getOverallDistance() {
        return overallDistance;
    }

    public void setOverallDistance(double overallDistance) {
        this.overallDistance = overallDistance;
    }

    public double getOverallTime() {
        return overallTime;
    }

    public void setOverallTime(double overallTime) {
        this.overallTime = overallTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getNumberOfDeliveries() {
        return deliveries.size();
    }

    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(ArrayList<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public String getDeliverByDate() {
        return deliverByDate;
    }

    public void setDeliverByDate(String deliverByDate) {
        this.deliverByDate = deliverByDate;
    }

}
