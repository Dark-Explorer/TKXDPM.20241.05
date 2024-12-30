package isd.aims.main.dao;

import isd.aims.main.entity.db.DBConnection;
import isd.aims.main.entity.media.Media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MediaDAO {

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media WHERE id = " + id + ";";
        Statement stm = DBConnection.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            return new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"))
                    .setAvailableForRush(res.getBoolean("isAvailableForRush"))
                    .setWeight(res.getFloat("weight"));
        }
        return null;
    }

    public List<Media> getAllMedia() throws SQLException {
        Statement stm = DBConnection.getConnection().createStatement();
        ResultSet res = stm.executeQuery("SELECT * FROM Media");
        List<Media> medium = new ArrayList<>();
        while (res.next()) {
            Media media = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"))
                    .setAvailableForRush(res.getBoolean("isAvailableForRush"))
                    .setWeight(res.getFloat("weight"));
            medium.add(media);
        }
        return medium;
    }
}