package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // ⚠️ ඔයාගේ MySQL Port එක 3306 නෙවෙයි වෙන එකක් නම් (XAMPP එකේ පේනවා) ඒක වෙනස් කරන්න.
    // ⚠️ ඔයා MySQL වලට Password එකක් දාලා තියෙනවා නම් "" වෙනුවට ඒක ලියන්න.
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database එක සමඟ සාර්ථකව සම්බන්ධ විය!");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL Driver එක සොයාගත නොහැක: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database සම්බන්ධතාවය අසාර්ථකයි: " + e.getMessage());
            }
        }
        return connection;
    }
}