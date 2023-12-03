package hr.tvz.dev.models;

public sealed interface Technical permits Laptop {
    Integer getGuaranteeExpiration();
    void setGuaranteeExpirationDate(Integer expirationDate);
}
