import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_DAO {
    public String insertUser(User U) {
        String query = "INSERT INTO Customer_details (Cust_id, Customer_name, Customer_age, Customer_Gender, Customer_mobile_No, Date_of_purchase, Time_of_purchase) VALUES (?, ?, ?, ?, ?, NOW(), Now())"; 
    
        try (Connection con = Create_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, U.User_id);
            ps.setString(2, U.name);
            
            ps.setInt(3, U.Age);
            ps.setString(4, U.Gender);
            ps.setLong(5, U.phone);  

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0){
                return U.User_id;
            } else {
                System.out.println("Failed to add user.");
            }

        } catch (SQLException e) {
            System.out.println(" Error inserting user: " + e.getMessage());
        }
        return null;
    }

    public boolean checkUser(String userId) {
        String query = "SELECT * FROM Customer_details WHERE Cust_id = ?";
        
        try (Connection con = Create_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) { 
                if (rs.next()) 
                {
                  System.out.printf("Customer Name: %-20s | ID: %-10s | Mobile Number: %d%n",
                  rs.getString("Customer_name"),
                  rs.getString("Cust_id"),
                  rs.getLong("Customer_mobile_No"));
                  System.out.println("\n");
                  return true;
                } 
                else 
                {
                    System.out.println("User Not Found");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error checking user: " + e.getMessage());
            return false;
        }
    }
}
