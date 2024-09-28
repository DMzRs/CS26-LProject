package Database;

import java.sql.*;

public class DatabaseInsert {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    public void newOrderUser(int userId, int productId, int orderQuantity, int subTotal){
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String insertOrderQuery = "INSERT INTO orders (userId,productId,orderQuantity,date,subTotal)VALUES(?,?,?,CURRENT_DATE,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertOrderQuery);
            preparedStatement.setInt(1, userId);
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
