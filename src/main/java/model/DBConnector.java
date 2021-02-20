package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private final static String DBURL = "jdbc:mysql://127.0.0.1:3306/loader";
    private final static String DBUSER = "root";
    private final static String DBPASS = "";
    private final static String DBDRIVER = "com.mysql.jdbc.Driver";

    private Connection connection;

    /**
     * Database connection
     *
     * @return
     */
    public Connection connect() {
        try {
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
