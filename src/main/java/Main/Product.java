package Main;

import Database.DatabaseLink;

import java.sql.*;
abstract class ProductIdentificationFunctions{
    public abstract int showProductId(String productName);
    public abstract String showProductName(int productId);
    public abstract String showProductDescription(int productId);
    public abstract int showProductPrice(int productId);
    public abstract int getProductQuantity(int productId);
}

public class Product extends ProductIdentificationFunctions{
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    private int productId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private int productQuantity;

    // to show product id using product name
    public int showProductId(String productName) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl, sqluser, sqlpassword);
            String showId = "SELECT * FROM product WHERE productName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showId);
            preparedStatement.setString(1, productName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                productId = result.getInt("productId");
                return productId;
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
    public String showProductName(int productId) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl, sqluser, sqlpassword);

            String showName = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showName);
            preparedStatement.setInt(1, productId);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                productName = result.getString("productName");
                preparedStatement.close();
                result.close();
                connection.close();
                return productName;
            } else {
                preparedStatement.close();
                result.close();
                connection.close();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //to get the product quantity using product id
    public int getProductQuantity(int productId) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl, sqluser, sqlpassword);
            String showId = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showId);
            preparedStatement.setInt(1, productId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                productQuantity = result.getInt("productQuantity");
                return productQuantity;
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
    public String showProductDescription(int productId) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl, sqluser, sqlpassword);

            String showDescription = "SELECT productDescription FROM product WHERE productId = ?";
            PreparedStatement Description = connection.prepareStatement(showDescription);
            Description.setInt(1, productId);
            ResultSet result = Description.executeQuery();

            if (result.next()) {
                productDescription = result.getString("productDescription");
                Description.close();
                result.close();
                connection.close();
                return productDescription;
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
    public int showProductPrice(int productId) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl, sqluser, sqlpassword);

            String showPrice = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showPrice);
            preparedStatement.setInt(1, productId);


            ResultSet result = preparedStatement.executeQuery();


            if (result.next()) {
                productPrice = result.getInt("price");
                preparedStatement.close();
                result.close();
                connection.close();
                return productPrice;
            } else {
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

    //for logic
    public int lastProductId(){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showLastProductId = "SELECT productId AS lastId FROM product ORDER BY productId DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(showLastProductId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int productId = result.getInt("lastId");
                return productId;
            }
            preparedStatement.close();
            result.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}

