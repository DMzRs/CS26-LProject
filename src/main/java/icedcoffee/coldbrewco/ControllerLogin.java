package icedcoffee.coldbrewco;
import Main.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ControllerLogin {


    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;


    @FXML
    private void initialize() {
        loginButton.setDefaultButton(true);

        loginButton.setOnAction(event -> {
            try {
                onLoginButtonClick();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //for normal user login
    @FXML
    protected void onLoginButtonClick() throws IOException {
        Employee employee = new Employee();
        String username = usernameField.getText();
        String password = passwordField.getText();
        int currentLoginID = employee.returnLoginEmployee(username,password);
        if(currentLoginID > 0){
            Employee.setEmployeeId(currentLoginID);
            JOptionPane.showMessageDialog(null,"Login Successful");
            switchtoMain();
        } else {
            JOptionPane.showMessageDialog(null,"Username or Password Incorrect!");
        }
    }



    //to switch to admin login Page
    @FXML
    protected void onHiddenAdminLoginButtonClick() throws IOException {
        switchtoAdminLogin();
    }



    //to switch stage to admin login
    private void switchtoAdminLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("adminLogin.fxml"));
        Scene adminLogin = new Scene(fxmlLoader.load(), 650, 400);

        Stage currentStage = (Stage) usernameField.getScene().getWindow();
        currentStage.setScene(adminLogin);
        currentStage.setTitle("Admin Login Page");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
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
        currentStage.setResizable(false);
        currentStage.show();
    }
}
