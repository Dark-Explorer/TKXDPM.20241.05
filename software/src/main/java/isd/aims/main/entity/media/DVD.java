package isd.aims.main.entity.media;

import isd.aims.main.dao.DVDDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

// Lớp DVD có các phương thức liên quan đến thông tin chung về một DVD, nhưng chưa có sự nhóm lại các
// => Communicational Cohesion
// => Các phương thức có thể được nhóm lại thành các nhóm hợp lý hơn để tăng tính đóng gói và giảm sự phân tán
// FIXED
public class DVD extends Media {

    String discType;
    String director;
    int runtime;
    String studio;
    String subtitles;
    Date releasedDate;
    String filmType;
    String language;

    DVDDAO dvdDAO;

    public DVD() throws SQLException{
        this.dvdDAO = new DVDDAO();
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

    @Override
    public String toString() {
        return "{" + super.toString() + " discType='" + discType + "'" + ", director='" + director + "'" + ", runtime='"
                + runtime + "'" + ", studio='" + studio + "'" + ", subtitles='" + subtitles + "'" + ", releasedDate='"
                + releasedDate + "'" + ", filmType='" + filmType + "'" + "}";
    }

    // SOLID: SRP
    // thực hiện nhiều nhiệm vụ: truy vấn sql, xử lý kết quả và tạo đối tượng => Sequential Cohesion
    // => Tách logic truy vấn SQL và tạo đối tượng thành các phương thức riêng biệt để tăng tính tái sử dụng
    // và dễ bảo trì.
    // Phương thức getMediaById không phù hợp với lớp DVD => Logical Cohesion
    // => Tách ra lớp khác ( ví dụ: lớp DAO)
    // FIXED
    @Override
    public Media getMediaById(int id) throws SQLException {
        return dvdDAO.getMediaById(id);
    }

    @Override
    public List getAllMedia() {
        return dvdDAO.getAllMedia();
    }
}
