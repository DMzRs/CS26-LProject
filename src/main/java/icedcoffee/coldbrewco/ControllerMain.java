package icedcoffee.coldbrewco;
import Main.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
    private Pane EmpAccView;
    @FXML
    private ImageView logOut;
    @FXML
    private ImageView selectOrder;
    @FXML
    private ImageView empProfile;
    @FXML
    private ImageView empProfileMain;
    @FXML
    private Label EmpNameMain;

    //intialize user details
    @FXML
    public void initialize() {
        EmpAccView.setVisible(false);
        Employee emp = new Employee();
        setProfileImage(emp.getEmployeeId());
        EmpNameMain.setText(""+emp.showName(emp.getEmployeeId()));
    }

    //to preload image profile
    private void setProfileImage(int empId) {
        InputStream imageStream = getClass().getResourceAsStream("/EmployeeProfiles/" + empId + ".jpg");

        Image profileImage = null;
        if (imageStream != null) {
            profileImage = new Image(imageStream);
        } else {
            profileImage = new Image(getClass().getResourceAsStream("/EmployeeProfiles/ProfileButton.jpg")); // Default image
        }

        // Set the profile image to the ImageView
        empProfile.setImage(profileImage);
    }

    //to change profile picture
    @FXML
    protected void ChangeProfile() {
        // Open a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");

        // Restrict file types to images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) empProfileMain.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                Employee employee = new Employee();
                int empId = employee.getEmployeeId();

                // Define a writable directory (e.g., user's home directory)
                String writableDirectory = System.getProperty("user.home") + "/ColdBrewCorp/EmployeeProfiles";
                File dir = new File(writableDirectory);

                // Create the directory if it doesn't exist
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Define the target file in the writable directory
                File targetFile = new File(dir, empId + ".jpg");

                // Copy the selected file to the writable directory
                Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Load the new profile image into the ImageView
                try (InputStream imageStream = new FileInputStream(targetFile)) {
                    Image newProfileImage = new Image(imageStream);
                    empProfileMain.setImage(newProfileImage);
                    empProfile.setImage(newProfileImage);
                }

                JOptionPane.showMessageDialog(null, "Profile picture updated successfully.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating profile picture: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.");
        }
    }




    //to go to profile details
    @FXML
    protected void ProfileDetailsButtonClick() throws IOException {
        EmpAccView.setVisible(true);
        showProfileDetails();
    }

    //to set up profile details content
    @FXML
    private void showProfileDetails() throws IOException{
        Employee employee = new Employee();
        EmpId.setText(""+ employee.getEmployeeId());
        EmpName.setText(""+employee.showName(employee.getEmployeeId()));
        empUsername.setText(""+employee.showUserName(employee.getEmployeeId()));

        InputStream imageStream = getClass().getResourceAsStream("/EmployeeProfiles/" + employee.getEmployeeId() + ".jpg");
        Image profileImage = null;
        if (imageStream != null) {
            profileImage = new Image(imageStream);
        } else {
            profileImage = new Image(getClass().getResourceAsStream("/EmployeeProfiles/ProfilePicture.jpg")); // Default image
        }
        empProfileMain.setImage(profileImage);
    }

    //to go back to main page
    @FXML
    protected void backButtonClick() throws IOException{
        EmpAccView.setVisible(false);
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
        Employee employee = new Employee();

        String currpassword = curpassfield.getText();
        String newpassword = newpassfield.getText();

        if (!currpassword.equals(employee.showPassword(employee.getEmployeeId()))) {
            JOptionPane.showMessageDialog(null, "The current password is incorrect");
        } else if(currpassword.equals(newpassword)){
            JOptionPane.showMessageDialog(null, "It is the same password");
        }else if (newpassword.length()<5||newpassword.length()>10) {
            JOptionPane.showMessageDialog(null, "The new password must be 5-10 characters long");
        } else if (currpassword.equals("")||newpassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Input valid password");
        }else {
            employee.changePassword(employee.getEmployeeId(), newpassword);
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
    protected void onCheckSalesButtonClick() throws IOException {
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
