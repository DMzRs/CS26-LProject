package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdate {

    public String sqlurl = "jdbc:mysql://127.0.0.1:3306/icedcoffeesystem";
    public String sqluser = "root";
    public String sqlpassword = "alamkoangpass";

    public boolean addMoney(int userId, int amountToAdd) {
        try {
            // Establish connection
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            // Correct SQL query to update balance
            String updateBalanceQuery = "UPDATE users SET accountBalance = accountBalance + ? WHERE userId = ?";

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery);
            preparedStatement.setInt(1, amountToAdd); // Set the amount to add
            preparedStatement.setInt(2, userId);       // Set the user ID

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
}
