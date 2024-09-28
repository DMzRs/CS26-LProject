package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdate {

    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    //to update specific user sale per transaction made
    public void updateUserSales(int userId, int totalPrice){
        try{
            Connection con = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            PreparedStatement ps = con.prepareStatement("update user set userSales = (userSales + ?) where userid = ?");
            ps.setInt(1, totalPrice);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to remove quantity in admin control
    public void deductProductQuantity(int productId, int quantityRemoved) {
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            // Correct SQL query to update balance
            String updateBalanceQuery = "UPDATE product SET productQuantity = ? WHERE productId = ?";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery);
            preparedStatement.setInt(1, quantityRemoved); // Set the amount to add
            preparedStatement.setInt(2, productId);       // Set the user ID

            // Execute the update
            int affectedRows = preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to add product quantity in admin control
    public void addProductQuantity(int productId, int quantityAdded) {
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            // Correct SQL query to update balance
            String updateBalanceQuery = "UPDATE product SET productQuantity = ? WHERE productId = ?";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery);
            preparedStatement.setInt(1, quantityAdded); // Set the amount to add
            preparedStatement.setInt(2, productId);       // Set the user ID

            // Execute the update
            int affectedRows = preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //when a product is bought
    public void productBought(int productId, int quantityBought) {
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            // Correct SQL query to update balance
            String updateBalanceQuery = "UPDATE product SET productQuantity = (productQuantity - ?) WHERE productId = ?";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery);
            preparedStatement.setInt(1, quantityBought); // Set the amount to add
            preparedStatement.setInt(2, productId);       // Set the user ID

            // Execute the update
            int affectedRows = preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
