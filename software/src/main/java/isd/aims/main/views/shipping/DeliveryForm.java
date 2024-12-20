package isd.aims.main.views.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import isd.aims.main.exception.InvalidDeliveryInfoException;
import isd.aims.main.controller.PlaceOrderController;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.utils.Configs;
import isd.aims.main.views.BaseForm;
import isd.aims.main.views.invoice.InvoiceForm;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

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
	private HBox rushTime;

	@FXML
	private TextField rushNote;

	@FXML
	private ComboBox<Integer> hour;

	@FXML
	private ComboBox<Integer> minute;

	@FXML
	private DatePicker date;

	private Order order;

	public DeliveryForm(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;

		if (!containsRushMedia(order)) {
			rushNote.setDisable(true);
			minute.setDisable(true);
			hour.setDisable(true);
			date.setDisable(true);
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
		hour.setItems(FXCollections.observableArrayList(
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23
		));

		// Thêm các giá trị phút vào ComboBox
		minute.setItems(FXCollections.observableArrayList(
				0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55
		));
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
		if (containsRushMedia(order)){
			LocalDateTime rushDateTime = getValidFutureDateTime(date, hour, minute);
			if (rushDateTime == null)
				return;
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
		LocalDateTime rushDateTime = getValidFutureDateTime(date, hour, minute);
        String rushNoteText = rushNote.getText();

        // Gán rushTime và rushNote vào RushInfo
		order.getRushInfo().setTimeDelivery(rushDateTime);
		order.getRushInfo().setInstruction(rushNoteText);
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

	public static boolean containsRushMedia(Order order) {
		if (order == null || order.getlstOrderMedia() == null) {
			return false;  // Nếu order hoặc lstOrderMedia là null thì không có media nào
		}

		// Duyệt qua danh sách lstOrderMedia để kiểm tra xem có media nào có thể rush
		for (Object obj : order.getlstOrderMedia()) {
			System.out.println(order.getlstOrderMedia());
			if (obj instanceof OrderMedia) {
				OrderMedia om = (OrderMedia) obj;
				System.out.println(om.isRush());
				if (om.isRush()) {  // Kiểm tra xem media có là rush không
					return true;  // Nếu có media nào có thể rush, trả về true
				}
			}
		}
		return false;  // Nếu không có media nào có thể rush, trả về false
	}

	private LocalDateTime getValidFutureDateTime(DatePicker datePicker, ComboBox<Integer> hourComboBox, ComboBox<Integer> minuteComboBox) {
		// Kiểm tra giá trị của DatePicker
		LocalDate selectedDate = datePicker.getValue();
		if (selectedDate == null) {
			showAlert("Vui lòng chọn ngày.");
			return null;
		}

		// Kiểm tra giá trị của ComboBox
		Integer selectedHour = hourComboBox.getValue();
		Integer selectedMinute = minuteComboBox.getValue();
		if (selectedHour == null || selectedMinute == null) {
			showAlert("Vui lòng chọn giờ và phút.");
			return null;
		}

		// Chuyển đổi thành LocalDateTime và kiểm tra
		LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedHour, selectedMinute));

		// So sánh với thời gian hiện tại
		if (!selectedDateTime.isAfter(LocalDateTime.now())) {
			showAlert("Thời gian đã chọn phải nằm trong tương lai.");
			return null;
		}

		// Nếu hợp lệ, trả về thời gian đã chọn
		return selectedDateTime;
	}




}
