package ObservableTableOrganizers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderItemStorage {

    private static OrderItemStorage instance;
    private ObservableList<OrderItem> selectedItems;
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


    public ObservableList<OrderItem> getSelectedItems() {
        return selectedItems;
    }
    public void addItem(OrderItem item) {
        selectedItems.add(item);
    }
    public void clearItems() {
        selectedItems.clear();
    }
}
