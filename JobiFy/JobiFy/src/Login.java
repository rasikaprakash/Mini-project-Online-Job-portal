import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jobify";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root123";
    static final Scanner sc = new Scanner(System.in);

    private static final String SELECT_QUERY = "SELECT id FROM users WHERE phone = ? AND password = ?";

    public static void log() {
        // Sample user login data
    	System.out.println("Enter your Credentials to login :");
    	System.out.println("Enter the phone number :");
        String phone = sc.next();
        System.out.println("Enter the Password :");
        String password = sc.next();

        // Call the login method to validate user credentials
        int userId = login(phone, password);

        if (userId != -1) {
            System.out.println("Login successful! User ID: " + userId);
        } else {
            System.out.println("Invalid login credentials.");
        }
        
        
    }

    public static int login(String phone, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_QUERY)) {
        	
            pstmt.setString(1, phone);
            pstmt.setString(2, password);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
//                	JobAPply.getId(resultSet.getInt("id"));
                    return resultSet.getInt("id"); // User ID found, login successful
                } else {
                    return -1; // User not found or invalid credentials
                }
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
            
            return -1;
        }
    }
}
