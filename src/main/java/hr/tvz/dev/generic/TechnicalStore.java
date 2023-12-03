package hr.tvz.dev.generic;

import hr.tvz.dev.models.Item;
import hr.tvz.dev.models.Store;
import hr.tvz.dev.models.Technical;

import java.util.List;
import java.util.Set;

public class TechnicalStore<T extends Technical> extends Store {

    List<T> technicalItems;

    public TechnicalStore(Long id, String name, String webAddress, Set<Item> items, List<T> technicalItems) {
        super(id, name, webAddress, items);
        this.technicalItems = technicalItems;
    }

    public List<T> getTechnicalItems() {
        return technicalItems;
    }

    public void setTechnicalItems(List<T> technicalItems) {
        this.technicalItems = technicalItems;
    }
}
