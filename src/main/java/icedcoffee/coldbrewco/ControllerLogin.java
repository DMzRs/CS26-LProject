package icedcoffee.coldbrewco;
import Database.DatabaseLogin;
import ForEnkeepingLoginId.LoginId;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ControllerLogin {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField newUserField;
    @FXML
    private PasswordField newPassField;
    @FXML
    private PasswordField passwordField;


    @FXML
    protected void onLoginButtonClick() throws IOException {
        DatabaseLogin login = new DatabaseLogin();
        String username = usernameField.getText();
        String password = passwordField.getText();
        int currentLoginID = login.ReturnLoginUser(username,password);
        if(currentLoginID > 0){
            LoginId.setLoginId(currentLoginID);
            JOptionPane.showMessageDialog(null,"Login Successful");
            switchtoMain();
        } else {
            JOptionPane.showMessageDialog(null,"Login Failed");
        }
    }


    @FXML
    protected void onCreateAccountButtonClick() {
        String NewUsername = newUserField.getText();
        String NewPassword = newPassField.getText();
        if (NewUsername.equals("") && NewPassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password");
        } else {
            DatabaseLogin login = new DatabaseLogin();
            boolean isCreated = login.RegisterUser(NewUsername, NewPassword);
            if (isCreated) {
                JOptionPane.showMessageDialog(null, "User Created");
            } else {
                JOptionPane.showMessageDialog(null, "User Not Created");
            }
        }
    }

    //button to switch to register account
    @FXML
    protected void onSignUpHereButtonClick() throws IOException {
        switchtoRegisterAccount();
    }

    //button to switch back to log in account
    @FXML
    protected void onLogInHereButtonClick() throws IOException {
        switchtoLoginAccount();
    }

    //to switch stage to register account
    private void switchtoRegisterAccount() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("createAcc.fxml"));
        Scene registerAccount = new Scene(fxmlLoader.load(), 600, 400);

        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        currentStage.setScene(registerAccount);
        currentStage.setTitle("Register Page");
        currentStage.show();
    }

    //to switch stage to log in account again
    private void switchtoLoginAccount() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("Login.fxml"));
        Scene loginAccount = new Scene(fxmlLoader.load(), 600, 400);

        Stage currentStage = (Stage) newUserField.getScene().getWindow();
        currentStage.setScene(loginAccount);
        currentStage.setTitle("Login Page");
        currentStage.show();

    }

    //to switch to main page
    private void switchtoMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainAccount = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        currentStage.setScene(mainAccount);
        currentStage.setTitle("Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }
}
