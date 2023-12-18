package hr.tvz.dev.models;

import java.io.Serializable;
import java.util.*;

public class Factory extends NamedEntity implements Serializable {
    Address address;
    Set<Item> items;

    public Factory(Long id, String name, Address address, Set<Item> items) {
        super(id, name);
        this.address = address;
        this.items = items;
    }

    public Factory(Long id, String name) {
        super(id, name);
    }

    public Item getItemWithLargestVolume() {
        return getItems().stream().max(Comparator.comparing(Item::calculateVolume)).get();
    }

    public String getItemsAsString() {
        return getItems().stream().map(Item::getName).reduce((a, b) -> a + "; " + b).get();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factory factory)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getAddress(), factory.getAddress()) && Objects.equals(getItems(), factory.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddress(), getItems());
    }
}
