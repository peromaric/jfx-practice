package hr.tvz.dev.models;

import hr.tvz.dev.models.builders.FactoryBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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

    public static List<Factory> readFactories(List<Item> items, List<Address> addresses) {
        List<Factory> factories = new ArrayList<>();

        try {
            List<String> data = Files.readAllLines(Path.of("dat/factories.txt"));
            int currentLine = 0;
            while(currentLine < data.size()) {
                FactoryBuilder factoryBuilder = new FactoryBuilder();
                factoryBuilder.setId(Long.parseLong(data.get(currentLine++)));
                factoryBuilder.setName(data.get(currentLine++));
                Long factoryAddressRead = Long.parseLong(data.get(currentLine++));
                Address factoryAddress = addresses.stream().filter(a -> factoryAddressRead.compareTo(a.getId()) == 0).findFirst().get();
                factoryBuilder.setAddress(factoryAddress);
                List<Long> itemIds = Arrays.stream(data.get(currentLine++).split(" ")).map(Long::parseLong).toList();
                Set<Item> factoryItems = Set.copyOf(
                        items.stream()
                                .filter(item -> itemIds.stream().anyMatch(i -> i.compareTo(item.getId()) == 0))
                                .collect(Collectors.toList())
                );
                factoryBuilder.setItems(factoryItems);
                factories.add(factoryBuilder.createFactory());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factories;
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
