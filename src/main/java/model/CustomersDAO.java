package model;

import java.sql.*;
import java.util.List;

public class CustomersDAO {
    public static final String TABLE_NAME = "customers";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";

    private static final DBConnector dbConnector = new DBConnector();
    static ContactsDAO contactsDAO = new ContactsDAO();

    /**
     * Sql method to save customers to the database
     *
     * @param customers
     * @return
     */
    public static boolean insertCustomers(List<Customers> customers) {
        String sql = "INSERT INTO " + TABLE_NAME
                + "(" + NAME + ", " + SURNAME + ", " + AGE + ") "
                + "VALUES (?,?,?)";
        Connection connection = dbConnector.connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
            int i = 0;
            for (Customers customer : customers) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getSurname());
                if (customer.getAge() == null) {
                    preparedStatement.setNull(3, Types.INTEGER);
                } else {
                    preparedStatement.setInt(3, customer.getAge());
                }
                int id = preparedStatement.executeUpdate();
                if (id == 0) {
                    throw new SQLException("Creating customer failed, no rows affected");
                }
                ResultSet generateKey = preparedStatement.getGeneratedKeys();
                if (generateKey.next()) {
                    contactsDAO.insertContact(customer.getContacts(), generateKey.getInt(1));
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
                connection.commit();
                i++;
                if (i % 100 == 0) {
                    System.out.println("Saved customers: " + i);
                }
            }
            System.out.println("All saved customers: " + i);
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                dbConnector.close();
            }
        }
        return true;
    }
}
