package hr.tvz.dev.sort;

import hr.tvz.dev.models.Item;

import java.util.Comparator;

public class ProductionSorter implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        return item1.getSellingPrice().compareTo(item2.getSellingPrice());
    }

    @Override
    public Comparator<Item> reversed() {
        return Comparator.super.reversed();
    }
}
