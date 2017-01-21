package uk.ac.sussex.deliveryservice.model;

import java.util.Date;

public class DeliveryStatus {
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