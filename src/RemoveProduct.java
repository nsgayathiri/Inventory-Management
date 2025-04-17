import java.sql.SQLException;
import java.util.Scanner;
public class RemoveProduct {
    Scanner sc = new Scanner(System.in);
    public void RemoveProduct() throws SQLException {
        System.out.println("Enter the Product id to be removed: ");
        int Product_id = sc.nextInt();
        RemoveProduct_DAO removeProduct_DAO = new RemoveProduct_DAO();
        removeProduct_DAO.deleteProduct(Product_id);
    }
}
