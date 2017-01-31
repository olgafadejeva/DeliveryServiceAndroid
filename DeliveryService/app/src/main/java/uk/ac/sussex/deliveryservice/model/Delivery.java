package uk.ac.sussex.deliveryservice.model;


import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Delivery implements Serializable{
    public int ID;

    public int ClientID;

    public DeliveryStatus DeliveryStatus;

    public String ItemSize;

    public Date DeliverBy;

    public double ItemWeight;

    public Client Client;

    public String StatusString;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }


    public DeliveryStatus getDeliveryStatus() {
        return DeliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        DeliveryStatus = deliveryStatus;
    }

    public String getItemSize() {

        return ItemSize;
    }

    public void setItemSize(String itemSize) {
        ItemSize = itemSize;
    }

    public Date getDeliverBy() {
        return DeliverBy;
    }

    public void setDeliverBy(Date deliverBy) {
        DeliverBy = deliverBy;
    }

    public double getItemWeight() {
        return ItemWeight;
    }

    public void setItemWeight(double itemWeight) {
        ItemWeight = itemWeight;
    }

    public Client getClient() {
        return Client;
    }

    public void setClient(Client client) {
        Client = client;
    }

    public String getStatusString() {
        return StatusString;
    }

    public void setStatusString(String statusString) {
        StatusString = statusString;
    }
}
