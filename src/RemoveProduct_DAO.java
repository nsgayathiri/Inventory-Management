import java.sql.*;
public class RemoveProduct_DAO {
    public void deleteProduct(int id) throws SQLException{
        String query = "update Available_Products set Quantity = 0, Product_Price = 0 WHERE Product_id = ?";
        try (Connection con = Create_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0)
            {
                System.out.println("Product deleted successfully!");
            } 
            else 
            {
                System.out.println("Failed to delete product. Product ID may not exist.");
            }
        } 
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
