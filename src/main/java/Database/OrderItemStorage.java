package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderItemStorage {

    private static OrderItemStorage instance;
    private ObservableList<OrderItem> selectedItems;

    // Private constructor to prevent instantiation
    private OrderItemStorage() {
        selectedItems = FXCollections.observableArrayList();
    }

    // Singleton instance method
    public static OrderItemStorage getInstance() {
        if (instance == null) {
            instance = new OrderItemStorage();
        }
        return instance;
    }

    // Method to get the selected items
    public ObservableList<OrderItem> getSelectedItems() {
        return selectedItems;
    }

    // Method to add an item to the list
    public void addItem(OrderItem item) {
        selectedItems.add(item);
    }

    // Method to clear all items from the list
    public void clearItems() {
        selectedItems.clear();
    }
}
