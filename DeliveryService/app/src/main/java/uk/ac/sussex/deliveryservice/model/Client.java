package uk.ac.sussex.deliveryservice.model;


public class Client {

    public String FirstName;

    public String LastName;

    public String Email;

    public Address Address;

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

    public uk.ac.sussex.deliveryservice.model.Address getAddress() {
        return Address;
    }

    public void setAddress(uk.ac.sussex.deliveryservice.model.Address address) {
        Address = address;
    }
}
