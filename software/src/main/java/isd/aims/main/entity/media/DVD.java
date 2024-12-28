package isd.aims.main.entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DVD extends Media {

    String discType;
    String director;
    int runtime;
    String studio;
    String subtitles;
    Date releasedDate;
    String filmType;
    String language;

    public DVD() throws SQLException{

    }

    public DVD(int id, String description, String title, String category, String barcode, String dimension, float weight, String warehouseEntryDate, int price, int quantity, String type, boolean isAvailableForRush,
               String discType, String director, int runtime, String studio, String subtitles, Date releasedDate, String filmType, String language) throws SQLException{
        super(id, description, title, category, barcode, dimension, weight, warehouseEntryDate, price, quantity, type, isAvailableForRush);
        this.discType = discType;
        this.director = director;
        this.runtime = runtime;
        this.studio = studio;
        this.subtitles = subtitles;
        this.releasedDate = releasedDate;
        this.filmType = filmType;
        this.language = language;
    }

    public String getDiscType() {
        return this.discType;
    }

    public DVD setDiscType(String discType) {
        this.discType = discType;
        return this;
    }

    public String getDirector() {
        return this.director;
    }

    public DVD setDirector(String director) {
        this.director = director;
        return this;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public DVD setRuntime(int runtime) {
        this.runtime = runtime;
        return this;
    }

    public String getStudio() {
        return this.studio;
    }

    public DVD setStudio(String studio) {
        this.studio = studio;
        return this;
    }

    public String getSubtitles() {
        return this.subtitles;
    }

    public DVD setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public DVD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    public String getFilmType() {
        return this.filmType;
    }

    public DVD setFilmType(String filmType) {
        this.filmType = filmType;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " discType='" + discType + "'" + ", director='" + director + "'" + ", runtime='"
                + runtime + "'" + ", studio='" + studio + "'" + ", subtitles='" + subtitles + "'" + ", releasedDate='"
                + releasedDate + "'" + ", filmType='" + filmType + "'" + "}";
    }

    // thực hiện nhiều nhiệm vụ: truy vấn sql, xử lý kết quả và tạo đối tượng
    // => Tách logic truy vấn SQL và tạo đối tượng thành các phương thức riêng biệt để tăng tính tái sử dụng
    // và dễ bảo trì.
    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                     "aims.DVD " +
                     "INNER JOIN aims.Media " +
                     "ON Media.id = DVD.id " +
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
