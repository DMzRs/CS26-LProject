package icedcoffee.coldbrewco;

import Database.*;
import ForEnkeepingLoginId.LoginId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ControllerOrderDetailsPage {

    @FXML
    private TableView<OrderItem> orderCoffeeTable;

    @FXML
    private TableColumn<OrderItem, String> nameColumn;

    @FXML
    private TableColumn<OrderItem, Integer> priceColumn;

    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;

    @FXML
    private TableColumn<OrderItem, String> subTotalColumn;


    private ObservableList<OrderItem> selectedItems = FXCollections.observableArrayList();

    public void setOrderItems(ObservableList<OrderItem> items) {
        selectedItems.setAll(items); // Populate the ObservableList with the passed items
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        // Get the items from the singleton
        orderCoffeeTable.setItems(OrderItemStorage.getInstance().getSelectedItems());
    }

    public void addOrderItem(OrderItem orderItem) {
        selectedItems.add(orderItem);
    }

    //to Add More Items
    @FXML
    protected void addItemsButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("Order Page.fxml"));
        Scene selectAgain = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) orderCoffeeTable.getScene().getWindow();
        currentStage.setScene(selectAgain);
        currentStage.setTitle("Order Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //to Remove Items
    @FXML
    private void onRemoveItemButtonClick() {
        OrderItem selectedItem = orderCoffeeTable.getSelectionModel().getSelectedItem();

        // to Check if an item is selected
        if (selectedItem != null) {
            ObservableList<OrderItem> items = orderCoffeeTable.getItems();
            items.remove(selectedItem);

            //Just For Logs
            System.out.println("Removed item: " + selectedItem.getName());
        } else {
            JOptionPane.showMessageDialog(null,"Please select an item to remove");
        }
    }

    //to Check Out Orders
    @FXML
    private void onCheckOutButtonClick() {
        DatabaseShow show = new DatabaseShow();
        DatabaseInsert insert = new DatabaseInsert();
        int TotalPrice = 0;
        // Get all items from the TableView
        ObservableList<OrderItem> allItems = orderCoffeeTable.getItems();

        // Create a StringBuilder to collect the names
        StringBuilder coffeeNames = new StringBuilder("All Selected Coffee:\n");

        // Iterate through the items to retrieve coffee names
        for (OrderItem item : allItems) {
            coffeeNames.append(item.getName())
                    .append(" (Quantity: ").append(item.getQuantity())
                    .append(")\n");
            TotalPrice += item.getSubTotal();
        }

        // Show confirmation dialog
        int confirmation = JOptionPane.showConfirmDialog(
                null,
                coffeeNames.toString() + "Total Price: " + TotalPrice,
                "Confirm Your Order",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Handle the user's response
        if (confirmation == JOptionPane.YES_OPTION) {
            // Proceed with the checkout
            System.out.println("Order confirmed!");
            int balance = show.showMoney(LoginId.getLoginId());
            if (balance >= TotalPrice) {
                while (!allItems.isEmpty()) {
                    OrderItem itemToRemove = allItems.get(0); // Get the first item
                    String coffeeName = itemToRemove.getName();
                    int itemQuantity = itemToRemove.getQuantity();
                    int subTotal = itemToRemove.getSubTotal();
                    insert.newOrderUser(LoginId.getLoginId(),show.showProductId(coffeeName),itemQuantity,subTotal);
                    allItems.remove(itemToRemove); // Remove the item
                }
            } else {
                System.out.println("Balance is not good");
            }
        } else {
            System.out.println("Order canceled.");
        }
    }

    @FXML
    private void switchtoPrintOrderPage() {

    }
}

