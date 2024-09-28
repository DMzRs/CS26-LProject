package icedcoffee.coldbrewco;

import Database.*;
import ForEnkeepingLoginId.LoginId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ControllerOrderDetailsPage {

    @FXML
    private Label removeItemButton;

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
    private void onCheckOutButtonClick() throws IOException {
        DatabaseShow show = new DatabaseShow();
        DatabaseInsert insert = new DatabaseInsert();
        DatabaseUpdate update = new DatabaseUpdate();

        // Get all items from the TableView
        ObservableList<OrderItem> allItems = orderCoffeeTable.getItems();
        int totalPrice = 0;

        // Create a StringBuilder to collect the names
        StringBuilder coffeeNames = new StringBuilder("All Selected Coffee:\n");

        // Iterate through the items to retrieve coffee names and calculate total price
        for (OrderItem item : allItems) {
            coffeeNames.append(item.getName())
                    .append(" (Quantity: ").append(item.getQuantity())
                    .append(")\n");
            totalPrice += item.getSubTotal();
        }

        // Show confirmation dialog
        int confirmation = JOptionPane.showConfirmDialog(
                null,
                coffeeNames.toString() + "Total Price: " + totalPrice,
                "Confirm Your Order",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        // Handle the user's response
        if (confirmation == JOptionPane.YES_OPTION) {
            // Proceed with the checkout
            ObservableList<OrderItem> receiptItems = FXCollections.observableArrayList(allItems);

            String moneyReceivedStr = JOptionPane.showInputDialog(null, "Enter Money Received:", "Money Received", JOptionPane.QUESTION_MESSAGE);


            if (moneyReceivedStr != null) {
                try {
                    int moneyReceived = Integer.parseInt(moneyReceivedStr);

                    if (moneyReceived >= totalPrice) {
                        for (OrderItem itemToRemove : allItems) {
                            String coffeeName = itemToRemove.getName();
                            int itemQuantity = itemToRemove.getQuantity();
                            int subTotal = itemToRemove.getSubTotal();
                            update.productBought(show.showProductId(coffeeName), itemQuantity);
                            insert.newOrderUser(LoginId.getLoginId(), show.showProductId(coffeeName), itemQuantity, subTotal);
                        }
                        update.updateEmployeeSales(LoginId.getLoginId(), totalPrice);
                        allItems.clear();


                        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("ReceiptPage.fxml"));
                        Scene receiptPage = new Scene(fxmlLoader.load(), 450, 600);
                        ControllerReceiptPage controllerReceiptPage = fxmlLoader.getController(); // Get the controller
                        controllerReceiptPage.setOrderItems(receiptItems, moneyReceived);


                        Stage currentStage = (Stage) removeItemButton.getScene().getWindow();
                        currentStage.setScene(receiptPage);
                        currentStage.setTitle("Receipt Page");
                        currentStage.centerOnScreen();
                        currentStage.show();
                    } else {
                        JOptionPane.showMessageDialog(null, "Received amount is less than the total price!");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for the money received!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Transaction was canceled.");
            }
        } else {
            System.out.println("Order Canceled");
        }
    }



}

