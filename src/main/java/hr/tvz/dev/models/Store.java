package hr.tvz.dev.models;

import hr.tvz.dev.models.builders.StoreBuilder;

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

    public static List<Store> readStores(List<Item> items) {
        List<Store> stores = new ArrayList<>();

        try {
            List<String> data = Files.readAllLines(Path.of("dat/stores.txt"));
            int currentLine = 0;
            while(currentLine < data.size()) {
                StoreBuilder storeBuilder = new StoreBuilder();
                storeBuilder.setId(Long.parseLong(data.get(currentLine++)));
                storeBuilder.setName(data.get(currentLine++));
                storeBuilder.setWebAddress(data.get(currentLine++));
                List<Long> itemIds = Arrays.stream(data.get(currentLine++).split(" ")).map(Long::parseLong).toList();
                Set<Item> storeItems = Set.copyOf(
                        items.stream()
                                .filter(item -> itemIds.stream().anyMatch(i -> i.compareTo(item.getId()) == 0))
                                .collect(Collectors.toList())
                );
                storeBuilder.setItems(storeItems);
                stores.add(storeBuilder.createStore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stores;
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
