package uk.ac.sussex.deliveryservice.model;

import java.util.ArrayList;

/**
 * Created by Olga on 08/02/2017.
 */

public class DriverDetails {

    private String email;
    private Address address;
    private ArrayList<Holiday> holidays;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(ArrayList<Holiday> holidays) {
        this.holidays = holidays;
    }
}

