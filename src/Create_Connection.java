import java.sql.*;

public class Create_Connection {
    private static final String url = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String userName = "root";
    private static final String passWord = "root";

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Make sure the connector JAR is added.");
            e.printStackTrace();
            throw new SQLException("Unable to load JDBC Driver", e); // Rethrow as SQLException
        }

        // Establish and return connection
        return DriverManager.getConnection(url, userName, passWord);
    }
}