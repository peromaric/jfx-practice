package hr.tvz.dev.utils;

import hr.tvz.dev.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public class Database {

    private static Database database_instance = null;
    private Boolean activeConnectionWithDatabase;
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private Database() {
        activeConnectionWithDatabase = false;
    }

    public static synchronized Database getInstance() {
        if (database_instance == null) {
            database_instance = new Database();
            logger.info("Database singleton instance created");
        }

        return database_instance;
    }

    private synchronized Connection connectToDatabase() throws SQLException, IOException {
        while(activeConnectionWithDatabase) {
            try {
                logger.info(Thread.currentThread().getName() + " waiting to connect to the database...");
                wait();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                logger.error(Thread.currentThread().getName() + " had issues with database connection!");
            }
        }
        Properties properties = new Properties();
        properties.load(new FileReader("conf/database.properties"));
        String url = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    private synchronized void disconnectFromDatabase(Connection connection) throws SQLException {
        connection.close();
        database_instance.activeConnectionWithDatabase = false;
        logger.info(Thread.currentThread().getName() + " disconnecting from the database and notifying...");
        notifyAll();
    }

    public synchronized List<Address> getAddresses() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Address> addresses = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet addressResultSet = sqlStatement.executeQuery("SELECT * FROM ADDRESS");
        while(addressResultSet.next()) {
            Address address = getAddressFromResultSet(addressResultSet);
            addresses.add(address);
        }
        disconnectFromDatabase(connection);
        return addresses;
    }

    public synchronized List<Category> getCategories() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Category> categories = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet categoriesResultSet = sqlStatement.executeQuery(
                "SELECT * FROM CATEGORY");
        while(categoriesResultSet.next()) {
            Category category = getCategoryFromResultSet(categoriesResultSet);
            categories.add(category);
        }
        disconnectFromDatabase(connection);
        return categories;
    }

    public synchronized List<Factory> getFactories() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Factory> factories = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet factoryResultSet = sqlStatement.executeQuery("SELECT * FROM FACTORY");
        while(factoryResultSet.next()) {
            Factory factory = getFactoryFromResultSet(factoryResultSet, connection);
            factories.add(factory);
        }
        disconnectFromDatabase(connection);
        return factories;
    }

    public synchronized List<Item> getItems() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Item> items = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet itemsResultSet = sqlStatement.executeQuery("SELECT * FROM ITEM");
        while(itemsResultSet.next()) {
            Item item = getItemFromResultSet(itemsResultSet, connection);
            items.add(item);
        }
        disconnectFromDatabase(connection);
        return items;
    }

    public synchronized List<Store> getStores() throws SQLException, IOException {
        Connection connection = connectToDatabase();
        List<Store> stores = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();
        ResultSet storeResultSet = sqlStatement.executeQuery("SELECT * FROM STORE");
        while(storeResultSet.next()) {
            Store store = getStoreFromResultSet(storeResultSet, connection);
            stores.add(store);
        }
        disconnectFromDatabase(connection);
        return stores;
    }

    public synchronized List<Item> getFactoryItems(Connection connection, Long factoryId) throws SQLException {
        List<Item> items = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM FACTORY_ITEM FI, ITEM I WHERE FI.FACTORY_ID = (?) AND FI.ITEM_ID = I.ID;"
        );
        preparedStatement.setLong(1, factoryId);
        ResultSet itemsResultSet = preparedStatement.executeQuery();
        while(itemsResultSet.next()) {
            Item item = getItemFromResultSet(itemsResultSet, connection);
            items.add(item);
        }
        return items;
    }

    public synchronized List<Item> getStoreItems(Connection connection, Long storeId) throws SQLException {
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

    public synchronized Item getItemById(Connection connection, Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM ITEM WHERE ID = ?;"
        );
        preparedStatement.setString(1, id.toString());
        ResultSet itemResultSet = preparedStatement.executeQuery();
        itemResultSet.first();
        return getItemFromResultSet(itemResultSet, connection);
    }

    public synchronized Address getAddressById(Connection connection, String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM ADDRESS WHERE ID = ?;"
        );
        preparedStatement.setString(1, id);
        ResultSet addressResultSet = preparedStatement.executeQuery();
        addressResultSet.first();
        return getAddressFromResultSet(addressResultSet);
    }

    public synchronized Store getStoreByName(Connection connection, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM STORE WHERE NAME = ?;"
        );
        preparedStatement.setString(1, name);
        ResultSet storeResultSet = preparedStatement.executeQuery();
        storeResultSet.first();
        return getStoreFromResultSet(storeResultSet, connection);
    }

    public synchronized Category getCategoryById(Connection connection, String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM CATEGORY WHERE ID = ?;"
        );
        preparedStatement.setString(1, id);
        ResultSet categoryResultSet = preparedStatement.executeQuery();
        categoryResultSet.first();
        return getCategoryFromResultSet(categoryResultSet);
    }


    public synchronized void insertCategory(Category category) throws SQLException, IOException {
        Connection connection = connectToDatabase();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO CATEGORY(NAME, DESCRIPTION) VALUES (?,?)");
        preparedStatement.setString(1, category.getName());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.executeUpdate();
        disconnectFromDatabase(connection);
    }

    public synchronized void insertItem(Item item) throws SQLException, IOException {
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
        disconnectFromDatabase(connection);
    }

    public synchronized void insertStore(Store store) throws SQLException, IOException {
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
        disconnectFromDatabase(connection);
    }

    public static Long insertAddress(Connection connection, Address address) throws SQLException, IOException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO  ADDRESS(STREET, HOUSE_NUMBER, CITY, POSTAL_CODE) " +
                        "VALUES ( ?, ?, ?, ? )",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, address.getStreet());
        preparedStatement.setString(2, address.getHouseNumber());
        preparedStatement.setString(3, address.getCity());
        preparedStatement.setInt(4, address.getPostalCode());
        preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();

        if (rs.next()) {
            return rs.getLong(1);
        } else return 0L;
    }

    public synchronized void insertFactory(Factory factory) throws SQLException, IOException {
        Connection connection = connectToDatabase();

        Long addressId = insertAddress(connection, factory.getAddress());
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO FACTORY(NAME, ADDRESS_ID) VALUES ( ?, ? )",
                Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, factory.getName());
        preparedStatement.setLong(2, addressId);
        preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        Long factoryId = 0L;
        if (rs.next()) {
            factoryId = rs.getLong(1);
        }

        for(Item item : factory.getItems()) {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO FACTORY_ITEM VALUES ( ?, ? )",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setLong(1, factoryId);
            preparedStatement.setLong(2, item.getId());
            preparedStatement.executeUpdate();
        }

        disconnectFromDatabase(connection);
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

    private synchronized Factory getFactoryFromResultSet(
            ResultSet factoryResultSet,
            Connection connection
    ) throws SQLException
    {
        Long id = factoryResultSet.getLong("ID");
        String name = factoryResultSet.getString("NAME");
        String addressId = factoryResultSet.getString("ADDRESS_ID");
        Address factoryAddress = getAddressById(connection, addressId);
        List<Item> factoryItems = getFactoryItems(connection, id);

        return new Factory(id, name, factoryAddress, new HashSet<>(factoryItems));
    }

    private synchronized Store getStoreFromResultSet(
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

    private synchronized Item getItemFromResultSet(ResultSet itemResultSet, Connection connection) throws SQLException {
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
