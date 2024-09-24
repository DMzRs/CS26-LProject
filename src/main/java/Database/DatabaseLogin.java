package Database;

import ForEnkeepingLoginId.LoginId;

import java.sql.*;

public class DatabaseLogin {
    public String sqlurl = DatabaseLink.getsqlurl();
    public String sqluser = DatabaseLink.getsqluser();
    public String sqlpassword = DatabaseLink.getsqlpassword();

    //for user login
    public int ReturnLoginUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String checkData = ("SELECT UserId,username,password FROM user WHERE username = ? AND password = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(checkData);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int user= resultSet.getInt("UserId");
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

    //for admin login
    public int ReturnLoginAdmin(String username, String password) {
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

    //for user create account
    public boolean RegisterUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(sqlurl,sqluser,sqlpassword);
            String insertData = ("INSERT INTO user (username, password) VALUES (?, ?)");

            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
