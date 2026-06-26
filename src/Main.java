
import database.DBConnection;
import java.sql.Connection;
import javax.swing.SwingUtilities;
import modules.Login;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Hospital Management System...");

        // Check Database Connection
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("Database Connected Successfully!");
            
            // Run UI on Safe Thread
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Login loginWindow = new Login();
                    loginWindow.setVisible(true);
                }
            });
        } else {
            System.out.println("Database Connection Failed!");
        }
    }
}