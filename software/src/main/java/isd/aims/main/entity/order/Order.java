package isd.aims.main.entity.order;

import isd.aims.main.entity.info.DeliveryInfo;
import isd.aims.main.entity.info.RushInfo;
import isd.aims.main.utils.Configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {

    private int shippingFees;
    private List lstOrderMedia;
    private Integer id;
    private DeliveryInfo deliveryInfo;
    private RushInfo rushInfo;

    public Order(){
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public RushInfo getRushInfo() {
        return this.rushInfo;
    }

    public void setRushInfo(RushInfo rushInfo) {
        this.rushInfo = rushInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice() * om.getQuantity();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
    }
}
