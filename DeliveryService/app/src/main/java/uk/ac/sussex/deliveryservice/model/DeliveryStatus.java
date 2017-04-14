package uk.ac.sussex.deliveryservice.model;

import java.io.Serializable;
import java.util.Date;

/*
Maps to  the DeliveryStatus class from the web app
 */
@SuppressWarnings("serial")
public class DeliveryStatus implements Serializable{
    public int ID;

    public int Status;

    public String ReasonFailed;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getReasonFailed() {
        return ReasonFailed;
    }

    public void setReasonFailed(String reasonFailed) {
        ReasonFailed = reasonFailed;
    }

    public Date getDeliveredDate() {
        return DeliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        DeliveredDate = deliveredDate;
    }

    public Date DeliveredDate;
}
