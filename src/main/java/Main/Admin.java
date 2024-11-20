package Main;

import Database.DatabaseLink;
import ObservableTableOrganizers.EmployeeTransactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Admin {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    public static int adminId;
    public static void setAdminId(int id) {
        adminId = id;
    }
    public static int getAdminId() {
        return adminId;
    }

    //to return admin user and pass
    public int returnLoginAdmin(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String checkData = ("SELECT adminId,username,password FROM admin WHERE username = ? AND password = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(checkData);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int user= resultSet.getInt("adminId");
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

    //to show admin name
    public String showAdminName(int AdminId){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showName = "SELECT * FROM admin WHERE adminId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(showName);
            preparedStatement.setInt(1, AdminId);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getString("username");
            }
            preparedStatement.close();
            result.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    //for user create account
    public boolean addEmployeeAccount(String FullName, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String insertData = ("INSERT INTO employee (empFullName,username, password) VALUES (?, ?, ?)");

            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            preparedStatement.setString(1, FullName);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //to remove quantity in admin control
    public void removeProductQuantity(int productId, int quantityRemoved) {
        try {

            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String updateProductQuantity = "UPDATE product SET productQuantity = ? WHERE productId = ?";
            PreparedStatement deductQuantity = connection.prepareStatement(updateProductQuantity);
            deductQuantity.setInt(1, quantityRemoved);
            deductQuantity.setInt(2, productId);

            deductQuantity.executeUpdate();
            deductQuantity.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to add product quantity in admin control
    public void addProductQuantity(int productId, int quantityAdded) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String addProductQuantity = "UPDATE product SET productQuantity = ? WHERE productId = ?";
            PreparedStatement addQuantity = connection.prepareStatement(addProductQuantity);
            addQuantity.setInt(1, quantityAdded); // Set the amount to add
            addQuantity.setInt(2, productId);       // Set the user ID

            addQuantity.executeUpdate();
            addQuantity.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //product quantity will temporarily be deducted when transferred from order page to order details
    public void productTemporaryDeductionQuantity(int productId, int quantityBought) {
        try {

            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String updateQuantityQuery = "UPDATE product SET productQuantity = (productQuantity - ?) WHERE productId = ?";
            PreparedStatement updateQuantity = connection.prepareStatement(updateQuantityQuery);
            updateQuantity.setInt(1, quantityBought);
            updateQuantity.setInt(2, productId);

            updateQuantity.executeUpdate();
            updateQuantity.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //product quantity will be added back if removed from order details
    public void productAddedBackFromTemporaryRemovedItems(int productId, int quantityAdded) {
        try {

            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);

            String updateQuantityQuery = "UPDATE product SET productQuantity = (productQuantity + ?) WHERE productId = ?";
            PreparedStatement updateQuantity = connection.prepareStatement(updateQuantityQuery);
            updateQuantity.setInt(1, quantityAdded);
            updateQuantity.setInt(2, productId);

            updateQuantity.executeUpdate();
            updateQuantity.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to get all transaction with employees' names
    public ObservableList<EmployeeTransactions> showEmployeeTransactions(){
        ObservableList<EmployeeTransactions> employeeSalesList = FXCollections.observableArrayList();
        Employee employee = new Employee();
        Product product = new Product();
        try{
            int empIdLimit = employee.showLastEmployeeId();
            int empId = 1;
            while(empId <= empIdLimit) {
                Connection connection = DriverManager.getConnection(sqlurl, sqluser, sqlpassword);
                String showEmpListSales = "SELECT SUM(orderQuantity) AS SoldQuantity, SUM(subTotal) AS TotalSales, date AS Date, productId AS coffeeId FROM orders WHERE empId = ? GROUP BY date,productId";
                PreparedStatement preparedStatement = connection.prepareStatement(showEmpListSales);
                preparedStatement.setInt(1, empId);
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    int soldQuantity = result.getInt("SoldQuantity");
                    String dateStr = result.getDate("Date").toString();
                    int totalSales = result.getInt("TotalSales");
                    int coffeesId = result.getInt("coffeeId");

                    String coffeeName = product.showProductName(coffeesId);
                    String userName = employee.showName(empId);

                    employeeSalesList.add(new EmployeeTransactions(userName, coffeeName, dateStr, soldQuantity, totalSales));
                }
                empId++;
                connection.close();
                result.close();
                preparedStatement.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return employeeSalesList;
    }

    //to get overall sales
    public int totalSales(){
        try{
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String showTotalSales = "Select SUM(subTotal) AS OverallSales FROM orders";
            PreparedStatement preparedStatement = connection.prepareStatement(showTotalSales);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("OverallSales");
            }
            connection.close();
            result.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}

