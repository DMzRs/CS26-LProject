package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdate {

    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();


    public boolean deductProductQuantity(int productId, int quantityBought) {
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

            // Return true if rows were affected
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addProductQuantity(int productId, int quantityAdded) {
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            // Correct SQL query to update balance
            String updateBalanceQuery = "UPDATE product SET productQuantity = (productQuantity + ?) WHERE productId = ?";

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
}
