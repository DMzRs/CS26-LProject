package icedcoffee.coldbrewco;
import Database.DatabaseShow;
import Database.DatabaseUpdate;
import ForEnkeepingLoginId.LoginId;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.IOException;

public class ControllerMain {
    @FXML
    private ImageView checkCash;
    @FXML
    private ImageView logOut;
    @FXML
    private ImageView selectOrder;
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private TextField cashAmountField;

    //to go to select order page
    @FXML
    protected void onSelectOrderButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("Order Page.fxml"));
        Scene Orderselect = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) selectOrder.getScene().getWindow();
        currentStage.setScene(Orderselect);
        currentStage.setTitle("Order Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //to check order history from current userId
    @FXML
    protected void onCheckPreviousOrderButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("OrderLists.fxml"));
        Scene prevOrders = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) selectOrder.getScene().getWindow();
        currentStage.setScene(prevOrders);
        currentStage.setTitle("Previous Orders Page");
        currentStage.centerOnScreen();
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
        currentStage.show();
    }

    //to exit program
    @FXML
    protected void onExitMainPageButtonClick(){
        System.exit(0);
    }

}
