package uk.ac.sussex.deliveryservice.model;

import java.util.ArrayList;

/**
 * Created by Olga on 22/01/2017.
 */

public class RouteViewModel {

    private String status;
    private String deliveryDate;

    private ArrayList<Delivery> deliveries;

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
}
