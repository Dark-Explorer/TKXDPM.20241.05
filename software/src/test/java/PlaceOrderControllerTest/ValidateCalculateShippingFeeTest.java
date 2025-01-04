package PlaceOrderControllerTest;

import isd.aims.main.controller.PlaceOrderController;
import isd.aims.main.entity.info.DeliveryInfo;
import isd.aims.main.entity.media.Media;
import isd.aims.main.entity.order.Order;
import isd.aims.main.entity.order.OrderMedia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateCalculateShippingFeeTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    @Test
    void testCalculateShippingFee_NoRushItems() {
        Order order = new Order();
        order.setDeliveryInfo(new DeliveryInfo(null, null,"Hà Nội", null, null, null));

        Media media1 = new Media();
        media1.setWeight(2.0f);
        OrderMedia orderMedia1 = new OrderMedia(media1, 1, 50000, false);

        Media media2 = new Media();
        media2.setWeight(1.0f);
        OrderMedia orderMedia2 = new OrderMedia(media2, 1, 30000, false);

        order.getlstOrderMedia().add(orderMedia1);
        order.getlstOrderMedia().add(orderMedia2);

        int shippingFee = placeOrderController.calculateShippingFee(order);
        assertEquals(22000, shippingFee);
    }

    @Test
    void testCalculateShippingFee_WithRushItems() {
        Order order = new Order();
        order.setDeliveryInfo(new DeliveryInfo(null, null,"Hà Nội", null, null, null));

        Media media1 = new Media();
        media1.setWeight(2.0f);
        OrderMedia orderMedia1 = new OrderMedia(media1, 1, 50000, true);

        Media media2 = new Media();
        media2.setWeight(1.0f);
        OrderMedia orderMedia2 = new OrderMedia(media2, 2, 30000, false);

        order.getlstOrderMedia().add(orderMedia1);
        order.getlstOrderMedia().add(orderMedia2);

        int shippingFee = placeOrderController.calculateShippingFee(order);
        assertEquals(54000, shippingFee);
    }

    @Test
    void testCalculateShippingFee_HighValueOrder() {
        Order order = new Order();
        order.setDeliveryInfo(new DeliveryInfo(null, null,"Hà Nội", null, null, null));

        Media media1 = new Media();
        media1.setWeight(2.0f);
        OrderMedia orderMedia1 = new OrderMedia(media1, 1, 150000, false);

        order.getlstOrderMedia().add(orderMedia1);

        int shippingFee = placeOrderController.calculateShippingFee(order);
        assertEquals(0, shippingFee);
    }

    @Test
    void testCalculateShippingFee_DifferentProvince() {
        Order order = new Order();
        order.setDeliveryInfo(new DeliveryInfo(null, null,"Đà Nẵng", null, null, null));

        Media media1 = new Media();
        media1.setWeight(2.0f);
        OrderMedia orderMedia1 = new OrderMedia(media1, 1, 50000, false);

        order.getlstOrderMedia().add(orderMedia1);

        int shippingFee = placeOrderController.calculateShippingFee(order);
        assertEquals(37500, shippingFee);
    }
}
