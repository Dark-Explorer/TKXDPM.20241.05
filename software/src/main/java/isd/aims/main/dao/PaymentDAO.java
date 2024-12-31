package isd.aims.main.dao;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.payment.PaymentTransaction;

import java.sql.*;
import java.util.Date;

public class PaymentDAO {
    public void saveTransaction(int orderId, Date createdAt, String content, Long amount) throws SQLException {
        String query = "INSERT INTO \"Transaction\" (orderID, createAt, content, amount) " +
                "VALUES ( ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setDate(2, new java.sql.Date(createdAt.getTime()));
            preparedStatement.setString(3, content);
            preparedStatement.setLong(4, amount);

            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new SQLException("Failed to save transaction", exception);
        }
    }

    public int countTransactionsByOrderId(int orderId) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) FROM Transaction WHERE orderID = ?";

        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        }

        return count;
    }
}