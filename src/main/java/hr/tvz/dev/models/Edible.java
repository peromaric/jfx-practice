package hr.tvz.dev.models;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface Edible {
    public BigInteger calculateKilocalories();

    public BigDecimal calculatePrice();

    public BigDecimal getWeight();
}
