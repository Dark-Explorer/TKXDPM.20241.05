package isd.aims.main.controller;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.invoice.Invoice;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import isd.aims.main.service.CartService;
import isd.aims.main.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */

public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void checkCartAvailability() throws SQLException{
        CartService.checkCartAvailability();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    // Lớp này phụ thuộc trực tiếp vào Cart và các chi tiết implementation của nó.
    // Điều này làm giảm khả năng thay đổi hoặc thay thế Cart. => SOLID: DIP
    // => Tạo interface trừu tượng (ví dụ: ICartService) để quản lý các thao tác trên giỏ hàng.
    // Controller sẽ phụ thuộc vào abstraction này thay vì chi tiết cụ thể của lớp Cart.
    // FIXED
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                                                   cartMedia.getQuantity(),
                                                   cartMedia.getPrice(),
                                                    cartMedia.isRush());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{

    }

    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.charAt(0) != '0') return false;
        char separator = 'k';
        int isPreviousSeparator = 0;
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == '-' || c == '/' || c == '.') {
                    if (isPreviousSeparator == 1) return false;
                    isPreviousSeparator = 1;
                    if (separator != 'k' && separator != c) return false;
                    separator = c;
                } else return false;
            } else {
                isPreviousSeparator = 0;
            }
        }
        return true;
    }

    public boolean validateName(String name) {
        if (name == null || name.length() > 30) return false;
        for (char c : name.toCharArray()) {
            if (!Character.isAlphabetic(c) && c != ' ') return false;
        }
        return true;
    }

    public boolean validateAddress(String address) {
        if (address == null || address.length() > 100) return false;
        for (char c : address.toCharArray()) {
            if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != ' ' && c != '/') return false;
        }
        return true;
    }


    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        int shippingFee = 0;
        int totalValue = 0;
        int numberOfRushItems = 0;

        float weightOfHeaviestNoRushItem = 0f;
        float weightOfHeaviestRushItem = 0f;

        for (Object object : order.getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            if (!om.isRush()) {
                totalValue += om.getPrice() * om.getQuantity();
                weightOfHeaviestNoRushItem = Math.max(weightOfHeaviestNoRushItem, om.getMedia().getWeight());
            } else {
                weightOfHeaviestRushItem = Math.max(weightOfHeaviestRushItem, om.getMedia().getWeight());
                numberOfRushItems++;
            }
        }

        shippingFee += calculateWithWeight(weightOfHeaviestNoRushItem, order.getDeliveryInfo().getProvince());
        shippingFee += calculateWithWeight(weightOfHeaviestRushItem, order.getDeliveryInfo().getProvince());
        shippingFee += 10000 * numberOfRushItems;

        if (totalValue > 100000) {
            shippingFee = Math.max(0, shippingFee - 25000);
        }

        return shippingFee;
    }

    private int calculateWithWeight(float weight, String province) {
        if (weight == 0f) return 0;

        int fee;
        if (province.equals("Hà Nội") || province.equals("Hồ Chí Minh")) {
            fee = 22000;
            weight -= 3;
        } else {
            fee = 30000;
            weight -= 0.5f;
        }

        if (weight > 0) {
            fee += (int) (2500 * Math.ceil(weight * 2));
        }

        return fee;
    }
}
