package hr.tvz.dev.models.builders;


import hr.tvz.dev.models.Category;
import hr.tvz.dev.models.Discount;
import hr.tvz.dev.models.Item;

import java.math.BigDecimal;

public class ItemBuilder {
    private Long id;
    private String name;
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;
    private Discount discount;

    public ItemBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public ItemBuilder setWidth(BigDecimal width) {
        this.width = width;
        return this;
    }

    public ItemBuilder setHeight(BigDecimal height) {
        this.height = height;
        return this;
    }

    public ItemBuilder setLength(BigDecimal length) {
        this.length = length;
        return this;
    }

    public ItemBuilder setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
        return this;
    }

    public ItemBuilder setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public ItemBuilder setDiscount(Discount discount) {
        this.discount = discount;
        return this;
    }

    public Item createItem() {
        return new Item(id, name, category, width, height, length, productionCost, sellingPrice, discount);
    }
}