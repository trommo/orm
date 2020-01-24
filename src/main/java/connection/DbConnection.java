package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    static final String DB_URL = ConnectionProp.getInstance().getDbUrl();
    static final String LOGIN = ConnectionProp.getInstance().getDbLogin();
    static final String PASSWORD = ConnectionProp.getInstance().getDbPassword();

    private DbConnection() {
        checkDriver();
    }

    private static void checkDriver(){
        try {
            Class.forName(ConnectionProp.getInstance().getDbDriver());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver is not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed connection");
            e.printStackTrace();
        }
        if (connection != null) {
            //System.out.println("You are successfully connected to the database now!");
        } else {
            System.out.println("Failed connection");
        }
        return connection;
    }


}