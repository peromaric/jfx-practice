package hr.tvz.dev.enums;

public enum City {
    ZAGREB("Zagreb", 10000),
    SPLIT("Split", 21000),
    ZADAR("Zadar", 23000);
    private static String name;
    private static String postalCode;


    City(String name, Integer postalCode) {};

    public static String getName() {
        return name;
    }

    public static String getPostalCode() {
        return postalCode;
    }
}
