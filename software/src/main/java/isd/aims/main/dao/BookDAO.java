package isd.aims.main.dao;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.Book;
import isd.aims.main.entity.media.Media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDAO extends MediaDAO {
    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "aims.Book " +
                "INNER JOIN aims.Media " +
                "ON Media.id = Book.id " +
                "where Media.id = " + id + ";";
        Statement stm = DBConnection.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {

            // from Media table
            String title = "";
            String type = res.getString("type");
            String description = res.getString("description");
            String barcode = res.getString("barcode");
            String dimension = res.getString("dimension");
            float weight = res.getFloat("weight");
            String warehouseEntryDate = res.getString("warehouseEntryDate");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");
            boolean isAvailableForRush = res.getBoolean("isAvailableForRush");

            // from Book table
            String author = res.getString("author");
            String coverType = res.getString("coverType");
            String publisher = res.getString("publisher");
            Date publishDate = res.getDate("publishDate");
            int numOfPages = res.getInt("numOfPages");
            String language = res.getString("language");
            String bookCategory = res.getString("bookCategory");

            return new Book(id, description, title, category, barcode, dimension, weight, warehouseEntryDate, price, quantity, type, isAvailableForRush, author, coverType, publisher, publishDate, numOfPages, language, bookCategory);

        } else {
            throw new SQLException();
        }
    }

    @Override
    public List getAllMedia() {
        return null;
    }
}