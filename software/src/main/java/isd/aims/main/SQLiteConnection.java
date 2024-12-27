package isd.aims.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteConnection {
    public static void main(String[] args) {
        // Đường dẫn đến file database SQLite
        String url = "jdbc:sqlite:/Users/nguyenvanduc/sqlite/my_database.db"; // Thay đường dẫn bằng đường dẫn thực tế

        // Kết nối với cơ sở dữ liệu
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Kết nối thành công!");

                // Tạo một Statement để truy vấn
                String query = "SELECT * FROM students";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                // Lấy dữ liệu từ bảng
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Age: " + rs.getInt("age"));
                    System.out.println("-------------------");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

