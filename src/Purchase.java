import java.sql.SQLException;
import java.util.Scanner;

public class Purchase {
    Scanner sc = new Scanner(System.in);
    billStatement bill = new  billStatement();
    Purchase_DAO pur_dao = new Purchase_DAO();
    
    public void Purchase_Things(String Customer_id) throws SQLException {
        int choice = 1;
        int bill_no = bill.Add_bill(Customer_id);
        while (choice == 1) {
            System.out.println("---------------------------------------");
            System.out.println("Welcome to the Purchase Section!");
            System.out.println("Available Products:");
    
            ViewInventory_DAO viewInventory_DAO = new ViewInventory_DAO();
            viewInventory_DAO.viewProducts();
    
            System.out.print("Enter the Product ID to purchase: ");
            int id = sc.nextInt();
    
            System.out.print("Enter the quantity to purchase: ");
            int quantity = sc.nextInt();
    
            if (pur_dao.isAvailable(id, quantity)) 
            {
                boolean flag = pur_dao.reduceQuantity(id, quantity);
                if (flag) 
                {
                    pur_dao.insert_in_billing_Table(bill_no, Customer_id, id, quantity);
                } 
                else
                {
                    System.out.println("Error in reducing product quantity.");
                }
            } 
            else
            {
                System.out.println("----------------------------------------------------");
                System.out.println("Requested quantity not available.");
                System.out.println("Please check the available quantity and try again.");
                System.out.println("----------------------------------------------------");
            }
    
            System.out.println("\n1.Continue Purchase\n2.Exit and Print Bill");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

        }        
        
        bill.printBill(Customer_id, bill_no);
    }
    
}
