package icedcoffee.coldbrewco;

import Database.DatabaseLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ControllerAdminCreateEmployeeAcc {
    @FXML
    private TextField newUserField;
    @FXML
    private TextField newPassField;
    @FXML
    private TextField fullNameField;
    @FXML
    private ImageView backToAdminMain;

    @FXML
    private void backtoMainPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminMainPage.fxml"));
        Scene adminPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backToAdminMain.getScene().getWindow();
        currentStage.setScene(adminPage);
        currentStage.setTitle("Admin Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }
    //to create new user account
    @FXML
    protected void onCreateAccountButtonClick() {
        String EmpFullName = fullNameField.getText();
        String NewUsername = newUserField.getText();
        String NewPassword = newPassField.getText();
        if (NewUsername.equals("") || NewPassword.equals("") || EmpFullName.equals("")) {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password");
        } else if(NewUsername.length()<5 || NewPassword.length()<5 || NewUsername.length()>10 || NewPassword.length()>10) {
            JOptionPane.showMessageDialog(null, "Username and Password must be 5-10 characters long");
        }else {
            DatabaseLogin login = new DatabaseLogin();
            boolean isCreated = login.RegisterEmployee( EmpFullName, NewUsername, NewPassword);
            fullNameField.setText("");
            newUserField.setText("");
            newPassField.setText("");
            if (isCreated) {
                JOptionPane.showMessageDialog(null, "User Created");
            } else {
                JOptionPane.showMessageDialog(null, "User Not Created");
            }
        }
    }
}
