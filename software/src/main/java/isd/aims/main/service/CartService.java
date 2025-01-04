package isd.aims.main.service;

import isd.aims.main.entity.cart.Cart;
import isd.aims.main.entity.cart.CartMedia;
import isd.aims.main.entity.media.Media;

import java.sql.SQLException;

public class CartService {
    public static CartMedia checkMediaInCart(Media media){
        return Cart.getCart().checkMediaInCart(media);
    }

    public static void emptyCart(){
        Cart.getCart().emptyCart();
    }

    public static void checkCartAvailability() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

}
