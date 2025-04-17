import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;



public class AdminClass {
    Scanner sc;

    public AdminClass() {
        this.sc = new Scanner(System.in);
    }

    public void Admin() throws SQLException {
        boolean flag = true;
        AdminDAO adminDAO = new AdminDAO(); 
        while (flag) {
            System.out.println("===========================================");
            System.out.println("|              MENU OPTIONS               |");
            System.out.println("===========================================");
            System.out.println("| 1. Add Item                             |");
            System.out.println("| 2. Remove Item                          |");
            System.out.println("| 3. View Inventory                       |");
            System.out.println("| 4. Customer Purchased Details           |");
            System.out.println("| 5. View Out of Stock Product            |");
            System.out.println("| 6. View Bills by Specific Date          |");
            System.out.println("| 7. View All Bills of a Customer         |");
            System.out.println("| 8. Monthly Profit/Loss                  |");
            System.out.println("| 9. Yearly Profit/Loss                   |");
            System.out.println("| 10. Exit                                |");
            System.out.println("===========================================");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> {
                    System.out.println();
                    System.out.println("Adding item...");
                    System.out.println("==========================================");
                    System.out.println("|1. New Product                          |");
                    System.out.println("|2. Restock Existing Product             |");
                    System.out.println("==========================================");
                    int option = sc.nextInt();
                    sc.nextLine(); // clear buffer

                    if (option == 1) {
                        AddProducts add = new AddProducts();
                        AddProducts_DAO add_dao = new AddProducts_DAO();
                        add_dao.insert_new_Product(add);
                    } else {
                        ViewInventory_DAO viewDao = new ViewInventory_DAO();
                        viewDao.viewProducts();
                        System.out.print("Enter the Product id to Restock: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // clear buffer
                        AddProducts_DAO add_dao = new AddProducts_DAO();
                        add_dao.restockProduct(id);
                    }
                }
                case 2 -> {
                    System.out.println();
                    System.out.println("Removing item...");
                    RemoveProduct remove = new RemoveProduct();
                    remove.RemoveProduct();
                }
                case 3 -> {
                    System.out.println();
                    System.out.println("Viewing inventory...");
                    ViewInventory_DAO viewDao = new ViewInventory_DAO();
                    viewDao.viewProducts();
                }
                case 4 -> {
                    System.out.println();
                    System.out.println("Viewing customer purchase details...");
                    Purchase_DAO purDao = new Purchase_DAO();
                    purDao.viewPurchaseDetails();
                }
                case 5 -> {
                    System.out.println();
                    System.out.println("View Out of Stock Products...");
                    ViewInventory_DAO viewDao = new ViewInventory_DAO();
                    viewDao.viewOutOfStockProducts();
                }
                case 6 -> {
                    System.out.print("Enter Date (yyyy-mm-dd): ");
                    String dateString = sc.nextLine().trim();

                    try {
                        Date date = Date.valueOf(dateString);
                        adminDAO.showBillsByDate(date);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format! Please use yyyy-MM-dd.");
                    }
                }
                case 7 -> {
                    System.out.print("Enter Customer ID: ");
                    String custId = sc.nextLine().trim();
                    adminDAO.showAllBillsOfCustomer(custId);
                }
                case 8 -> {
                    System.out.print("Enter the Month (1-12): ");
                    int month = sc.nextInt();
                    System.out.print("Enter the Year: ");
                    int year = sc.nextInt();
                    sc.nextLine(); // clear buffer
                    adminDAO.MonthlyProfitLoss(month, year);
                }
                case 9 -> {
                    System.out.print("Enter the Year: ");
                    int year = sc.nextInt();
                    sc.nextLine(); // clear buffer
                    adminDAO.yearlyProfitLoss(year);
                }
                case 10 -> {
                    System.out.println("Exiting...");
                    flag = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
