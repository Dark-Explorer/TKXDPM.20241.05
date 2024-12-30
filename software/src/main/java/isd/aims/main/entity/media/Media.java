package isd.aims.main.entity.media;

import isd.aims.main.dao.MediaDAO;
import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The general media class, for another media it can be done by inheriting this class
 * @author nguyenlm
 */
public class Media {

    protected int id;
    protected String description;
    protected String title;
    protected String category;
    protected String barcode;
    protected String dimension;
    protected float weight;
    protected String warehouseEntryDate;
    protected int value;
    protected int price;
    protected int quantity;
    protected String type;
    protected String imageURL;
    protected boolean isAvailableForRush;

    private MediaDAO mediaDAO;

    public Media() {
        this.mediaDAO = new MediaDAO();
    }

    public Media(int id, String description, String title, String category, String barcode, String dimension, float weight, String warehouseEntryDate, int price, int quantity, String type, boolean isAvailableForRush) {
        this();
        this.id = id;
        this.description = description;
        this.title = title;
        this.category = category;
        this.barcode = barcode;
        this.dimension = dimension;
        this.weight = weight;
        this.warehouseEntryDate = warehouseEntryDate;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.isAvailableForRush = isAvailableForRush;
    }

    public int getQuantity() throws SQLException {
        int updated_quantity = mediaDAO.getMediaById(id).quantity;
        this.quantity = updated_quantity;
        return updated_quantity;
    }

    // SOLID: SRP
    // sử dụng lớp DBconnection để kết nối và thực hiện các thao tác với cơ sở dữ liệu
    // => phụ thuộc vào lớp cơ sở dữ liệu
    // => tách riêng ra 1 lớp riêng để giảm sự phụ thuộc ( ví dụ MediaDAO )
    public Media getMediaById(int id) throws SQLException {
        return mediaDAO.getMediaById(id);
    }

    // sử dụng lớp DBconnection để kết nối và thực hiện các thao tác với cơ sở dữ liệu
    // => phụ thuộc vào lớp cơ sở dữ liệu
    // => tách riêng ra 1 lớp riêng để giảm sự phụ thuộc ( ví dụ MediaDAO )
    public List<Media> getAllMedia() throws SQLException {
        return mediaDAO.getAllMedia();
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    public Media setId(int id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public Media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public String getBarcode() {
        return barcode;
    }

    public float getWeight() {
        return weight;
    }

    public String getDimension() {
        return dimension;
    }

    public String getWarehouseEntryDate() {
        return warehouseEntryDate;
    }

    public boolean isAvailableForRush() {
        return this.isAvailableForRush;
    }

    public Media setAvailableForRush(boolean isAvailableForRush) {
        this.isAvailableForRush = isAvailableForRush;
        return this;
    }

    public Media setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", title='" + title + "'" +
            ", category='" + category + "'" +
            ", price='" + price + "'" +
            ", quantity='" + quantity + "'" +
            ", type='" + type + "'" +
            ", imageURL='" + imageURL + "'" +
            "}";
    }
}