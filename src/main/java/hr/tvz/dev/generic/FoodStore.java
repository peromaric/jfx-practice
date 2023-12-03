package hr.tvz.dev.generic;


import hr.tvz.dev.models.Edible;
import hr.tvz.dev.models.Item;
import hr.tvz.dev.models.Store;

import java.util.List;
import java.util.Set;

public class FoodStore<T extends Edible> extends Store {
    List<T> foodItems;

    public List<T> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<T> foodItems) {
        this.foodItems = foodItems;
    }

    public FoodStore(Long id, String name, String webAddress, Set<Item> items, List<T> foodItems) {
        super(id, name, webAddress, items);
        this.foodItems = foodItems;
    }
}
