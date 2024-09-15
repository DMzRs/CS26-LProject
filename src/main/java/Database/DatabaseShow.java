package Database;

import java.sql.*;

public class DatabaseShow {

    public int showMoney(int userId)    {
        try {
            int balance;
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/icedcoffeesystem",
                    "root",
                    "alamkoangpass"
            );
            String showBalance = "SELECT * FROM users WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showBalance);
            preparedStatement.setInt(1, userId);

            // Execute query
            ResultSet result = preparedStatement.executeQuery();

            // Process result
            if (result.next()) {
                balance = result.getInt("accountBalance");
                return balance;
            } else {
                System.out.println("No user found with userId: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
