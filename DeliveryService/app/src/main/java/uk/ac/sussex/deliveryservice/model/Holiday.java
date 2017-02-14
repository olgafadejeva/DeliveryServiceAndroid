package uk.ac.sussex.deliveryservice.model;

import java.util.Date;

public class Holiday {

    public Date getTo() {
        return To;
    }

    public void setTo(Date to) {
        To = to;
    }

    public Date getFrom() {
        return From;
    }

    public void setFrom(Date from) {
        From = from;
    }

    private Date To;
    private Date From;

}
