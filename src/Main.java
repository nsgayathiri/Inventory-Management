//Compiler Command --->  javac -cp ".;lib/mysql-connector-j-9.2.0.jar" -d bin src/*.java
// run Commands --->  java -cp ".;lib/mysql-connector-j-9.2.0.jar;bin" Main
import java.util.Scanner;
public class Main{
    public static void main(String args[]) throws Exception{
        Scanner sc = new Scanner (System.in);
        boolean flag = true;
        while(flag){
            System.out.println("================================================");
            System.out.println("Welcome to the Inventory Management System!");
            System.out.println("================================================");
            System.out.println("Please select your role:");
            System.out.println("1.Admin\n2.User \n3.Exit");
            System.out.println("================================================");
            int choice = sc.nextInt();
    
            switch(choice){
                case 1 -> {
                    AdminClass admin = new AdminClass();
                    admin.Admin();
                }
                case 2 -> {
                    System.out.println("1.New User\n2.Existing User");
                    int ch = sc.nextInt();
                    if(ch == 1){
                        User user = new User();
                        user.User();
                    }
                    else if(ch == 2){
                        System.out.println("Enter your User ID: ");
                        String userId = sc.next();
                        User_DAO userDAO = new User_DAO();
                        boolean checkinguser = userDAO.checkUser(userId);
                        if(checkinguser){
                            Purchase purchase = new Purchase();
                            purchase.Purchase_Things(userId);
                        }
        
                    }
                }
                case 3 -> {
                    System.out.println("Exiting....");
                    flag = false;
                }
                 }

            }
           
        }
       
        

    }
    


