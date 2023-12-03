package hr.tvz.dev.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Food extends Item implements Edible {

    private BigDecimal weight;
    private BigInteger kcalPerKg;

    public Food(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, BigDecimal weight, BigInteger kcalPerKg) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
        this.kcalPerKg = kcalPerKg;
    }

    public Food(Item item) {
        super(item.getId(), item.getName(), item.getCategory(), item.getWidth(), item.getHeight(),
                item.getLength(), item.getProductionCost(), item.getSellingPrice(), item.getDiscount());
    }

    public BigInteger getKcalPerKg() {
        return kcalPerKg;
    }

    public void setKcalPerKg(BigInteger kcalPerKg) {
        this.kcalPerKg = kcalPerKg;
    }

    @Override
    public BigInteger calculateKilocalories() {
        return weight.multiply(new BigDecimal(getKcalPerKg())).toBigInteger();
    }

    @Override
    public BigDecimal calculatePrice() {
        return weight.multiply(sellingPrice);
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getWeight(), that.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWeight());
    }
}
