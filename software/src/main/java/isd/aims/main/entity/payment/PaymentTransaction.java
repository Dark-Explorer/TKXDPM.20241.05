package isd.aims.main.entity.payment;

import isd.aims.main.dao.PaymentDAO;
import isd.aims.main.entity.db.DBConnection;

import java.sql.*;
import java.util.Date;

// Phương thức save và checkPaymentByOrderId không tập trung vào nhiệm vụ chính => Logical Cohesion
// Tách ra 1 lớp DAO khác chuyên xử lý các truy vấn cơ sở dữ liệu
// FIXED
public class PaymentTransaction {
	private String errorCode;
	private String transactionId;
	private String transactionContent;
	private long amount;
	private Integer orderID;
	private Date createdAt;

	PaymentDAO paymentDAO;

	public PaymentTransaction() {
		this.paymentDAO = new PaymentDAO();
	}

	public PaymentTransaction(String errorCode, String transactionId, String transactionContent,
							  long amount, Date createdAt) {
		super();
		this.errorCode = errorCode;
		this.transactionId = transactionId;
		this.transactionContent = transactionContent;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	// Content Coupling
	// sử dụng lớp DBconnection để kết nối và thực hiện các thao tác với cơ sở dữ liệu
	// => phụ thuộc vào lớp cơ sở dữ liệu
	// => tách riêng ra 1 lớp riêng để giảm sự phụ thuộc (ví dụ PaymentTransactionDAO)
	// FIXED
	public void save(int orderId) throws SQLException {
		this.orderID = orderId;
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		paymentTransaction.paymentDAO.saveTransaction(orderId, createdAt, transactionContent, amount);
	}

	// Content Coupling
	// sử dụng lớp DBconnection để kết nối và thực hiện các thao tác với cơ sở dữ liệu
	// => phụ thuộc vào lớp cơ sở dữ liệu
	// => tách riêng ra 1 lớp riêng để giảm sự phụ thuộc (ví dụ PaymentTransactionDAO)
	// FIXED
	public int checkPaymentByOrderId(int orderId) throws SQLException {
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		return paymentTransaction.paymentDAO.countTransactionsByOrderId(orderId);
	}

	public boolean isSuccess() {
		return errorCode == null || "00".equals(errorCode);
	}

	public String getMessage() {
		if (isSuccess()) {
			return "Payment was successful.";
		} else {
			return "Payment failed with error code: " + errorCode;
		}
	}

}

