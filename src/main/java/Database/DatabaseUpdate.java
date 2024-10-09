package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdate {

    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    //to update employee password
    public void updateEmpPassword(int employeeId, String password){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            PreparedStatement updateEmpPassword = connection.prepareStatement("UPDATE employee SET password=? WHERE empId=?");
            updateEmpPassword.setString(1, password);
            updateEmpPassword.setInt(2, employeeId);
            updateEmpPassword.executeUpdate();
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to remove quantity in admin control
    public void deductProductQuantity(int productId, int quantityRemoved) {
        try {

            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String updateBalanceQuery = "UPDATE product SET productQuantity = ? WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery);
            preparedStatement.setInt(1, quantityRemoved);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to add product quantity in admin control
    public void addProductQuantity(int productId, int quantityAdded) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String updateBalanceQuery = "UPDATE product SET productQuantity = ? WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery);
            preparedStatement.setInt(1, quantityAdded); // Set the amount to add
            preparedStatement.setInt(2, productId);       // Set the user ID

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //product quantity will temporarily be deducted when transferred from order page to order details
    public void productTemporaryDeductionQuantity(int productId, int quantityBought) {
        try {

            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String updateQuantityQuery = "UPDATE product SET productQuantity = (productQuantity - ?) WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuantityQuery);
            preparedStatement.setInt(1, quantityBought);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //product quantity will be added back if removed from order details
    public void productAddedBack(int productId, int quantityAdded) {
        try {

            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String updateQuantityQuery = "UPDATE product SET productQuantity = (productQuantity + ?) WHERE productId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuantityQuery);
            preparedStatement.setInt(1, quantityAdded);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
