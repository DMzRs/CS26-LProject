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

    //Click Event to switch to check balance page
    @FXML
    protected void onCheckCashButtonClick() throws IOException {
    switchtoCheckBalance();
    }

    //Button click show balance
    @FXML
    protected void onCheckBalanceButtonClick(){
    showBalance();
    }

    //to switch check balance stage
    @FXML
    private void switchtoCheckBalance()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("checkAccountBal.fxml"));
        Scene checkBalance = new Scene(fxmlLoader.load(), 375, 575);


        Stage currentStage = (Stage) checkCash.getScene().getWindow();
        currentStage.setScene(checkBalance);
        currentStage.setTitle("Balance Page");
        currentStage.centerOnScreen();
        currentStage.show();

    }

    //to show balance
    @FXML
    private void showBalance(){
        DatabaseShow show = new DatabaseShow();
        int userid = LoginId.getLoginId();
        int balance = show.showMoney(userid);
        currentBalanceLabel.setText(" "+balance);

    }


    //to save funds amount added
    @FXML
    protected void onAddButtonClick(){
        DatabaseUpdate update = new DatabaseUpdate();
        LoginId loginId = new LoginId();

        String cashAmount = cashAmountField.getText();
        int cash = Integer.parseInt(cashAmount);
        int userid = loginId.getLoginId();

        boolean cofirmAdd = update.addMoney(userid,cash);
        if(cofirmAdd){
            showBalance();
            JOptionPane.showMessageDialog(null, "You have successfully added money to the database");
        }
        else{
            JOptionPane.showMessageDialog(null, "You have not successfully added money to the database");
        }
    }

    //to delete input text
    @FXML
    protected void onCancelButtonClick(){
        cashAmountField.clear();
    }

    //toReturnMainPage
    @FXML
    protected void onReturnMainPageButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainAccount = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) cashAmountField.getScene().getWindow();
        currentStage.setScene(mainAccount);
        currentStage.setTitle("Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //to exit program
    @FXML
    protected void onExitMainPageButtonClick(){
        System.exit(0);
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

}
