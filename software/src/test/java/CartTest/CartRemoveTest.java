package CartTest;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.media.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartRemoveTest {

    private Cart cart;
    private Media media;

    @BeforeEach
    void setUp() {
        // Khởi tạo Cart mới và đối tượng Media trước mỗi test
        cart = Cart.getCart();

        // Tạo đối tượng Media giả để thêm vào giỏ hàng
        media = new Media()
                .setId(1)
                .setTitle("Movie")
                .setCategory("Entertainment")
                .setPrice(50)
                .setQuantity(5)
                .setType("Digital")
                .setAvailableForRush(true);
    }

    @Test
    void testRemoveCartMedia() {
        // Tạo đối tượng CartMedia và thêm vào giỏ hàng
        CartMedia cartMedia = new CartMedia(media, cart, 2, media.getPrice(), false);
        cart.addCartMedia(cartMedia);

        // Kiểm tra giỏ hàng trước khi xóa
        assertEquals(1, cart.getListMedia().size(), "Giỏ hàng phải chứa 1 sản phẩm.");
        assertTrue(cart.getListMedia().contains(cartMedia), "Giỏ hàng phải chứa CartMedia.");

        // Xóa CartMedia khỏi giỏ hàng
        cart.removeCartMedia(cartMedia);

        // Kiểm tra giỏ hàng sau khi xóa
        assertEquals(0, cart.getListMedia().size(), "Giỏ hàng phải trống sau khi xóa.");
        assertFalse(cart.getListMedia().contains(cartMedia), "Giỏ hàng không được chứa CartMedia sau khi xóa.");
    }

    @Test
    void testRemoveNonExistentCartMedia() {
        // Tạo đối tượng CartMedia giả không có trong giỏ hàng
        CartMedia cartMedia = new CartMedia(media, cart, 2, media.getPrice(), false);

        // Kiểm tra giỏ hàng trước khi xóa
        assertEquals(0, cart.getListMedia().size(), "Giỏ hàng phải trống ban đầu.");

        // Thử xóa CartMedia không có trong giỏ hàng
        cart.removeCartMedia(cartMedia);

        // Giỏ hàng vẫn phải trống sau khi thử xóa
        assertEquals(0, cart.getListMedia().size(), "Giỏ hàng không thay đổi sau khi xóa sản phẩm không có trong giỏ.");
    }
}
