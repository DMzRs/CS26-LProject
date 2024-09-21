package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderItemStorage {

    private static final OrderItemStorage instance = new OrderItemStorage();
    private ObservableList<OrderItem> selectedItems = FXCollections.observableArrayList();

    // Private constructor to enforce singleton
    private OrderItemStorage() {}

    public static OrderItemStorage getInstance() {
        return instance;
    }

    public ObservableList<OrderItem> getSelectedItems() {
        return selectedItems;
    }

    public void addItem(OrderItem item) {
        selectedItems.add(item);
    }
}

