package ObservableTableOrganizers;

public class PreviousOrders {
    private String productName;
    private int productPrice;
    private int productQuantity;
    private String date;
    private int subTotal;

    public PreviousOrders( String productName, int productPrice, int productQuantity, String date, int subTotal) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.date = date;
        this.subTotal = subTotal;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    public int getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(int orderQuantity) {
        this.productQuantity = orderQuantity;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }
}
