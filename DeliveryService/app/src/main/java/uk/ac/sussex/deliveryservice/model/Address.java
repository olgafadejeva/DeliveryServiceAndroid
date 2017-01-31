package uk.ac.sussex.deliveryservice.model;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Address implements Serializable {

    public String LineOne;

    public String LineTwo;

    public String City;

    public String PostCode;

    public double Lat;

    public double Lng;

    public String getLineOne() {
        return LineOne;
    }

    public void setLineOne(String lineOne) {
        LineOne = lineOne;
    }

    public String getLineTwo() {
        return LineTwo;
    }

    public void setLineTwo(String lineTwo) {
        LineTwo = lineTwo;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
}
