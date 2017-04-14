package uk.ac.sussex.deliveryservice.model;


import java.io.Serializable;

/*
Maps to the vehicle class from the main app
 */
@SuppressWarnings("serial")
public class Vehicle implements Serializable {

    public String VehicleName;

    public String RegistrationNumber;

    public String MaxLoad;

    public String Width;

    public String Height;

    public String Length;

    public Vehicle(String vehicleName, String registrationNumber) {
        VehicleName = vehicleName;
        RegistrationNumber = registrationNumber;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }

    public String getMaxLoad() {
        return MaxLoad;
    }

    public void setMaxLoad(String maxLoad) {
        MaxLoad = maxLoad;
    }

    public String getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = width;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }


    public String getVehicleName() {
        return VehicleName;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        RegistrationNumber = registrationNumber;
    }
}
