import java.sql.*;

import java.util.Scanner;

public class AddProducts_DAO {

    Scanner sc = new Scanner(System.in);

    public void insert_new_Product(AddProducts add) throws SQLException 
    {
        String query1 = "INSERT INTO Product_details(Product_name,Product_Price, Quantity, Cost_price) VALUES (?, ?, ?, ?)";
        String query2 = "INSERT INTO Available_Products(Product_name, Product_Price, Quantity) VALUES (?, ?, ?)";

        try (Connection con = Create_Connection.getConnection()) {
            try (
                PreparedStatement ps1 = con.prepareStatement(query1);
                PreparedStatement ps2 = con.prepareStatement(query2)
            ) {
               
                // ps1.setInt(1, add.getProduct_id());
                ps1.setString(1, add.getProduct_name());
                ps1.setDouble(2, add.getProduct_price());
                ps1.setInt(3, add.getProduct_quantity());
                ps1.setDouble(4, add.getCost_Price());
                

                // ps2.setInt(1, add.getProduct_id());
                ps2.setString(1, add.getProduct_name());
                ps2.setDouble(2, add.getProduct_price());
                ps2.setInt(3, add.getProduct_quantity());

                int rowsInserted_1 = ps1.executeUpdate();
                int rowsInserted_2 = ps2.executeUpdate();

                if (rowsInserted_1 > 0 && rowsInserted_2 > 0) {
                    System.out.println("Product added successfully!");
                } else {
                    System.out.println("Failed to add product.");
                }
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Product ID already exists. Please use a different ID.");
            } else {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void restockProduct(int productId) throws SQLException {
    try (Connection con = Create_Connection.getConnection()) {

        String checkProduct = "SELECT * FROM Product_details WHERE Product_id = ?";
        PreparedStatement psCheck = con.prepareStatement(checkProduct);
        psCheck.setInt(1, productId);
        ResultSet rs = psCheck.executeQuery();

        if (!rs.next()) {
            System.out.println(" Product ID not found in Product_details. Cannot restock.");
            return;
        }

        String currentName = rs.getString("Product_name");
        double currentPrice = rs.getDouble("Product_Price");

        System.out.println("Product Found:");
        System.out.println("Name: " + currentName + "\t | Price: " + currentPrice);

        // Ask if admin wants to change the name
        System.out.print("Do you want to change the product name? (yes/no): \n");
        String nameChoice = sc.nextLine().trim().toLowerCase();
        String newName = currentName;
        if (nameChoice.equals("yes")) {
            System.out.print("Enter new product name: ");
            newName = sc.nextLine();
        }

        // Ask if admin wants to change the price
        System.out.print("Do you want to change the product price? (yes/no):\n ");
        
        String priceChoice = sc.nextLine().trim().toLowerCase();
        double newPrice = currentPrice;
        if (priceChoice.equals("yes")) {
            System.out.print("Enter new product price: ");
            newPrice = sc.nextDouble();
            sc.nextLine(); // clear newline
        }

        System.out.print("Enter quantity to restock: ");
        int restockQty = sc.nextInt();

        // Update Product_details (always to keep in sync)
        String updateDetails = "UPDATE Available_Products SET Product_name = ?, Product_Price = ? , Quantity = ? WHERE Product_id = ?";
        PreparedStatement psUpdate = con.prepareStatement(updateDetails);
        psUpdate.setString(1, newName);
        psUpdate.setDouble(2, newPrice);
        psUpdate.setInt(3, restockQty);
        psUpdate.setInt(4, productId);
        psUpdate.executeUpdate();


    } catch (SQLException e) {
        System.out.println("Error during restocking: " + e.getMessage());
    }
}

}
