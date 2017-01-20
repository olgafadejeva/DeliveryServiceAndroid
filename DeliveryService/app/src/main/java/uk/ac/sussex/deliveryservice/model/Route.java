package uk.ac.sussex.deliveryservice.model;

import java.util.Date;
import java.util.List;

public class Route {
    public int ID;

    public Date DeliverBy;

    public Date  DeliveryDate;

    public double OverallDistance;

    public String RouteStatusString;

    public double OverallTimeRequired;

    public List<Delivery> Deliveries;

    public PickUpAddress PickUpAddress;

    public Vehicle Vehicle;
}
