package isd.aims.main.entity.media;

import isd.aims.main.dao.CDDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

// Cohesion: Communicational Cohesion
// các phương thức  như getArtist, getRecordLabel, getMusicType, getReleasedDate liên quan đến thông tin của CD
// => Các phương thức có thể được nhóm lại thành các nhóm hợp lý hơn để tăng tính đóng gói và giảm sự phân tán
public class CD extends Media {

    String artist;
    String recordLabel;
    String trackList;
    String musicType;
    Date releasedDate;

    CDDAO cdDAO;

    public CD() throws SQLException{
        this.cdDAO = new CDDAO();
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

    // SOLID: SRP
    // thực hiện nhiều nhiệm vụ: truy vấn sql, xử lý kết quả và tạo đối tượng => Sequential Cohesion
    // => Tách logic truy vấn SQL và tạo đối tượng thành các phương thức riêng biệt để tăng tính tái sử dụng
    // và dễ bảo trì.
    // Phương thức getMediaById không phù hợp với lớp CD => Logical Cohesion
    // => Tách ra lớp khác ( ví dụ: lớp DAO)
    // FIXED
    @Override
    public Media getMediaById(int id) throws SQLException {
        return cdDAO.getMediaById(id);
    }

    @Override
    public List getAllMedia() {
        return cdDAO.getAllMedia();
    }

}
