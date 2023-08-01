import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateUser extends UserManager {
    private static final String UPDATE_QUERY = "UPDATE users SET name = ?, phone = ?, password = ?, filePath = ?, description = ?, courses = ? WHERE id = ?";

    @Override
    public void performAction() {
        try (Scanner sc = new Scanner(System.in);
             Connection conn = getConnection()) {

            System.out.println("Enter the user ID to update: ");
            int userId = Integer.parseInt(sc.next());

            if (!userExists(conn, userId)) {
                System.out.println("User with ID " + userId + " does not exist.");
                return;
            }

            System.out.println("Enter the new name: ");
            String name = sc.next();
            System.out.println("Enter the new phone number: ");
            String phone = sc.next();
            System.out.println("Enter the new password: ");
            String password = sc.next();
            System.out.println("Enter the new file path: ");
            String filePath = sc.next();
            System.out.println("Enter the new description: ");
            String description = sc.next();
            System.out.println("Enter the new courses: ");
            String courses = sc.next();

            updateUser(conn, userId, name, phone, password, filePath, description, courses);
            System.out.println("User information updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUser(Connection conn, int userId, String name, String phone, String password, String filePath, String description, String courses) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, password);
            pstmt.setString(4, filePath);
            pstmt.setString(5, description);
            pstmt.setString(6, courses);
            pstmt.setInt(7, userId);

            pstmt.executeUpdate();
        }
    }

    public static void execute() {
        UpdateUser updateUser = new UpdateUser();
        updateUser.performAction();
    }

    public static void main(String[] args) {
        execute();
    }
}
