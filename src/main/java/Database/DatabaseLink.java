package Database;

public class DatabaseLink {
    public static String getsqlurl(){
        String sqlurl = "jdbc:mysql://127.0.0.1:3306/icedcoffeesystem";
        return sqlurl;
    }
    public static String getsqluser(){
        String sqluser = "root";
        return sqluser;
    }
    public static String getsqlpassword(){
        String sqlpassword = "nicholasbalodo06";
        return sqlpassword;
    }
}
