import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JobAPply {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jobify";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root123";

    private static final String SELECT_CATEGORIES_QUERY = "SELECT id, name FROM categories";
    private static final String SELECT_JOBS_BY_CATEGORY_FILTER_QUERY =
            "SELECT job_id, job_name, salary, time_period FROM jobs WHERE job_id = ? AND salary BETWEEN ? AND ? AND time_period >= ?";
    private static final String INSERT_JOB_APPLICATION_QUERY = "INSERT INTO job_applications (user_id, job_id, resume_path) VALUES (?, ?, ?)";
    
    
    public static void apply() {
        applyForJobs();
    }

    public static void applyForJobs() {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            displayCategories(conn);

            Map<Integer, String> jobApplicationsMap = new HashMap<>();

            while (true) {
                System.out.print("Enter the category ID to view its jobs (0 to finish): ");
                int categoryId = scanner.nextInt();

                if (categoryId == 0) {
                    break;
                }

                System.out.print("Enter the minimum salary (INR) to filter: ");
                double minSalary = scanner.nextDouble();

                System.out.print("Enter the maximum salary (INR) to filter: ");
                double maxSalary = scanner.nextDouble();

                System.out.print("Enter the minimum time_period to filter: ");
                int minTimePeriod = scanner.nextInt();

                displayJobsByCategoryWithFilter(conn, categoryId, minSalary, maxSalary, minTimePeriod);

                System.out.print("Enter the job ID to apply for: ");
                int jobId = scanner.nextInt();

                System.out.print("Enter the path to your resume: ");
                String resumePath = scanner.next();

                jobApplicationsMap.put(jobId, resumePath);
            }

            if (!jobApplicationsMap.isEmpty()) {
                applyForJobs(conn, jobApplicationsMap);
            } else {
                System.out.println("No job applications to submit.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCategories(Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_CATEGORIES_QUERY);
             ResultSet resultSet = pstmt.executeQuery()) {

            System.out.println("Available Categories:");
            System.out.println("--------------------");
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                String categoryName = resultSet.getString("name");
                System.out.println(categoryId + ". " + categoryName);
            }
            System.out.println("--------------------");
        }
    }

    private static void displayJobsByCategoryWithFilter(Connection conn, int categoryId, double minSalary, double maxSalary, int minTimePeriod) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_JOBS_BY_CATEGORY_FILTER_QUERY)) {
            pstmt.setInt(1, categoryId);
            pstmt.setDouble(2, minSalary);
            pstmt.setDouble(3, maxSalary);
            pstmt.setInt(4, minTimePeriod);
            try (ResultSet resultSet = pstmt.executeQuery()) {

                System.out.println("Jobs in the selected category with filters:");
                System.out.println("--------------------");
                while (resultSet.next()) {
                    int jobId = resultSet.getInt("job_id");
                    String jobName = resultSet.getString("job_name");
                    double salary = resultSet.getDouble("salary");
                    int timePeriod = resultSet.getInt("time_period");
                    System.out.println("Job ID: " + jobId + ", Job: " + jobName + ", Salary: " + salary + ", Time Period: " + timePeriod);
                }
                System.out.println("--------------------");
            }
        }
    }

    private static void applyForJobs(Connection conn, Map<Integer, String> jobApplicationsMap) throws SQLException {
        try {
            conn.setAutoCommit(false);

            for (Map.Entry<Integer, String> entry : jobApplicationsMap.entrySet()) {
                int jobId = entry.getKey();
                String resumePath = entry.getValue();

                // Check if the job exists
                if (!isJobExists(conn, jobId)) {
                    System.out.println("Job ID " + jobId + " does not exist.");
                    continue;
                }

                // Insert the job application details
                try (PreparedStatement insertPstmt = conn.prepareStatement(INSERT_JOB_APPLICATION_QUERY)) {
                    // Replace 'user_id' with the actual user ID who is applying for the job.
                    int userId = 5; // Replace 1 with the actual user ID
                    insertPstmt.setInt(1, userId);
                    insertPstmt.setInt(2, jobId);
                    insertPstmt.setString(3, resumePath);
                    insertPstmt.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("Job applications submitted successfully!");

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private static boolean isJobExists(Connection conn, int jobId) throws SQLException {
        String selectJobQuery = "SELECT COUNT(*) AS count FROM jobs WHERE job_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectJobQuery)) {
            pstmt.setInt(1, jobId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                } else {
                    return false;
                }
            }
        }
    }
    
    
}