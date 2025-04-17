import java.util.Scanner;

public class User 
{
    Scanner sc = new Scanner(System.in);
    private static int id = 1;
    String User_id;
    String name;
    int Age;
    String Gender;
    long phone;

    public User() throws Exception{        
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("------Enter the Required Details------");
        
        String U_id = "C" + id++;
        this.User_id = U_id;
        System.out.println("Enter Your Name:");
        this.name = sc.nextLine();
        
        System.out.println("Enter your age:");
        this.Age = sc.nextInt();
        sc.nextLine();  
        
        System.out.println("Enter your Gender");
        System.out.println("1. Female \t 2. Male");
        int ch = sc.nextInt();
        sc.nextLine();
        
        this.Gender = (ch == 1) ? "Female" : "Male";
        
        System.out.println("Enter your phone number:");
        this.phone = sc.nextLong();

        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("------Details Entered Successfully------");

        // Pass the User object to User_DAO for database insertion
        User_DAO userDAO = new User_DAO();
        String id = userDAO.insertUser(this);
        if(id != null)
        {
        System.out.println("Welcome " + name + " to the Inventory Management System!");
        System.out.println("Your User ID is: " + id);
        Purchase purchase = new Purchase();
        purchase.Purchase_Things(id);
        }
        else
        {
            System.out.println("Error in inserting user details.");
        }
    }

    public void User() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'User'");
    }
}
