package isd.aims.main.dao;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.DVD;
import isd.aims.main.entity.media.Media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class DVDDAO extends MediaDAO {
    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "aims.DVD " +
                "INNER JOIN aims.Media " +
                "ON Media.id = DVD.id " +
                "where Media.id = " + id + ";";
        Statement stm = DBConnection.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {

            // from media table
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

            // from DVD table
            String discType = res.getString("discType");
            String director = res.getString("director");
            int runtime = res.getInt("runtime");
            String studio = res.getString("studio");
            String subtitles = res.getString("subtitle");
            Date releasedDate = res.getDate("releasedDate");
            String filmType = res.getString("filmType");
            String language = res.getString("language");

            return new DVD(id, description, title, category, barcode, dimension, weight, warehouseEntryDate, price, quantity, type, isAvailableForRush, discType, director, runtime, studio, subtitles, releasedDate, filmType, language);

        } else {
            throw new SQLException();
        }
    }

    @Override
    public List getAllMedia() {
        return null;
    }
}
