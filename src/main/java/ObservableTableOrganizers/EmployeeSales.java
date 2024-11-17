package ObservableTableOrganizers;

public class EmployeeSales {
    private String EmpName;
    private int SoldQuantity;
    private String CoffeeName;
    private int SalesPerEmployee;
    private String date;
    public EmployeeSales(String EmpName, String CoffeeName, String date, int SoldQuantity, int SalesPerEmployee) {
        this.EmpName = EmpName;
        this.CoffeeName = CoffeeName;
        this.date = date;
        this.SoldQuantity = SoldQuantity;
        this.SalesPerEmployee = SalesPerEmployee;
    }
    public String getEmpName() {return EmpName;}
    public void setEmpName(String EmpName) {this.EmpName = EmpName;}
    public String getCoffeeName() {return CoffeeName;}
    public void setCoffeeName(String CoffeeName) {this.CoffeeName = CoffeeName;}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getSoldQuantity() {
        return SoldQuantity;
    }
    public void setSoldQuantity(int SoldQuantity) {
        this.SoldQuantity = SoldQuantity;
    }
    public int getSalesPerEmployee() {
        return SalesPerEmployee;
    }
    public void setSalesPerEmployee(int SalesPerEmployee) {
        this.SalesPerEmployee = SalesPerEmployee;
    }
}
