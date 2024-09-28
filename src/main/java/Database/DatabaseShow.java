package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseShow {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();


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


    public int getProductQuantity(int productId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showId = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showId);
            preparedStatement.setInt(1, productId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int productQuantity = result.getInt("productQuantity");
                return productQuantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int totalSaleOnSpecificUser(int userId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showTotal = "SELECT userSales from user where userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showTotal);
            preparedStatement.setInt(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int totalSale = result.getInt("userSales");
                return totalSale;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public ObservableList<PreviousOrders> getPreviousOrdersUser(int userId){
        ObservableList<PreviousOrders> orderList = FXCollections.observableArrayList();
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showOrderList = "SELECT productId,orderQuantity,date,subTotal FROM orders WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showOrderList);
            preparedStatement.setInt(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int productId = result.getInt("productId");
                int orderQuantity = result.getInt("orderQuantity");
                Date date = result.getDate("date");
                int subTotal = result.getInt("subTotal");

                String name = showProductName(productId);
                int price = showProductPrice(productId);
                String dateStr = date.toString();

                orderList.add(new PreviousOrders(name,price,orderQuantity,dateStr,subTotal));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public String showAdminName(int AdminId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showName = "SELECT * FROM admin WHERE adminId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showName);
            preparedStatement.setInt(1, AdminId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String Name = result.getString("username");
                return Name;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

}

