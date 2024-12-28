package isd.aims.main.controller;

import isd.aims.main.InterbankSubsystem.IPayment;
import isd.aims.main.InterbankSubsystem.vnPay.VnPaySubsystemController;
import isd.aims.main.entity.payment.PaymentTransaction;
import isd.aims.main.entity.cart.Cart;
import isd.aims.main.listener.TransactionResultListener;

import java.io.IOException;
import java.sql.SQLException;


/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 *
 */

public class PaymentController extends BaseController implements TransactionResultListener {

	private IPayment paymentService;
	private int orderId;

	public PaymentController(IPayment vnPayService) {
		this.paymentService = vnPayService;
	}

	/**
	 * Generate VNPay payment URL
	 */

	public void payOrder(int orderId, int amount, String orderInfo) throws IOException, SQLException {
		this.orderId = orderId;
		// Bắt đầu quy trình thanh toán
		new VnPaySubsystemController(this).payOrder(amount, orderInfo);
	}

	// Lớp PaymentController vừa xử lý luồng thanh toán, vừa chịu trách nhiệm làm trống giỏ hàng
	// bằng cách gọi trực tiếp Cart.emptyCart().
	// => Tách logic liên quan đến giỏ hàng vào một service riêng như CartService
	@Override
	public void onTransactionCompleted(PaymentTransaction transactionResult) {
		if (transactionResult != null && transactionResult.isSuccess()) {
			try {
				transactionResult.save(orderId); // Lưu giao dịch vào cơ sở dữ liệu nếu thành công
				emptyCart(); // Làm trống giỏ hàng
				System.out.println("Lưu thành công");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Giao dịch thất bại: " + (transactionResult != null ? transactionResult.getMessage() : "Lỗi không xác định"));
		}
	}

	public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}
