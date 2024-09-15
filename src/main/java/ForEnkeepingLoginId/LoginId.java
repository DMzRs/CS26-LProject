package ForEnkeepingLoginId;

public class LoginId {
    // Static variable to hold the login ID
    private static int loginId;

    // Static method to set the login ID
    public static void setLoginId(int userId) {
        loginId = userId;
    }

    // Static method to get the login ID
    public static int getLoginId() {
        return loginId;
    }
}
