package hr.tvz.dev.models.builders;


import hr.tvz.dev.models.Item;
import hr.tvz.dev.models.Store;

import java.util.Set;

public class StoreBuilder {
    private Long id;
    private String name;
    private String webAddress;
    private Set<Item> items;

    public StoreBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public StoreBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StoreBuilder setWebAddress(String webAddress) {
        this.webAddress = webAddress;
        return this;
    }

    public StoreBuilder setItems(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Store createStore() {
        return new Store(id, name, webAddress, items);
    }
}