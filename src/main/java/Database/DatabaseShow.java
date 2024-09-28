package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.concurrent.Callable;

public class DatabaseShow {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    //to show product description using product id
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

    //to show product name using product id
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
                return price;
            }   else   {
                System.out.println("No product found with productId: " + productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // to show product id using product name
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

    //to get the product quantity using product id
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

    //to get sale on specific user Id
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

    // to get the table for previous orders made on specific userId
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

    //to get all user sales one by one
    public ObservableList<EmployeeSales> getEmployeeSalesUser(){
        ObservableList<EmployeeSales> employeeSalesList = FXCollections.observableArrayList();
        try{
            int userIdLimit = showLastUserId();
            int userId = 1;
            while(userId <= userIdLimit) {
                Connection connection = DriverManager.getConnection(sqlurl, sqluser, sqlpassword);
                String showEmpListSales = "SELECT SUM(orderQuantity) AS totalCoffeeSold, SUM(subTotal) AS TotalSales FROM orders WHERE userId = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(showEmpListSales);
                preparedStatement.setInt(1, userId);
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    int totalCoffeeSold = result.getInt("totalCoffeeSold");
                    int totalSales = result.getInt("TotalSales");

                    String userName = showName(userId);

                    employeeSalesList.add(new EmployeeSales(userName, totalCoffeeSold, totalSales));
                }
                userId++;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employeeSalesList;
    }

    //limiter for getEmployeeSales
    public int showLastUserId(){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showLastUserId = "SELECT userId AS lastId FROM user ORDER BY userId DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(showLastUserId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int userId = result.getInt("lastId");
                return userId;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //to show the name of a user
    public String showName(int userId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showName = "SELECT username FROM user WHERE userId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showName);
            preparedStatement.setInt(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String Name = result.getString("username");
                return Name;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public int showOverallSales(){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showTotalSales = "Select SUM(subTotal) AS OverallSales FROM orders";
            PreparedStatement preparedStatement = connection.prepareStatement(showTotalSales);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int overallSales = result.getInt("OverallSales");
                return overallSales;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    //to show admin name
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

