package hr.tvz.dev.utils;

import hr.tvz.dev.models.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public class Database {

    private static Connection connectToDatabase() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("conf/database.properties"));
        String url = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    public static List<Address> getAddresses() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Address> addresses = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet addressResultSet = sqlStatement.executeQuery("SELECT * FROM ADDRESS");
        while(addressResultSet.next()) {
            Address address = getAddressFromResultSet(addressResultSet);
            addresses.add(address);
        }
        connection.close();
        return addresses;
    }

    public static List<Category> getCategories() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Category> categories = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet categoriesResultSet = sqlStatement.executeQuery(
                "SELECT * FROM CATEGORY");
        while(categoriesResultSet.next()) {
            Category category = getCategoryFromResultSet(categoriesResultSet);
            categories.add(category);
        }
        connection.close();
        return categories;
    }

    public static List<Factory> getFactories() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Factory> factories = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet factoryResultSet = sqlStatement.executeQuery("SELECT * FROM FACTORY");
        while(factoryResultSet.next()) {
            Factory factory = getFactoryFromResultSet(factoryResultSet, connection);
            factories.add(factory);
        }
        connection.close();
        return factories;
    }

    public static List<Item> getItems() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Item> items = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet itemsResultSet = sqlStatement.executeQuery("SELECT * FROM ITEM");
        while(itemsResultSet.next()) {
            Item item = getItemFromResultSet(itemsResultSet, connection);
            items.add(item);
        }
        connection.close();
        return items;
    }

    public static List<Store> getStores() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Store> stores = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet storeResultSet = sqlStatement.executeQuery("SELECT * FROM STORE");
        while(storeResultSet.next()) {
            Store store = getStoreFromResultSet(storeResultSet, connection);
            stores.add(store);
        }
        connection.close();
        return stores;
    }

    public static List<Item> getFactoryItems(Connection connection) throws SQLException {
        List<Item> items = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet itemsResultSet = sqlStatement.executeQuery(
                "SELECT * FROM FACTORY_ITEM FI, ITEM I WHERE FI.FACTORY_ID = 1 AND FI.ITEM_ID = I.ID;"
        );
        while(itemsResultSet.next()) {
            Item item = getItemFromResultSet(itemsResultSet, connection);
            items.add(item);
        }
        return items;
    }

    public static List<Item> getStoreItems(Connection connection, Long storeId) throws SQLException {
        List<Item> items = new ArrayList<>();
        PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM STORE_ITEM SI, ITEM I WHERE SI.STORE_ID = ? " +
                "AND SI.ITEM_ID = I.ID;");
        sqlStatement.setLong(1, storeId);
        ResultSet itemsResultSet = sqlStatement.executeQuery();
        while(itemsResultSet.next()) {
            Item item = getItemFromResultSet(itemsResultSet, connection);
            items.add(item);
        }
        return items;
    }

    public static Item getItemById(Connection connection, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM ITEM WHERE ID = ?;"
        );
        preparedStatement.setString(1, id.toString());
        ResultSet itemResultSet = preparedStatement.executeQuery();
        itemResultSet.first();
        return getItemFromResultSet(itemResultSet, connection);
    }

    public static Address getAddressById(Connection connection, String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM ADDRESS WHERE ID = ?;"
        );
        preparedStatement.setString(1, id);
        ResultSet addressResultSet = preparedStatement.executeQuery();
        addressResultSet.first();
        return getAddressFromResultSet(addressResultSet);
    }

    public static Store getStoreByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM STORE WHERE NAME = ?;"
        );
        preparedStatement.setString(1, name);
        ResultSet storeResultSet = preparedStatement.executeQuery();
        storeResultSet.first();
        return getStoreFromResultSet(storeResultSet, connection);
    }

    public static Category getCategoryById(Connection connection, String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM CATEGORY WHERE ID = ?;"
        );
        preparedStatement.setString(1, id);
        ResultSet categoryResultSet = preparedStatement.executeQuery();
        categoryResultSet.first();
        return getCategoryFromResultSet(categoryResultSet);
    }


    public static void insertCategory(Category category) throws SQLException, IOException {
        Connection connection = connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO CATEGORY(NAME, DESCRIPTION) VALUES (?,?)");
        preparedStatement.setString(1, category.getName());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.executeUpdate();
        connection.close();
    }

    public static void insertItem(Item item) throws SQLException, IOException {
        Connection connection = connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO ITEM(CATEGORY_ID, NAME, WIDTH, HEIGHT, LENGTH, PRODUCTION_COST, SELLING_PRICE) " +
                        "VALUES (?,?,?,?,?,?,?)");
        preparedStatement.setLong(1, item.getCategory().getId());
        preparedStatement.setString(2, item.getName());
        preparedStatement.setBigDecimal(3, item.getWidth());
        preparedStatement.setBigDecimal(4, item.getHeight());
        preparedStatement.setBigDecimal(5, item.getLength());
        preparedStatement.setBigDecimal(6, item.getProductionCost());
        preparedStatement.setBigDecimal(7, item.getSellingPrice());
        preparedStatement.executeUpdate();
        connection.close();
    }

    public static void insertStore(Store store) throws SQLException, IOException {
        Connection connection = connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO STORE(NAME, WEB_ADDRESS) VALUES (?,?)");
        preparedStatement.setString(1, store.getName());
        preparedStatement.setString(2, store.getWebAddress());
        preparedStatement.execute();
        Store newStore = getStoreByName(connection, store.getName());
        for(Item item : store.getItems()) {
            PreparedStatement preparedStatementItem = connection.prepareStatement(
                    "INSERT INTO STORE_ITEM(STORE_ID, ITEM_ID) VALUES (?,?)"
            );
            preparedStatementItem.setLong(1, newStore.getId());
            preparedStatementItem.setLong(2, item.getId());
            preparedStatementItem.execute();
        }
        connection.close();
    }

    private static Category getCategoryFromResultSet(ResultSet categoryResultSet) throws SQLException {
        Long id = categoryResultSet.getLong("ID");
        String name = categoryResultSet.getString("NAME");
        String description = categoryResultSet.getString("DESCRIPTION");
        return new Category(id, name, description);
    }

    private static Address getAddressFromResultSet(ResultSet addressResultSet) throws SQLException {
        Long id = addressResultSet.getLong("ID");
        String street = addressResultSet.getString("STREET");
        String houseNumber = addressResultSet.getString("HOUSE_NUMBER");
        String city = addressResultSet.getString("CITY");
        Integer postalCode = addressResultSet.getInt("POSTAL_CODE");

        return new Address(id, street, houseNumber, city, postalCode);
    }

    private static Factory getFactoryFromResultSet(
            ResultSet factoryResultSet,
            Connection connection
    ) throws SQLException
    {
        Long id = factoryResultSet.getLong("ID");
        String name = factoryResultSet.getString("NAME");
        String addressId = factoryResultSet.getString("ADDRESS_ID");
        Address factoryAddress = getAddressById(connection, addressId);
        List<Item> factoryItems = getFactoryItems(connection);

        return new Factory(id, name, factoryAddress, new HashSet<>(factoryItems));
    }

    private static Store getStoreFromResultSet(
            ResultSet storeResultSet,
            Connection connection
    ) throws SQLException
    {
        Long id = storeResultSet.getLong("ID");
        String name = storeResultSet.getString("NAME");
        String webAddresss = storeResultSet.getString("WEB_ADDRESS");
        List<Item> storeItems = getStoreItems(connection, id);

        return new Store(id, name, webAddresss, new HashSet<>(storeItems));
    }

    private static Item getItemFromResultSet(ResultSet itemResultSet, Connection connection) throws SQLException {
        Long id = itemResultSet.getLong("ID");
        Integer categoryId = itemResultSet.getInt("CATEGORY_ID");
        String name = itemResultSet.getString("NAME");
        BigDecimal width = itemResultSet.getBigDecimal("WIDTH");
        BigDecimal height = itemResultSet.getBigDecimal("HEIGHT");
        BigDecimal length = itemResultSet.getBigDecimal("LENGTH");
        BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
        BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

        Category category = getCategoryById(connection, categoryId.toString());
        return new Item(id, name, category, width, height, length, productionCost, sellingPrice);
    }
}
