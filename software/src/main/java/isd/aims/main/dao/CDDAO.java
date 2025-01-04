package isd.aims.main.dao;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.CD;
import isd.aims.main.entity.media.Media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class CDDAO extends MediaDAO{
    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                "aims.CD " +
                "INNER JOIN aims.Media " +
                "ON Media.id = CD.id " +
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

            // from CD table
            String artist = res.getString("artist");
            String trackList = res.getString("trackList");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");

            return new CD(id, description, title, category, barcode, dimension, weight, warehouseEntryDate, price, quantity, type, isAvailableForRush,
                    artist, recordLabel, trackList, musicType, releasedDate);

        } else {
            throw new SQLException();
        }
    }

    @Override
    public List getAllMedia() {
        return null;
    }
}
