package icedcoffee.coldbrewco;
import Database.DatabaseShow;
import Database.DatabaseUpdate;
import ForEnkeepingLoginId.LoginId;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;

public class ControllerMain {
    @FXML
    private Label EmpId;
    @FXML
    private Label EmpName;
    @FXML
    private Label empUsername;
    @FXML
    private Label curpass;
    @FXML
    private Label newpass;
    @FXML
    private PasswordField curpassfield;
    @FXML
    private PasswordField newpassfield;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView backButton;
    @FXML
    private ImageView logOut;
    @FXML
    private ImageView selectOrder;


    //to go to profile details
    @FXML
    protected void ProfileDetailsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("EmployeeViewAccount.fxml"));
        Pane viewAccountDetails = fxmlLoader.load();
        ControllerMain controller = fxmlLoader.getController();
        controller.showProfileDetails();

        Stage currentStage = (Stage) selectOrder.getScene().getWindow();
        Pane rootPane = (Pane) currentStage.getScene().getRoot();
        double centerX = (rootPane.getWidth() - viewAccountDetails.getPrefWidth()) / 2;
        double centerY = (rootPane.getHeight() - viewAccountDetails.getPrefHeight()) / 2;
        viewAccountDetails.setLayoutX(centerX);
        viewAccountDetails.setLayoutY(centerY);
        rootPane.getChildren().add(viewAccountDetails);
    }

    //to set up profile details content
    @FXML
    private void showProfileDetails() throws IOException{
        DatabaseShow show = new DatabaseShow();
        EmpId.setText(""+LoginId.getLoginId());
        EmpName.setText(""+show.showName(LoginId.getLoginId()));
        empUsername.setText(""+show.showUserName(LoginId.getLoginId()));
    }

    //to go back to main page
    @FXML
    protected void backButtonClick() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainAccount = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.setScene(mainAccount);
        currentStage.setTitle("Main Page");
        currentStage.centerOnScreen();
        currentStage.setResizable(false);
        currentStage.show();
    }


    //when changePassword is click makes change password field visible
    @FXML
    protected void changePassClick(){
        curpass.setVisible(true);
        newpass.setVisible(true);
        curpassfield.setVisible(true);
        newpassfield.setVisible(true);
        confirmButton.setVisible(true);
    }

    //when confirm button in change pass is clicked
    @FXML
    protected void confirmButtonClick(){
        DatabaseShow show = new DatabaseShow();
        DatabaseUpdate update = new DatabaseUpdate();
        String currpassword = curpassfield.getText();
        String newpassword = newpassfield.getText();

        if (!currpassword.equals(show.showPassword(LoginId.getLoginId()))) {
            JOptionPane.showMessageDialog(null, "The current password is incorrect");
        } else if(currpassword.equals(newpassword)){
            JOptionPane.showMessageDialog(null, "It is the same password");
        }else if (newpassword.length()<5||newpassword.length()>10) {
            JOptionPane.showMessageDialog(null, "The new password must be 5-10 characters long");
        } else if (currpassword.equals("")||newpassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Input valid password");
        }else {
            update.updateEmpPassword(LoginId.getLoginId(), newpassword);
            JOptionPane.showMessageDialog(null, "Password updated successfully");
            curpass.setVisible(false);
            newpass.setVisible(false);
            curpassfield.setVisible(false);
            newpassfield.setVisible(false);
            confirmButton.setVisible(false);
        }
    }

    //to go to select order page
    @FXML
    protected void onSelectOrderButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("Order Page.fxml"));
        Scene Orderselect = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) selectOrder.getScene().getWindow();
        currentStage.setScene(Orderselect);
        currentStage.setTitle("Order Page");
        currentStage.centerOnScreen();
        currentStage.setResizable(false);
        currentStage.show();
    }

    //to check order history from current userId
    @FXML
    protected void onCheckPreviousOrderButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("SalesPage.fxml"));
        Scene userSales = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) selectOrder.getScene().getWindow();
        currentStage.setScene(userSales);
        currentStage.setTitle("User Sales Page");
        currentStage.centerOnScreen();
        currentStage.setResizable(false);
        currentStage.show();
    }

    //to go back to log in page
    @FXML
    protected void onLogOutButtonClick() throws IOException {
        JOptionPane.showMessageDialog(null, "User logged out");
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("Login.fxml"));
        Scene loginAccount = new Scene(fxmlLoader.load(), 600, 400);

        Stage currentStage = (Stage) logOut.getScene().getWindow();
        currentStage.setScene(loginAccount);
        currentStage.setTitle("Login Page");
        currentStage.centerOnScreen();
        currentStage.setResizable(false);
        currentStage.show();
    }

    //to exit program
    @FXML
    protected void onExitMainPageButtonClick(){
        System.exit(0);
    }

}
