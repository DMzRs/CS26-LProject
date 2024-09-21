package Database;

import java.sql.*;

public class DatabaseShow {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    public int showMoney(int userId)    {
        try {
            int balance;
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

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

    public String showProductDescription(int productId)    {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String showDescription = "SELECT productDescription FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showDescription);
            preparedStatement.setInt(1, productId);

            // Execute query
            ResultSet result = preparedStatement.executeQuery();

            // Process result
            if (result.next()) {
                String description = result.getString("productDescription");
                return description;
            } else {
                return "No product found with productId: " + productId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No description";
    }

    public String showProductName(int productId)    {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String showName = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showName);
            preparedStatement.setInt(1, productId);

            // Execute query
            ResultSet result = preparedStatement.executeQuery();

            // Process result
            if (result.next()) {
                String Name = result.getString("productName");
                return Name;
            } else {
                return "No product found with productId: " + productId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No description";
    }

    public int showProductPrice(int productId)    {
        try {
            int price;
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String showPrice = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showPrice);
            preparedStatement.setInt(1, productId);

            // Execute query
            ResultSet result = preparedStatement.executeQuery();

            // Process result
            if (result.next()) {
                price = result.getInt("price");
                return price;
            }   else   {
                System.out.println("No product found with productId: " + productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int showProductId(String productName){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showId = "SELECT * FROM product WHERE productName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showId);
            preparedStatement.setString(1, productName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int productId = result.getInt("productId");
                return productId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

