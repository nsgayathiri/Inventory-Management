

import java.sql.*;

public class AdminDAO {

    public void showBillsByDate(Date selectedDate) throws SQLException {
        String query = "SELECT DISTINCT Bill_no, Cust_id FROM Purchase_details WHERE Purchase_date = ? ORDER BY Cust_id";

        try (Connection con = Create_Connection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDate(1, selectedDate);
            ResultSet rs = ps.executeQuery();

            boolean hasBills = false;

            while (rs.next()) {
                hasBills = true;
                int billNo = rs.getInt("Bill_no");
                String custId = rs.getString("Cust_id");

                showSingleBill(con, custId, billNo, selectedDate);
            }

            if (!hasBills) {
                System.out.println("No bills found on " + selectedDate);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showAllBillsOfCustomer(String custId) throws SQLException {
        String query = "SELECT Bill_no, Purchase_date FROM Purchase_details WHERE Cust_id = ? ORDER BY Purchase_date";

        try (Connection con = Create_Connection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            System.out.println("Running query for Customer ID: " + custId);
            ps.setString(1, custId.trim()); // trimming to avoid space mismatch
            ResultSet rs = ps.executeQuery();

            boolean hasBills = false;

            while (rs.next()) {
                hasBills = true;
                int billNo = rs.getInt("Bill_no");
                Date date = rs.getDate("Purchase_date");

                showSingleBill(con, custId, billNo, date);
            }

            if (!hasBills) {
                System.out.println("No bills found for Customer ID: " + custId);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving bills: " + e.getMessage());
        }
    }

    private void showSingleBill(Connection con, String custId, int billNo, Date date) throws SQLException {
        String query = "SELECT pd.Product_id, p.Product_name, p.Product_price, pd.Quantity, pd.Amount " +
                "FROM Purchase_details pd JOIN Product_details p ON pd.Product_id = p.Product_id " +
                "WHERE pd.Bill_no = ? AND pd.Cust_id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, billNo);
            ps.setString(2, custId);
            ResultSet rs = ps.executeQuery();

            double total = 0;

            System.out.printf("==== BILL NO:%-2d === Date: %-10s === Customer ID:%-2s ====%n",
                    billNo, date.toString(), custId);

            System.out.printf("| %-22s | %-9s | %-5s | %-13s |%n", "Product Name", "Price", "Qty", "Total");
            System.out.println("|------------------------|-----------|-------|---------------|");

            while (rs.next()) {
                String pname = rs.getString("Product_name");
                double price = rs.getDouble("Product_price");
                int qty = rs.getInt("Quantity");
                double itemTotal = rs.getDouble("Amount");

                total += itemTotal;

                System.out.printf("| %-22s | %9.2f | %5d | %13.2f   |%n", pname, price, qty, itemTotal);
            }

            System.out.println("==============================================================");
            System.out.printf("| %-43s %13.2f |%n", "Total Bill Amount:", total);
            System.out.println("==============================================================");
            System.out.println("\n");

        }
    }

    public void MonthlyProfitLoss(int month, int year) {
        String query = "SELECT pd.Bill_no, pd.Cust_id, pd.Product_id, pd.Quantity, " +
                "pd.Amount, pd.Purchase_date, pr.Product_name, pr.Cost_price " +
                "FROM Purchase_details pd " +
                "JOIN Product_details pr ON pd.Product_id = pr.Product_id " +
                "WHERE MONTH(pd.Purchase_date) = ? AND YEAR(pd.Purchase_date) = ?";

        try (Connection con = Create_Connection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();

            double totalSelling = 0;
            double totalCost = 0;

            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s%n",
                    "Bill No", "Cust ID", "Prod ID", "Qty", "SP", "CP", "Date");
            System.out.println("--------------------------------------------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                int billNo = rs.getInt("Bill_no");
                String custId = rs.getString("Cust_id");
                int productId = rs.getInt("Product_id");
                int qty = rs.getInt("Quantity");
                double amount = rs.getDouble("Amount");
                Date date = rs.getDate("Purchase_date");
                double costPrice = rs.getDouble("Cost_price");

                double sellingPrice = amount; // SP = qty * selling price
                double totalCostPrice = costPrice * qty;

                totalSelling += sellingPrice;
                totalCost += totalCostPrice;

                System.out.printf("%-10d %-10s %-10d %-10d %-10.2f %-10.2f %-10s%n",
                        billNo, custId, productId, qty, sellingPrice, totalCostPrice, date.toString());
            }

            if (!found) {
                System.out.println("No purchases found for this month.");
            } else {
                System.out.println("--------------------------------------------------------------------------------");
                System.out.printf("%-30s %-10.2f%n", "Total Selling Amount:", totalSelling);
                System.out.printf("%-30s %-10.2f%n", "Total Cost Amount:", totalCost);
                System.out.printf("%-30s %-10.2f%n",
                        (totalSelling > totalCost ? "Profit:" : "Loss:"),
                        Math.abs(totalSelling - totalCost));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Yearly Profit/Loss
    public void yearlyProfitLoss(int year) {
        String query = "SELECT pd.Bill_no, pd.Cust_id, pd.Product_id, pd.Quantity, " +
                "pd.Amount, pd.Purchase_date, pr.Product_name, pr.Cost_price " +
                "FROM Purchase_details pd " +
                "JOIN Product_details pr ON pd.Product_id = pr.Product_id " +
                "WHERE YEAR(pd.Purchase_date) = ?";

        try (Connection con = Create_Connection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();

            double totalSelling = 0;
            double totalCost = 0;

            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-15s%n",
                    "Bill No", "Cust ID", "Prod ID", "Qty", "SP", "CP", "Date");
            System.out.println("----------------------------------------------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                int billNo = rs.getInt("Bill_no");
                String custId = rs.getString("Cust_id");
                int productId = rs.getInt("Product_id");
                int qty = rs.getInt("Quantity");
                double amount = rs.getDouble("Amount");
                Date date = rs.getDate("Purchase_date");
                double costPrice = rs.getDouble("Cost_price");

                double sellingPrice = amount;
                double totalCostPrice = costPrice * qty;

                totalSelling += sellingPrice;
                totalCost += totalCostPrice;

                System.out.printf("%-10d %-10s %-10d %-10d %-10.2f %-10.2f %-15s%n",
                        billNo, custId, productId, qty, sellingPrice, totalCostPrice, date.toString());
            }

            if (!found) {
                System.out.println("No purchases found for this year.");
            } else {
                System.out
                        .println("----------------------------------------------------------------------------------");
                System.out.printf("%-30s %-10.2f%n", "Total Selling Amount:", totalSelling);
                System.out.printf("%-30s %-10.2f%n", "Total Cost Amount:", totalCost);
                System.out.printf("%-30s %-10.2f%n",
                        (totalSelling > totalCost ? "Profit:" : "Loss:"),
                        Math.abs(totalSelling - totalCost));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
