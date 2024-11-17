package Main;

import Database.DatabaseLink;
import ObservableTableOrganizers.PreviousOrders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Employee {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    public int ReturnLoginEmployee(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String checkData = ("SELECT empId,username,password FROM employee WHERE username = ? AND password = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(checkData);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int user= resultSet.getInt("empId");
                return user;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //to update employee password
    public void changePassword(int employeeId, String password){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            PreparedStatement changePassword = connection.prepareStatement("UPDATE employee SET password=? WHERE empId=?");
            changePassword.setString(1, password);
            changePassword.setInt(2, employeeId);
            changePassword.executeUpdate();
            changePassword.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //to update specific user sale per transaction made
    public void updateEmployeeSales(int empId, int totalPrice){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            PreparedStatement updateEmployeeSales = connection.prepareStatement("update employee set empSales = (empSales + ?) where empId = ?");
            updateEmployeeSales.setInt(1, totalPrice);
            updateEmployeeSales.setInt(2, empId);
            updateEmployeeSales.executeUpdate();
            updateEmployeeSales.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to get sale on specific employee id
    public int Sales(int employeeId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showTotal = "SELECT empSales from employee where empId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showTotal);
            preparedStatement.setInt(1, employeeId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int totalSale = result.getInt("empSales");
                preparedStatement.close();
                connection.close();
                return totalSale;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //to show the name of a user
    public String showName(int employeeId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showName = "SELECT empFullName FROM employee WHERE empId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showName);
            preparedStatement.setInt(1, employeeId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String Name = result.getString("empFullName");
                preparedStatement.close();
                connection.close();
                return Name;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    //to get employee username
    public String showUserName(int employeeId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showUserName = "SELECT username FROM employee WHERE empId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showUserName);
            preparedStatement.setInt(1, employeeId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String username = result.getString("username");
                preparedStatement.close();
                connection.close();
                return username;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    //to get currentPassword
    public String showPassword(int employeeId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showPass = "SELECT password FROM employee WHERE empId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showPass);
            preparedStatement.setInt(1, employeeId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String Password = result.getString("password");
                preparedStatement.close();
                connection.close();
                return Password;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    //limiter for getEmployeeSales in admin class
    public int showLastEmployeeId(){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showLastUserId = "SELECT empId AS lastId FROM employee ORDER BY empId DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(showLastUserId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int userId = result.getInt("lastId");
                preparedStatement.close();
                connection.close();
                return userId;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    // to show the table for previous orders made on specific userId
    public ObservableList<PreviousOrders> showPreviousOrdersEmployee(int employeeId){
        ObservableList<PreviousOrders> orderList = FXCollections.observableArrayList();
        Product product = new Product();
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showOrderList = "SELECT productId,orderQuantity,date,subTotal FROM orders WHERE empId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showOrderList);
            preparedStatement.setInt(1, employeeId);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int productId = result.getInt("productId");
                int orderQuantity = result.getInt("orderQuantity");
                Date date = result.getDate("date");
                int subTotal = result.getInt("subTotal");

                String name = product.showProductName(productId);
                int price = product.showProductPrice(productId);
                String dateStr = date.toString();
                orderList.add(new PreviousOrders(name,price,orderQuantity,dateStr,subTotal));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
