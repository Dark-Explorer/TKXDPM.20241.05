package isd.aims.main.entity.cart;

import isd.aims.main.exception.MediaNotAvailableException;
import isd.aims.main.entity.media.Media;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// SOLID: SRP
// lớp chịu nhiều trách nhiệm: quản lý giỏ hàng, tính toán tổng số lượng, kiểm tra tính khả dụng, xử lý ngoại lệ.
// => Tách trách nhiệm thành các lớp riêng như CartCalculator và ProductAvailabilityChecker
public class Cart {
    
    private List<CartMedia> lstCartMedia;
    private static Cart cartInstance;

    public static Cart getCart(){
        if(cartInstance == null) cartInstance = new Cart();
        return cartInstance;
    }

    private Cart(){
        lstCartMedia = new ArrayList<>();
    }

    public void addCartMedia(CartMedia cm){
        lstCartMedia.add(cm);
    }

    public void removeCartMedia(CartMedia cm){
        lstCartMedia.remove(cm);
    }

    public List getListMedia(){
        return lstCartMedia;
    }

    // Phương thức emptyCart() và checkAvailabilityOfProduct() có thể được gọi gần nhau
    // trong một khoảng thời gian cụ thể (ví dụ, khi người dùng đặt hàng). => Cohesion: Temporal Cohesion
    // => Tách chức năng kiểm tra tính khả dụng và xóa giỏ hàng thành các service độc lập
    public void emptyCart(){
        lstCartMedia.clear();
    }

    public int getTotalMedia(){
        int total = 0;
        for (CartMedia obj : lstCartMedia) {
            total += obj.getQuantity();
        }
        return total;
    }

    public int calSubtotal(){
        int total = 0;
        for (CartMedia obj : lstCartMedia) {
            total += obj.getPrice()* obj.getQuantity();
        }
        return total;
    }
    // SOLID: OCP
    // Lớp không dễ mở rộng vì logic kiểm tra tính khả dụng được viết cứng trong
    // phương thức checkAvailabilityOfProduct
    // => Sử dụng interface hoặc lớp trừu tượng để quản lý các quy tắc kiểm tra sản phẩm.
    public void checkAvailabilityOfProduct() throws SQLException{
        boolean allAvai = true;
        for (CartMedia object : lstCartMedia) {
            int requiredQuantity = object.getQuantity();
            int availQuantity = object.getMedia().getQuantity();
            if (requiredQuantity > availQuantity) allAvai = false;
        }
        if (!allAvai) throw new MediaNotAvailableException("Some media not available");
    }

    public CartMedia checkMediaInCart(Media media){
        for (CartMedia cartMedia : lstCartMedia) {
            if (cartMedia.getMedia().getId() == media.getId()) return cartMedia;
        }
        return null;
    }

}
