package uk.ac.sussex.deliveryservice.model;

import java.util.ArrayList;

/*
Maps to the DriverDetails class from the web app
 */
public class DriverDetails {

    private String email;
    private Address driverAddress;
    private ArrayList<Holiday> holidays;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(Address driverAddress) {
        this.driverAddress = driverAddress;
    }

    public ArrayList<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(ArrayList<Holiday> holidays) {
        this.holidays = holidays;
    }
}

