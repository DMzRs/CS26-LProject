package Main;

import Database.DatabaseLink;

import java.sql.*;

public class Product {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    // to show product id using product name
    public int showProductId(String productName){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showId = "SELECT * FROM product WHERE productName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showId);
            preparedStatement.setString(1, productName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("productId");
            }
            preparedStatement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //to show product name using product id
    public String showProductName(int productId)    {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String showName = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showName);
            preparedStatement.setInt(1, productId);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                String Name = result.getString("productName");
                preparedStatement.close();
                result.close();
                connection.close();
                return Name;
            } else {
                preparedStatement.close();
                result.close();
                connection.close();
                return "No product found with productId: " + productId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No description";
    }

    //to get the product quantity using product id
    public int getProductQuantity(int productId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showId = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showId);
            preparedStatement.setInt(1, productId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("productQuantity");
            }
            preparedStatement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //to show product description using product id
    public String showProductDescription(int productId)    {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String showDescription = "SELECT productDescription FROM product WHERE productId = ?";
            PreparedStatement Description = connection.prepareStatement(showDescription);
            Description.setInt(1, productId);
            ResultSet result = Description.executeQuery();

            if (result.next()) {
                String description = result.getString("productDescription");
                Description.close();
                result.close();
                connection.close();
                return description;
            } else {
                Description.close();
                result.close();
                connection.close();
                return "No product found with productId: " + productId;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No description";
    }

    //to show price of a product using product id
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
                preparedStatement.close();
                result.close();
                connection.close();
                return price;
            }   else   {
                preparedStatement.close();
                result.close();
                connection.close();
                System.out.println("No product found with productId: " + productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
