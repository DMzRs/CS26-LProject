package Main;

import Database.DatabaseLink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Order {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    public void addOrder(int empId, int productId, int orderQuantity, int subTotal){
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String insertOrderQuery = "INSERT INTO orders (empId,productId,orderQuantity,date,subTotal)VALUES(?,?,?,CURRENT_DATE,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertOrderQuery);
            preparedStatement.setInt(1, empId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, orderQuantity);
            preparedStatement.setInt(4, subTotal);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
