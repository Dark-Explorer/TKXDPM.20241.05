package CartTest;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.media.Media;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartAddTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        // Lấy instance của giỏ hàng và làm rỗng giỏ trước mỗi test
        cart = Cart.getCart();
        cart.emptyCart();
    }

    @Test
    void testAddCartMedia() {
        // Tạo một đối tượng Media giả
        Media media = new Media()
                .setId(1)
                .setTitle("Book")
                .setCategory("Education")
                .setPrice(100)
                .setQuantity(10)
                .setType("Physical")
                .setAvailableForRush(true);

        // Tạo một đối tượng CartMedia từ Media
        CartMedia cartMedia = new CartMedia(media, cart, 2, media.getPrice(), false);

        // Thêm CartMedia vào giỏ
        cart.addCartMedia(cartMedia);

        // Kiểm tra kết quả
        Assertions.assertEquals(1, cart.getListMedia().size());
        Assertions.assertEquals(cartMedia, cart.getListMedia().get(0));
        Assertions.assertEquals(2, cart.getListMedia().get(0).getQuantity());
    }

    @Test
    void testCheckMediaInCart() {
        // Tạo hai đối tượng Media giả
        Media media1 = new Media()
                .setId(1)
                .setTitle("Book")
                .setCategory("Education")
                .setPrice(100)
                .setQuantity(10)
                .setType("Physical")
                .setAvailableForRush(true);

        Media media2 = new Media()
                .setId(2)
                .setTitle("CD")
                .setCategory("Music")
                .setPrice(200)
                .setQuantity(5)
                .setType("Digital")
                .setAvailableForRush(false);

        // Tạo CartMedia từ Media
        CartMedia cartMedia1 = new CartMedia(media1, cart, 3, media1.getPrice(), true);

        // Thêm vào giỏ
        cart.addCartMedia(cartMedia1);

        // Kiểm tra kết quả
        Assertions.assertNotNull(cart.checkMediaInCart(media1)); // Media đã được thêm
        Assertions.assertNull(cart.checkMediaInCart(media2));    // Media chưa được thêm
    }

    @Test
    void testCheckIfAvailableForRush() {
        // Tạo một đối tượng Media giả
        Media media = new Media()
                .setId(1)
                .setTitle("Book")
                .setCategory("Education")
                .setPrice(100)
                .setQuantity(10)
                .setType("Physical")
                .setAvailableForRush(true);

        // Tạo CartMedia từ Media
        CartMedia cartMedia = new CartMedia(media, cart, 1, media.getPrice(), true);

        // Kiểm tra kết quả
        Assertions.assertTrue(cartMedia.checkIfAvailableForRush());
    }
}
