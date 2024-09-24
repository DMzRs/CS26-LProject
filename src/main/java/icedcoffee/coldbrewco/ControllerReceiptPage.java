package icedcoffee.coldbrewco;

import java.time.LocalDate;
import Database.DatabaseShow;
import Database.OrderItem;
import ForEnkeepingLoginId.LoginId;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerReceiptPage {

    // Labels for receipt details
    @FXML
    private Label receiptAddress;
    @FXML
    private Label receiptContactNumber;
    @FXML
    private Label receiptDate;
    @FXML
    private Label BalanceLabelR;
    @FXML
    private Label TotalLabel;
    @FXML
    private Label ChangeLabel;

    // TableView and columns for order items
    @FXML
    private TableView<OrderItem> ReceiptTable;
    @FXML
    private TableColumn<OrderItem, String> CoffeeName;
    @FXML
    private TableColumn<OrderItem, Integer> PriceCoffee;
    @FXML
    private TableColumn<OrderItem, Integer> QuantityCoffee;
    @FXML
    private TableColumn<OrderItem, Integer> SubTotalCoffee;

    // Method to set order items in the TableView
    public void setOrderItems(ObservableList<OrderItem> items) {
        if (ReceiptTable != null) {
            ReceiptTable.setItems(items);
            updateReceiptDetails();
        } else {
            System.out.println("ReceiptTable is null!");
        }
    }

    // Initialize method to configure the TableView columns
    @FXML
    public void initialize() {
        // Set up the cell value factories
        CoffeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PriceCoffee.setCellValueFactory(new PropertyValueFactory<>("price"));
        QuantityCoffee.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        SubTotalCoffee.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
    }

    private void updateReceiptDetails() {
        DatabaseShow show = new DatabaseShow();
        ObservableList<OrderItem> allItems = ReceiptTable.getItems();


        int total = 0;
        for (OrderItem item : allItems) {
            int subTotal = item.getSubTotal(); // Ensure this returns the expected value
            total += subTotal;
        }

        int change = show.showMoney(LoginId.getLoginId());
        BalanceLabelR.setText("Balance: " + (change + total));
        TotalLabel.setText("Total: " + total);
        ChangeLabel.setText("Change: " + change);
        LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        receiptDate.setText("Date: "+day+"/"+month+"/"+year);
    }

    //Close Event to go Back to Main Page
    @FXML
    protected void onCloseButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) BalanceLabelR.getScene().getWindow();
        currentStage.setScene(mainPage);
        currentStage.setTitle("Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //
    @FXML
    protected void onPrintButtonClicked() throws IOException{

    }
}
