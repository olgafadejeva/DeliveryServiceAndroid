package uk.ac.sussex.deliveryservice.model;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Vehicle implements Serializable {

    public String VehicleName;

    public String RegistrationNumber;

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
