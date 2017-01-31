package uk.ac.sussex.deliveryservice.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Route implements Serializable{
    private int ID;

    private Date DeliverBy;

    private Date  DeliveryDate;

    private double OverallDistance;

    private String RouteStatusString;

    private double OverallTimeRequired;

    private List<Delivery> Deliveries;

    private PickUpAddress PickUpAddress;

    private Vehicle Vehicle;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getDeliverBy() {
        return DeliverBy;
    }

    public void setDeliverBy(Date deliverBy) {
        DeliverBy = deliverBy;
    }

    public Date getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public double getOverallDistance() {
        return OverallDistance;
    }

    public void setOverallDistance(double overallDistance) {
        OverallDistance = overallDistance;
    }

    public String getRouteStatusString() {
        return RouteStatusString;
    }

    public void setRouteStatusString(String routeStatusString) {
        RouteStatusString = routeStatusString;
    }

    public double getOverallTimeRequired() {
        return OverallTimeRequired;
    }

    public void setOverallTimeRequired(double overallTimeRequired) {
        OverallTimeRequired = overallTimeRequired;
    }

    public List<Delivery> getDeliveries() {
        return Deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        Deliveries = deliveries;
    }

    public uk.ac.sussex.deliveryservice.model.PickUpAddress getPickUpAddress() {
        return PickUpAddress;
    }

    public void setPickUpAddress(uk.ac.sussex.deliveryservice.model.PickUpAddress pickUpAddress) {
        PickUpAddress = pickUpAddress;
    }

    public uk.ac.sussex.deliveryservice.model.Vehicle getVehicle() {
        return Vehicle;
    }

    public void setVehicle(uk.ac.sussex.deliveryservice.model.Vehicle vehicle) {
        Vehicle = vehicle;
    }
}
