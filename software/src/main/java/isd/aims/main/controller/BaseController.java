package isd.aims.main.controller;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.media.Media;

import java.util.List;

/**
 * This class is the base controller for our AIMS project
 * @author nguyenlm
 */
public class BaseController {

    /**
     * The method checks whether the Media in Cart, if it were in, we will return the CartMedia else return null
     * @param media
     * @return CartMedia or null
     */
    // Lớp phụ thuộc trực tiếp vào một implementation cụ thể (Cart) thay vì một abstraction  => SOLID: DIP
    // Lớp BaseController không chỉ chịu trách nhiệm điều hướng (controller logic), mà còn trực tiếp
    // xử lý logic liên quan đến Cart => Phụ thuộc vào lớp Cart => SOLID: OCP, SRP
    // => Sử dụng một lớp trung gian như CartService với interface để trừu tượng hóa các thao tác liên quan đến Cart
    public CartMedia checkMediaInCart(Media media){
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart
     * @return List[CartMedia]
     */
    @SuppressWarnings("rawtypes")
    public List getListCartMedia(){
        return Cart.getCart().getListMedia();
    }
}
