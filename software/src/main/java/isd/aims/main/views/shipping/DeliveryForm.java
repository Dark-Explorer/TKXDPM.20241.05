package isd.aims.main.views.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import isd.aims.main.exception.InvalidDeliveryInfoException;
import isd.aims.main.controller.PlaceOrderController;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.order.Order;
import isd.aims.main.utils.Configs;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.invoice.InvoiceForm;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DeliveryForm extends BaseForm implements Initializable {

	@FXML
	private Label screenTitle;

	@FXML
	private TextField name;

	@FXML
	private TextField phone;

	@FXML
	private TextField address;

	@FXML
	private TextField instructions;

	@FXML
	private ComboBox<String> province;

	@FXML
	private TextField rushTime;

	@FXML
	private TextField rushNote;

	private Order order;

	public DeliveryForm(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;

		if (order.getRushInfo() == null) {
			rushNote.setDisable(true);
			rushTime.setDisable(true);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		this.province.getItems().addAll(Configs.PROVINCES);
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

		// Lấy thông tin từ các trường
		String nameText = name.getText();
		String phoneText = phone.getText();
		String addressText = address.getText();
		String instructionsText = instructions.getText();
		String provinceText = province.getValue();

		// Kiểm tra các trường bắt buộc
		if (!validateRequiredFields(nameText, phoneText, addressText, provinceText))
			return;

		// Kiểm tra tính hợp lệ của tên
		if (!validateField(nameText, "Tên không hợp lệ",
							getBController().validateName(nameText)))
			return;

		// Kiểm tra tính hợp lệ của địa chỉ
		if (!validateField(addressText, "Địa chỉ không hợp lệ",
							getBController().validateAddress(addressText)))
			return;

		// Kiểm tra tính hợp lệ của số điện thoại
		if (!validateField(phoneText, "Số điện thoại không hợp lệ",
							getBController().validatePhoneNumber(phoneText)))
			return;

		// Kiểm tra Rush Info
		if (order.getRushInfo() != null) {
			String rushTimeText = rushTime.getText();

			// Kiểm tra Rush Time
			if (rushTimeText.isEmpty()) {
				showAlert("Rush time không thể để trống");
				return;
			}
		}


		// add info to messages
		HashMap<String, String> messages = new HashMap<>();
		messages.put("name", nameText);
		messages.put("phone", phoneText);
		messages.put("address", addressText);
		messages.put("instructions", instructionsText);
		messages.put("province", provinceText);

		try {
			// process and validate delivery info
			getBController().processDeliveryInfo(messages);
		} catch (InvalidDeliveryInfoException e) {
			throw new InvalidDeliveryInfoException(e.getMessage());
		}

		// Handle rush information if applicable
    if (order.getRushInfo() != null) {
        // Nếu là đơn hàng rush, gán rushInfo từ giao diện
        String rushTimeText = rushTime.getText();
        String rushNoteText = rushNote.getText();

        // Gán rushTime và rushNote vào RushInfo
        order.getRushInfo().setInstruction(rushNoteText);
        order.getRushInfo().setTimeDelivery(LocalDateTime.parse(rushTimeText));
    }

		// calculate shipping fees
		int shippingFees = getBController().calculateShippingFee(order);
		order.setShippingFees(shippingFees);
		order.setDeliveryInfo(null);

		// create invoice screen
		Invoice invoice = getBController().createInvoice(order);
		BaseForm InvoiceScreenHandler = new InvoiceForm(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
		InvoiceScreenHandler.setPreviousScreen(this);
		InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
		InvoiceScreenHandler.setScreenTitle("Invoice Screen");
		InvoiceScreenHandler.setBController(getBController());
		InvoiceScreenHandler.show();
	}

	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Lỗi");
		alert.setHeaderText(null); // Không cần header
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean validateRequiredFields(String nameText, String phoneText, String addressText, String provinceText) {
		if (nameText.isEmpty() ||
				phoneText.isEmpty() ||
				addressText.isEmpty() ||
				provinceText == null) {
			showAlert("Các trường bắt buộc không thể để trống! Vui lòng điền đầy đủ thông tin như Tên, Số điện thoại, Địa chỉ và Tỉnh/Thành phố.");
			return false;
		}
		return true;
	}

	private boolean validateField(String fieldText, String validationMessage, boolean isValid) {
		if (!isValid) {
			showAlert(validationMessage);
			return false;
		}
		return true;
	}

}
