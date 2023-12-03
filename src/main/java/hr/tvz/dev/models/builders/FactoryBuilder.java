package hr.tvz.dev.models.builders;

import hr.tvz.dev.models.Address;
import hr.tvz.dev.models.Factory;
import hr.tvz.dev.models.Item;

import java.util.Set;

public class FactoryBuilder {
    private Long id;
    private String name;
    private Address address;
    private Set<Item> items;

    public FactoryBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public FactoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FactoryBuilder setAddress(Address address) {
        this.address = address;
        return this;
    }

    public FactoryBuilder setItems(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Factory createFactory() {
        return new Factory(id, name, address, items);
    }
}