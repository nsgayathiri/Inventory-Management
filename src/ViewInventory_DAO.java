import java.sql.*;


public class ViewInventory_DAO {

    public void viewProducts() throws SQLException {
        String query = "SELECT * FROM Available_Products where Quantity > 0";
        
        try (Connection con = Create_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
    
            System.out.println("===============================================================");
            System.out.printf("| %-10s | %-20s | %-12s | %-8s |\n", "Product ID", "Product Name", "Price", "Quantity");
            System.out.println("===============================================================");
    
            while (rs.next()) {
                int id = rs.getInt("Product_id");
                String name = rs.getString("Product_name");
                double price = rs.getDouble("Product_price");
                int quantity = rs.getInt("Quantity");
    
                System.out.printf("| %-10d | %-20s | %-12.2f | %-8d |\n", id, name, price, quantity);
            }
    
            System.out.println("=================================================================");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    

    public void viewOutOfStockProducts() throws SQLException {
        String query = "SELECT * FROM Available_Products WHERE Quantity <= 0";
        
        try (Connection con = Create_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;
            System.out.println("=======================================================");
            System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Qty");
            System.out.println("=======================================================");

            while (rs.next()) {
                found = true;
                System.out.printf("%-10d %-20s %-10.2f %-10d%n",
                        rs.getInt("Product_id"),
                        rs.getString("Product_name"),
                        rs.getDouble("Product_Price"),
                        rs.getInt("Quantity"));
            }
            System.out.println("========================================================");

            if (!found) {
                System.out.println("----ALL PRODUCTS ARE IN STOCK----");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving out-of-stock products: " + e.getMessage());
        }
    }
}
