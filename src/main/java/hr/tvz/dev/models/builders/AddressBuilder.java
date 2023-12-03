package hr.tvz.dev.models.builders;


import hr.tvz.dev.enums.City;
import hr.tvz.dev.models.Address;

public class AddressBuilder {
    private Long id;
    private String street;
    private String houseNumber;
    private City city;

    public AddressBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public AddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public AddressBuilder setCity(City city) {
        this.city = city;
        return this;
    }

    public Address createAddress() {
        return new Address(id, street, houseNumber, city);
    }
}