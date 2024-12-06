package icedcoffee.coldbrewco;

import Main.Admin;
import Main.Employee;
import Main.Order;
import Main.Product;
import ObservableTableOrganizers.OrderItem;
import ObservableTableOrganizers.OrderItemStorage;
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


    private final ObservableList<OrderItem> selectedItems = FXCollections.observableArrayList();

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
        currentStage.setResizable(false);
        currentStage.show();
    }

    //to Remove Items
    @FXML
    private void onRemoveItemButtonClick() {
        Product product = new Product();
        Admin admin = new Admin();
        // Get the selected item
        OrderItem selectedItem = orderCoffeeTable.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedItem != null) {
            int selectedQuantity = selectedItem.getQuantity();

            ObservableList<OrderItem> items = orderCoffeeTable.getItems();
            admin.productAddedBackFromTemporaryRemovedItems(product.showProductId(selectedItem.getName()), selectedQuantity);
            items.remove(selectedItem);
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to remove");
        }
    }


    //to Check Out Orders
    @FXML
    private void onCheckOutButtonClick() throws IOException {
        Product product = new Product();
        Order order = new Order();
        Employee employee = new Employee();

        ObservableList<OrderItem> allItems = orderCoffeeTable.getItems();
        if (allItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select an item to check out");
        } else {
            int totalPrice = 0;

            StringBuilder coffeeNames = new StringBuilder("All Selected Coffee:\n");

            for (OrderItem item : allItems) {
                coffeeNames.append(item.getName())
                        .append(" (Quantity: ").append(item.getQuantity())
                        .append(")\n");
                totalPrice += item.getSubTotal();
            }

            int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    coffeeNames + "Total Price: " + totalPrice,
                    "Confirm Your Order",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
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
                                order.addOrder(Employee.getEmployeeId(), product.showProductId(coffeeName), itemQuantity, subTotal);
                            }
                            allItems.clear();

                            String costumerNameStr = JOptionPane.showInputDialog(null, "Customer Name", "Customer Name:", JOptionPane.QUESTION_MESSAGE);
                            FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("ReceiptPage.fxml"));
                            Scene receiptPage = new Scene(fxmlLoader.load(), 450, 600);

                            // Get the controller and pass data
                            ControllerReceiptPage controllerReceiptPage = fxmlLoader.getController();
                            controllerReceiptPage.setOrderItems(receiptItems, moneyReceived, costumerNameStr); // Pass data here


                            Stage currentStage = (Stage) removeItemButton.getScene().getWindow();
                            currentStage.setScene(receiptPage);
                            currentStage.setTitle("Receipt Page");
                            currentStage.setResizable(false);
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
                System.out.println("Canceled");
            }
        }
    }


}

