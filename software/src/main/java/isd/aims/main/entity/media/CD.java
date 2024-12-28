package isd.aims.main.entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CD extends Media {

    String artist;
    String recordLabel;
    String trackList;
    String musicType;
    Date releasedDate;

    public CD() throws SQLException{

    }

    public CD(int id, String description, String title, String category, String barcode, String dimension, float weight, String warehouseEntryDate, int price, int quantity, String type, boolean isAvailableForRush, String artist,
            String recordLabel, String trackList, String musicType, Date releasedDate) throws SQLException{
        super(id, description, title, category, barcode, dimension, weight, warehouseEntryDate, price, quantity, type, isAvailableForRush);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
        this.trackList = trackList;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " artist='" + artist + "'" + ", recordLabel='" + recordLabel + "'"
                + "'" + ", musicType='" + musicType + "'" + ", releasedDate='"
                + releasedDate + "'" + "}";
    }

    // thực hiện nhiều nhiệm vụ: truy vấn sql, xử lý kết quả và tạo đối tượng
    // => Tách logic truy vấn SQL và tạo đối tượng thành các phương thức riêng biệt để tăng tính tái sử dụng
    // và dễ bảo trì.
    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                     "aims.CD " +
                     "INNER JOIN aims.Media " +
                     "ON Media.id = CD.id " +
                     "where Media.id = " + id + ";";
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
