import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {
	static final Scanner sc = new Scanner(System.in);

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jobify";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root123";

    private static final String INSERT_QUERY = "INSERT INTO users (name, phone, password, filePath, description, courses) VALUES (?, ?, ?, ?, ?, ?)";

    public static void Signup() {
        // Sample customer data
    	System.out.println("Enter the name :");
    	String name = sc.next();
    	System.out.println("Enter the phone number :");
    	String phone = sc.next();
    	System.out.println("Enter the Password :");
    	String Password = sc.next();
    	System.out.println("Enter the Resume path :");
    	String filePath = sc.next();
    	
    	System.out.println("Enter the Description : ");
    	String desc = sc.next();
    	System.out.println("Enter the Courses : ");
    	String courses = sc.next();
    	
        Customer customer = new Customer(name, phone, Password, filePath, desc, courses);

        // Call the signUpCustomer method to insert the customer data into the database
        signUpCustomer(customer);
    }

    public static void signUpCustomer(Customer customer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getPassword());
            pstmt.setString(4, customer.getFilePath());
            pstmt.setString(5, customer.getDesc());
            pstmt.setString(6, customer.getCourses());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer signed up successfully!");

                // Retrieve the generated auto-incremented ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int customerId = generatedKeys.getInt(1);
                        System.out.println("Customer ID: " + customerId);
                    }
                }

            } else {
                System.out.println("Failed to sign up customer.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions appropriately
        }
    }
}
