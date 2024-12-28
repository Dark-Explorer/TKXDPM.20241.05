package isd.aims.main.entity.payment;

import isd.aims.main.entity.db.DBConnection;

import java.sql.*;
import java.util.Date;

public class PaymentTransaction {
	private String errorCode;
	private String transactionId;
	private String transactionContent;
	private long amount;
	private Integer orderID;
	private Date createdAt;

	public PaymentTransaction(String errorCode, String transactionId, String transactionContent,
							  long amount, Date createdAt) {
		super();
		this.errorCode = errorCode;
		this.transactionId = transactionId;
		this.transactionContent = transactionContent;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	// sử dụng lớp DBconnection để kết nối và thực hiện các thao tác với cơ sở dữ liệu
	// => phụ thuộc vào lớp cơ sở dữ liệu
	// => tách riêng ra 1 lớp riêng để giảm sự phụ thuộc ( ví dụ PaymentTransactionDAO )
	public void save(int orderId) throws SQLException {
		this.orderID = orderId;
		String query = "INSERT INTO \"Transaction\" ( orderID, createAt, content, amount) " +
				"VALUES ( ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, orderId);
			preparedStatement.setDate(2, new java.sql.Date(createdAt.getTime()));
			preparedStatement.setString(3,transactionContent );
			preparedStatement.setLong(4, amount);

			preparedStatement.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}


	// sử dụng lớp DBconnection để kết nối và thực hiện các thao tác với cơ sở dữ liệu
	// => phụ thuộc vào lớp cơ sở dữ liệu
	// => tách riêng ra 1 lớp riêng để giảm sự phụ thuộc ( ví dụ PaymentTransactionDAO )
	public int checkPaymentByOrderId(int orderId) throws SQLException {
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

	public boolean isSuccess() {
		// Assuming a null errorCode or an errorCode "00" means success
		return errorCode == null || "00".equals(errorCode);
	}

	// Get a message based on the success or failure of the transaction
	public String getMessage() {
		if (isSuccess()) {
			return "Payment was successful.";
		} else {
			return "Payment failed with error code: " + errorCode;
		}
	}

}

