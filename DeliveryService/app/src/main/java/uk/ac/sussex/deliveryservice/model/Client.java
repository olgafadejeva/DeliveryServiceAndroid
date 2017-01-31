package uk.ac.sussex.deliveryservice.model;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Client implements Serializable {

    private String FirstName;

    private String LastName;

    private String Email;

    private Address Address;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Address getAddress() {
        return Address;
    }

    public void setAddress(Address address) {
        Address = address;
    }
}
