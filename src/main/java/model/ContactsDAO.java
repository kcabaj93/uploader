package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ContactsDAO {
    private static final String TABLE_NAME = "contacts";
    private static final String ID_CUSTOMER = "id_customer";
    private static final String TYPE = "type";
    private static final String CONTACT = "contact";

    private final DBConnector dbConnector = new DBConnector();

    /**
     * Sql method to save contacts to the database
     *
     * @param contacts
     * @param idCustomer
     * @throws SQLException
     */
    public void insertContact(List<Contacts> contacts, int idCustomer) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME
                + "(" + ID_CUSTOMER + ", " + TYPE + ", " + CONTACT + ") "
                + "VALUES (?, ?, ? )";
        Connection connection = dbConnector.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (Contacts contact : contacts) {
            preparedStatement.setInt(1, idCustomer);
            preparedStatement.setInt(2, contact.getType());
            preparedStatement.setString(3, contact.getContact());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.clearBatch();
        preparedStatement.close();
    }
}
