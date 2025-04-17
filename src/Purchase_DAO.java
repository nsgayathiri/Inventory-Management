import java.sql.*;

public class Purchase_DAO {

    public boolean isAvailable(int id, int quantity) throws SQLException {
        String query = "select Quantity from Available_Products where Product_id = ?";
        try (Connection con = Create_Connection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int availableQuantity = rs.getInt("Quantity");
                if (availableQuantity >= quantity) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean reduceQuantity(int id, int quantity) throws SQLException {
        String updateQuery = "UPDATE Available_Products SET Quantity = Quantity - ? WHERE Product_id = ?";
        String checkQuery = "SELECT Quantity FROM Available_Products WHERE Product_id = ?";
        String SetZeroQuery = "UPDATE Available_Products SET Quantity = 0 WHERE Product_id = ?";

        try (Connection con = Create_Connection.getConnection()) {
            // Step 1: Reduce the quantity
            try (PreparedStatement psUpdate = con.prepareStatement(updateQuery)){
                psUpdate.setInt(1, quantity);
                psUpdate.setInt(2, id);
                int rowsUpdated = psUpdate.executeUpdate();

                if (rowsUpdated > 0) {
                    // Step 2: Check the new quantity
                    try (PreparedStatement psCheck = con.prepareStatement(checkQuery)) {
                        psCheck.setInt(1, id);
                        ResultSet rs = psCheck.executeQuery();
                        if (rs.next()) {
                            int remainingQuantity = rs.getInt("Quantity");

                            // Step 3: If quantity is 0, delete the product
                            if (remainingQuantity <= 0) {
                                try (PreparedStatement psDelete = con.prepareStatement(SetZeroQuery)) {
                                    psDelete.setInt(1, id);
                                    psDelete.executeUpdate();
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void insert_in_billing_Table(int Bill_no, String Cust_id, int id, int quantity) {
        String checkQuery = "SELECT Quantity, Amount FROM Purchase_details WHERE Bill_no = ? AND Cust_id = ? AND Product_id = ?";
        String insertQuery = "INSERT INTO Purchase_details (Bill_no, Cust_id, Product_id, Product_price, Quantity, Amount, Purchase_date, Purchase_time) VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";
        String priceQuery = "SELECT Product_Price FROM Available_Products WHERE Product_id = ?";
        String updateQuery = "UPDATE Purchase_details SET Quantity = Quantity + ?, Amount = Amount + ? WHERE Bill_no = ? AND Cust_id = ? AND Product_id = ?";
    
        try (Connection con = Create_Connection.getConnection();
             PreparedStatement psCheck = con.prepareStatement(checkQuery);
             PreparedStatement psPrice = con.prepareStatement(priceQuery);
             PreparedStatement psInsert = con.prepareStatement(insertQuery);
             PreparedStatement psUpdate = con.prepareStatement(updateQuery)) {
    
            // Get product price
            psPrice.setInt(1, id);
            ResultSet rsPrice = psPrice.executeQuery();
            double price;
    
            if (rsPrice.next()) {
                price = rsPrice.getDouble("Product_Price");
            } else {
                System.out.println("Product not found in Available_Products.");
                return;
            }
    
            double totalAmount = price * quantity;
    
            // Check if product already exists in purchase
            psCheck.setInt(1, Bill_no);
            psCheck.setString(2, Cust_id);
            psCheck.setInt(3, id);
            ResultSet rsCheck = psCheck.executeQuery();
    
            if (rsCheck.next()) {
                // Product already exists in purchase, update quantity and amount
                psUpdate.setInt(1, quantity);
                psUpdate.setDouble(2, totalAmount);
                psUpdate.setInt(3, Bill_no);
                psUpdate.setString(4, Cust_id);
                psUpdate.setInt(5, id);
    
                int updated = psUpdate.executeUpdate();
                if (updated > 0) {
                    System.out.println("Existing product updated in purchase.");
                } else {
                    System.out.println("Failed to update existing product.");
                }
            } else {
                // Insert new product into purchase
                psInsert.setInt(1, Bill_no);
                psInsert.setString(2, Cust_id);
                psInsert.setInt(3, id);
                psInsert.setDouble(4, price);
                psInsert.setInt(5, quantity);
                psInsert.setDouble(6, totalAmount);
    
                int inserted = psInsert.executeUpdate();
                if (inserted < 0) {
                    System.out.println("Failed to insert new product.");
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Error in insert_in_billing_Table: " + e.getMessage());
        }
    }
    

    public void viewPurchaseDetails() throws SQLException {
        String query = "SELECT Cust_id, Purchase_date, COUNT(*) AS total_orders, SUM(Amount) AS total_amount " +
                "FROM Purchase_details " +
                "GROUP BY Cust_id, Purchase_date " +
                "ORDER BY Purchase_date DESC";

        try (Connection con = Create_Connection.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");

            System.out.println("                 Customer Purchase Details");
            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");

            System.out.printf("%-15s %-15s %-15s %-15s%n", "Customer ID", "Purchase Date", "Total Orders",
                    "Total Amount");
            System.out.println("---------------------------------------------------------------");

            while (rs.next()) {
                String custId = rs.getString("Cust_id");
                Date purchaseDate = rs.getDate("Purchase_date");
                int totalOrders = rs.getInt("total_orders");
                double totalAmount = rs.getDouble("total_amount");

                System.out.printf("%-15s %-15s %-15d %-15.2f%n", custId, purchaseDate, totalOrders, totalAmount);
            }
            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving purchase details: " + e.getMessage());
        }
    }

}
