//import java.sql.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class ApplyJobs {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
//    private static final String DB_USER = "your_mysql_username";
//    private static final String DB_PASSWORD = "your_mysql_password";
//
//    private static Map<String, Integer> userMap = new HashMap<>(); // Simulate user authentication
//
//    public static void main(String[] args) {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//
//            // Simulate user registration and login
//            int userId = registerOrLoginUser(connection);
//
//            if (userId != -1) {
//                displayCategories(connection);
//
//                int selectedCategoryId = selectCategory(connection);
//
//                if (selectedCategoryId != -1) {
//                    listJobs(connection, selectedCategoryId);
//                    int selectedJobId = selectJob(connection);
//
//                    if (selectedJobId != -1) {
//                        applyForJob(connection, userId, selectedJobId);
//                    }
//                }
//            }
//
//            connection.close();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static int registerOrLoginUser(Connection connection) throws SQLException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter your email: ");
//        String email = scanner.nextLine();
//
//        // Check if the user already exists in the database
//        int existingUserId = getUserIdByEmail(connection, email);
//        if (existingUserId != -1) {
//            System.out.println("Welcome back!");
//            return existingUserId;
//        } else {
//            // User not found, proceed with registration
//            System.out.print("Enter your name: ");
//            String name = scanner.nextLine();
//
//            System.out.print("Enter your phone number: ");
//            String phone = scanner.nextLine();
//
//            System.out.print("Enter your password: ");
//            String password = scanner.nextLine();
//
//            // Store the user details in the database
//            int newUserId = insertUser(connection, name, phone, email, password);
//            if (newUserId != -1) {
//                System.out.println("Registration successful! Your user ID is: " + newUserId);
//                userMap.put(email, newUserId); // Update the simulated user authentication map
//                return newUserId;
//            } else {
//                System.out.println("Error: Registration failed.");
//                return -1;
//            }
//        }
//    }
//
//    private static int getUserIdByEmail(Connection connection, String email) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE email = ?");
//        preparedStatement.setString(1, email);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        int userId = -1;
//
//        if (resultSet.next()) {
//            userId = resultSet.getInt("id");
//        }
//
//        preparedStatement.close();
//        return userId;
//    }
//
//    private static int insertUser(Connection connection, String name, String phone, String email, String password) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, phone, email, password) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
//        preparedStatement.setString(1, name);
//        preparedStatement.setString(2, phone);
//        preparedStatement.setString(3, email);
//        preparedStatement.setString(4, password);
//
//        int affectedRows = preparedStatement.executeUpdate();
//        int newUserId = -1;
//
//        if (affectedRows > 0) {
//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                newUserId = generatedKeys.getInt(1);
//            }
//        }
//
//        preparedStatement.close();
//        return newUserId;
//    }
//
//    private static void displayCategories(Connection connection) throws SQLException {
//        Statement statement = connection.createStatement();
//        String query = "SELECT * FROM categories";
//        ResultSet resultSet = statement.executeQuery(query);
//
//        System.out.println("Available Categories:");
//        while (resultSet.next()) {
//            int categoryId = resultSet.getInt("id");
//            String categoryName = resultSet.getString("name");
//            System.out.println(categoryId + ". " + categoryName);
//        }
//
//        statement.close();
//    }
//
//
//    private static int selectCategory(Connection connection) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter the category ID: ");
//        int selectedCategoryId = scanner.nextInt();
//
//        // Check if the selected category exists
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT COUNT(*) AS count FROM categories WHERE id = " + selectedCategoryId;
//            ResultSet resultSet = statement.executeQuery(query);
//            resultSet.next();
//            int count = resultSet.getInt("count");
//
//            if (count == 0) {
//                System.out.println("Invalid category ID. Please try again.");
//                return -1;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return selectedCategoryId;
//    }
//
//    private static void listJobs(Connection connection, int categoryId) throws SQLException {
//        Statement statement = connection.createStatement();
//        String query = "SELECT * FROM jobs WHERE category_id = " + categoryId;
//        ResultSet resultSet = statement.executeQuery(query);
//
//        System.out.println("Jobs under selected category:");
//        while (resultSet.next()) {
//            int jobId = resultSet.getInt("id");
//            String jobTitle = resultSet.getString("title");
//            System.out.println(jobId + ". " + jobTitle);
//        }
//
//        statement.close();
//    }
//
//    private static int selectJob(Connection connection) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter the job ID: ");
//        int selectedJobId = scanner.nextInt();
//
//        // Check if the selected job exists
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT COUNT(*) AS count FROM jobs WHERE id = " + selectedJobId;
//            ResultSet resultSet = statement.executeQuery(query);
//            resultSet.next();
//            int count = resultSet.getInt("count");
//
//            if (count == 0) {
//                System.out.println("Invalid job ID. Please try again.");
//                return -1;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return selectedJobId;
//    }
//
//    private static void insertSelectedJob(Connection connection, int jobId) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO selected_jobs (job_id) VALUES (?)");
//        preparedStatement.setInt(1, jobId);
//        preparedStatement.executeUpdate();
//        preparedStatement.close();
//    }
//}
