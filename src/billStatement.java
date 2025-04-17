import java.sql.*;

public class billStatement {
    public void printBill(String customerId, int billNo) throws SQLException {
        String query = "SELECT pd.Product_Name, pd.Product_Price, p.Quantity " +
                       "FROM Purchase_details p " +
                       "JOIN Product_details pd ON p.Product_id = pd.Product_id " +
                       "WHERE p.Cust_id = ? AND p.Bill_no = ?"; 

        try (Connection con = Create_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, customerId);
            ps.setInt(2, billNo);
            ResultSet rs = ps.executeQuery();

            double total = 0;
            System.out.println("\n======================= BILL ========================");
            System.out.println("Customer ID: " + customerId);
            System.out.println("Bill No: " + billNo);
            System.out.printf("%-20s %-10s %-10s %-10s%n", "Product", "Price", "Qty", "Total");

            while (rs.next()) 
            {
                String name = rs.getString("Product_Name");
                double price = rs.getDouble("Product_Price");
                int qty = rs.getInt("Quantity");
                double subtotal = price * qty;
                total += subtotal;

                System.out.printf("%-20s %-10.2f %-10d %-10.2f%n", name, price, qty, subtotal);
            }

            System.out.println("---------------------------------------------------------");
            System.out.printf("%-42s %-10.2f%n", "Total Amount: ", total);
            System.out.println("========================================================\n");
            
        }
    }
    public int Add_bill(String Cust_id) {
        String query = "INSERT INTO bill(Cust_id, Date_of_Purchase) VALUES (?, CURDATE())";
        int bill_no = -1;

        try (Connection con = Create_Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, Cust_id);
            int rowsInserted = ps.executeUpdate();
            
            if (rowsInserted > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    bill_no = rs.getInt(1); 
                }
            } else {
                System.out.println("Error in adding bill.");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting into bill table: " + e.getMessage());
        }

        return bill_no;
    }
}
