package hr.tvz.dev.models;


import hr.tvz.dev.enums.City;
import hr.tvz.dev.models.builders.AddressBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Address implements Serializable {

    Long id;
    String street;
    String houseNumber;
    City city;

    public Address(Long id, String street, String houseNumber, City city) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public static List<Address> readAddresses() {
        List<Address> addresses = new ArrayList<>();

        try {
            List<String> data = Files.readAllLines(Path.of("dat/addresses.txt"));
            int currentLine = 0;
            while(currentLine < data.size()) {
                AddressBuilder addressBuilder = new AddressBuilder();
                addressBuilder.setId(Long.parseLong(data.get(currentLine++)));
                addressBuilder.setCity(City.valueOf(data.get(currentLine++)));
                addressBuilder.setStreet(data.get(currentLine++));
                addressBuilder.setHouseNumber(data.get(currentLine++));
                addresses.add(addressBuilder.createAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return street + houseNumber + ", " + city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(getId(), address.getId()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getHouseNumber(), address.getHouseNumber()) && getCity() == address.getCity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStreet(), getHouseNumber(), getCity());
    }
}
