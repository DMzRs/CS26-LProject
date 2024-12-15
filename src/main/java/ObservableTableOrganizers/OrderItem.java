package ObservableTableOrganizers;

public class OrderItem {
    private String name;
    private int price;
    private int quantity;

    public OrderItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getSubTotal() {
        return price * quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
