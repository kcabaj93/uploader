package model;

import controller.GetPropertyValues;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection connection;

    /**
     * Database connection
     *
     * @return
     */
    public Connection connect() {
        GetPropertyValues getPropertyValues = new GetPropertyValues();
        try {
            connection = DriverManager.getConnection(getPropertyValues.getPropValues("DBURL"),
                    getPropertyValues.getPropValues("DBUSER"),
                    getPropertyValues.getPropValues("DBPASS"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
