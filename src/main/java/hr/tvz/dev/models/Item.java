package hr.tvz.dev.models;

import hr.tvz.dev.models.builders.ItemBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Item extends NamedEntity implements Serializable {
    Category category;
    BigDecimal width;
    BigDecimal height;
    BigDecimal length;
    BigDecimal productionCost;
    BigDecimal sellingPrice;
    Discount discount;

    public Item(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount) {
        super(id, name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    public static List<Item> readItems(List<Category> categories) {
        List<Item> items = new ArrayList<>();
        try {
            List<String> data = Files.readAllLines(Path.of("dat/items.txt"));

            int currentLine = 0;
            while(currentLine < data.size()) {
                ItemBuilder itemBuilder = new ItemBuilder();
                itemBuilder.setId(Long.parseLong(data.get(currentLine++)));
                itemBuilder.setName(data.get(currentLine++));
                Long categoryId = Long.parseLong(data.get(currentLine++));
                Category category = categories.stream().filter(c -> c.getId().equals(categoryId)).findFirst().get();
                itemBuilder.setCategory(category);
                itemBuilder.setWidth(BigDecimal.valueOf(Long.parseLong(data.get(currentLine++))));
                itemBuilder.setHeight(BigDecimal.valueOf(Long.parseLong(data.get(currentLine++))));
                itemBuilder.setLength(BigDecimal.valueOf(Long.parseLong(data.get(currentLine++))));
                itemBuilder.setProductionCost(new BigDecimal(data.get(currentLine++)));
                itemBuilder.setSellingPrice(new BigDecimal(data.get(currentLine++)));
                Discount discount = new Discount(((new BigDecimal(data.get(currentLine++)))));
                itemBuilder.setDiscount(discount);
                Item item = itemBuilder.createItem();
                if(item.getCategory().getId().equals(1L)) {
                    Food food = new Food(item);
                    food.setWeight(new BigDecimal(data.get(currentLine++)));
                    food.setKcalPerKg(new BigInteger(data.get(currentLine++)));
                    items.add(food);
                } else if (item.getCategory().getId().equals(2L)) {
                    Laptop laptop = new Laptop(item);
                    laptop.setGuaranteeExpirationDate(Integer.parseInt(data.get(currentLine++)));
                    items.add(laptop);
                } else items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public BigDecimal calculateVolume() {
        return getLength().multiply(getWidth()).multiply(getHeight());
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getCategory(), item.getCategory()) && Objects.equals(getWidth(), item.getWidth()) && Objects.equals(getHeight(), item.getHeight()) && Objects.equals(getLength(), item.getLength()) && Objects.equals(getProductionCost(), item.getProductionCost()) && Objects.equals(getSellingPrice(), item.getSellingPrice()) && Objects.equals(getDiscount(), item.getDiscount());
    }

    @Override
    public String toString() {
        return "Item{" +
                "category=" + category +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", productionCost=" + productionCost +
                ", sellingPrice=" + sellingPrice +
                ", discount=" + discount +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCategory(), getWidth(), getHeight(), getLength(), getProductionCost(), getSellingPrice(), getDiscount());
    }
}
