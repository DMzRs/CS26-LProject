package Database;

public class EmployeeSales {
    private String EmpName;
    private int TotalCoffeeSold;
    private int TotalSalesPerEmployee;
    public EmployeeSales(String EmpName, int TotalCoffeeSold, int TotalSalesPerEmployee) {
        this.EmpName = EmpName;
        this.TotalCoffeeSold = TotalCoffeeSold;
        this.TotalSalesPerEmployee = TotalSalesPerEmployee;
    }
    public String getEmpName() {
        return EmpName;
    }
    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }
    public int getTotalCoffeeSold() {
        return TotalCoffeeSold;
    }
    public void setTotalCoffeeSold(int TotalCoffeeSold) {
        this.TotalCoffeeSold = TotalCoffeeSold;
    }
    public int getTotalSalesPerEmployee() {
        return TotalSalesPerEmployee;
    }
    public void setTotalSalesPerEmployee(int TotalSalesPerEmployee) {
        this.TotalSalesPerEmployee = TotalSalesPerEmployee;
    }
}
