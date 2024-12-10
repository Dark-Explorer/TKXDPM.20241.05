package isd.aims.main.entity.cart;


import isd.aims.main.entity.media.Media;

public class CartMedia {
    
    private Media media;
    private int quantity;
    private int price;
    private boolean isRush;

    public CartMedia(){

    }

    public CartMedia(Media media, Cart cart, int quantity, int price, boolean isRush) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
        this.isRush = isRush;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public boolean isRush() {
        return isRush;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" 
            + " media='" + media + "'" 
            + ", quantity='" + quantity + "'" 
            + "}";
    }

}

    
