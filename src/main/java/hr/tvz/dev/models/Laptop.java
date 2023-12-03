package hr.tvz.dev.models;

import java.math.BigDecimal;
import java.util.Objects;

public non-sealed class Laptop extends Item implements Technical {
    private Integer guaranteeExpirationDate;

    public Laptop(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Discount discount, Integer guaranteeExpirationDate) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discount);
        this.guaranteeExpirationDate = guaranteeExpirationDate;
    }
    public Laptop(Item item) {
        super(item.getId(), item.getName(), item.getCategory(), item.getWidth(), item.getHeight(),
                item.getLength(), item.getProductionCost(), item.getSellingPrice(), item.getDiscount());
    }

    @Override
    public Integer getGuaranteeExpiration() {
        return guaranteeExpirationDate;
    }

    @Override
    public void setGuaranteeExpirationDate(Integer guaranteeExpirationDate) {
        this.guaranteeExpirationDate = guaranteeExpirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Laptop laptop)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(guaranteeExpirationDate, laptop.guaranteeExpirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), guaranteeExpirationDate);
    }
}
