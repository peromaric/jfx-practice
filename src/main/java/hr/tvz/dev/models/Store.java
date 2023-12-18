package hr.tvz.dev.models;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Store extends NamedEntity implements Serializable {
    String webAddress;
    Set<Item> items;

    public Store(Long id, String name, String webAddress, Set<Item> items) {
        super(id, name);
        this.webAddress = webAddress;
        this.items = items;
    }

    public String getItemsAsString() {
        return getItems().stream().map(Item::getName).reduce((a, b) -> a + "; " + b).get();
    }

    public Item findCheapestItem() {
        return items.stream().min(Comparator.comparing(Item::getSellingPrice)).get();
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
