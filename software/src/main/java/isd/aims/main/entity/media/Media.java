package isd.aims.main.entity.media;

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

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());

    protected Statement stm;
    protected int id;
    protected String description;
    protected String title;
    protected String category;
    protected String barcode;
    protected String dimension;
    protected float weight;
    protected String warehouseEntryDate;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;
    protected boolean isAvailableForRush;

    public Media() throws SQLException{
        stm = DBConnection.getConnection().createStatement();
    }

    public Media (int id, String description, String title, String category, String barcode, String dimension, float weight, String warehouseEntryDate, int price, int quantity, String type, boolean isAvailableForRush) throws SQLException{
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

        //stm = DBConnection.getConnection().createStatement();
    }

    public int getQuantity() throws SQLException{
        int updated_quantity = getMediaById(id).quantity;
        this.quantity = updated_quantity;
        return updated_quantity;
    }

    public Media getMediaById(int id) throws SQLException{
        String sql = "SELECT * FROM Media ;";
        Statement stm = DBConnection.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
		if(res.next()) {

            return new Media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"));
        }
        return null;
    }

    public List getAllMedia() throws SQLException{
        Statement stm = DBConnection.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Media");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Media media = new Media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"));
            medium.add(media);
        }
        return medium;
    }

    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = DBConnection.getConnection().createStatement();
        if (value instanceof String){
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " " 
                          + field + "=" + value + " " 
                          + "where id=" + id + ";");
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    private Media setId(int id){
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
        return isAvailableForRush;
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