package Main;

import Database.DatabaseLink;
import ObservableTableOrganizers.PreviousTransactionsOfSpecificEmployee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

//abstraction for emp
abstract class EmployeeIdentificationFunctions{
    public abstract String showName(int employeeId);
    public abstract String showUserName(int employeeId);
    public abstract String showPassword(int employeeId);
}

public class Employee extends EmployeeIdentificationFunctions{

    //encapsulation
    private static int employeeId;
    public static void setEmployeeId(int userId) {employeeId = userId;}
    public static int getEmployeeId() {return employeeId;}

    private String empFullName;
    private String username;
    private String password;

    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    public int returnLoginEmployee(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String checkData = ("SELECT empId,username,password FROM employee WHERE username = ? AND password = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(checkData);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int user = resultSet.getInt("empId");
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

    //to get sale on specific employee id
    public int Sales(int employeeId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showTotal = "SELECT SUM(subTotal) AS Sales FROM orders WHERE empId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showTotal);
            preparedStatement.setInt(1, employeeId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("Sales");
            }
            preparedStatement.close();
            result.close();
            connection.close();
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
                empFullName = result.getString("empFullName");
                return empFullName;
            }
            preparedStatement.close();
            result.close();
            connection.close();
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
                username = result.getString("username");
                return username;
            }
            preparedStatement.close();
            result.close();
            connection.close();

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
                password = result.getString("password");
                return password;
            }

            preparedStatement.close();
            result.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    //limiter for showEmployeeTransactions in admin class
    public int showLastEmployeeId(){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showLastUserId = "SELECT empId AS lastId FROM employee ORDER BY empId DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(showLastUserId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int userId = result.getInt("lastId");
                return userId;
            }
            preparedStatement.close();
            result.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    // to show the table for previous orders made on specific userId
    public ObservableList<PreviousTransactionsOfSpecificEmployee> showPreviousOrdersEmployee(int employeeId){
        ObservableList<PreviousTransactionsOfSpecificEmployee> orderList = FXCollections.observableArrayList();
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
                orderList.add(new PreviousTransactionsOfSpecificEmployee(name,price,orderQuantity,dateStr,subTotal));
            }
            preparedStatement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

}
