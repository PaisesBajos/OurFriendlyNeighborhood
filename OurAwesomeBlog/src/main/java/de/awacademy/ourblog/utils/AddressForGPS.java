package de.awacademy.ourblog.utils;

public class AddressForGPS {
    private String cityName;
    private String streetName;
    private int number;

    public AddressForGPS(String cityName, String streetName, int number) {
        this.cityName = cityName;
        this.streetName = streetName;
        this.number = number;
    }

    public AddressForGPS(){

    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
