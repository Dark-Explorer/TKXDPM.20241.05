package CartTest;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.media.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class CartSubtotalTest {

    private Cart cart;
    private CartMedia cartMedia1;
    private CartMedia cartMedia2;
    private Media media1;
    private Media media2;

    @BeforeEach
    void setUp() {
        // Lấy instance của giỏ hàng và làm rỗng giỏ trước mỗi test
        cart = Cart.getCart();
        cart.emptyCart();

        // Tạo các đối tượng Media giả
        media1 = new Media()
                .setId(1)
                .setTitle("Book 1")
                .setCategory("Education")
                .setPrice(100)
                .setQuantity(2)
                .setType("Physical")
                .setAvailableForRush(true);

        media2 = new Media()
                .setId(2)
                .setTitle("CD 1")
                .setCategory("Music")
                .setPrice(150)
                .setQuantity(1)
                .setType("Digital")
                .setAvailableForRush(false);

        // Tạo CartMedia từ các đối tượng Media
        cartMedia1 = new CartMedia(media1, cart, 2, media1.getPrice(), false);
        cartMedia2 = new CartMedia(media2, cart, 1, media2.getPrice(), false);

        // Thêm CartMedia vào giỏ
        cart.addCartMedia(cartMedia1);
        cart.addCartMedia(cartMedia2);
    }

    @Test
    void testCalSubtotal() {
        // Tính toán subtotal mong muốn
        int expectedSubtotal = (100 * 2) + (150 * 1);

        // Kiểm tra subtotal thực tế từ phương thức calSubtotal
        Assertions.assertEquals(expectedSubtotal, cart.calSubtotal(), "Subtotal calculation should be correct");
    }
}
