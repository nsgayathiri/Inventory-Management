import java.util.Scanner;

public class AddProducts {
    Scanner sc = new Scanner(System.in);

    // Static counter to auto-increment product IDs
    private static int idCounter = 1;

    // Instance variables
    private int product_id;
    private String product_name;
    private double product_price;
    private int product_quantity;
    private int Cost_Price;

    public AddProducts() {
        this.product_id = idCounter++; // Assign and increment
        System.out.println("Enter the Product name: ");
        product_name = sc.nextLine();

        System.out.println("Enter the Product price:");
        product_price = sc.nextDouble();

        System.out.println("Enter the Product quantity: ");
        product_quantity = sc.nextInt();

        System.out.println("Enter the Cost price: ");
        Cost_Price = sc.nextInt();
    }

    // Getters and Setters
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }
    public int getCost_Price() {
        return Cost_Price;
    }
    public void setCost_Price(int cost_Price) {
        Cost_Price = cost_Price;
    }
}
