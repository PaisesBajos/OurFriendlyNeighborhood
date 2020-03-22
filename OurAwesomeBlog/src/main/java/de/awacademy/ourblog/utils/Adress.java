package de.awacademy.ourblog.utils;

public class Adress {

    private String country;
    private String city;
    private String plz;
    private String street;
    private String houseNumber;
    private String addition;


    public Adress(){};

    public Adress(String country, String city, String plz, String street, String houseNumber, String addition) {
        this.country = country;
        this.city = city;
        this.plz = plz;
        this.street = street;
        this.houseNumber = houseNumber;
        this.addition = addition;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }
}
