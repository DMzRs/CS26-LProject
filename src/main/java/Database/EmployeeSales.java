package Database;

public class EmployeeSales {
    private String EmpName;
    private int TotalCoffeeSold;
    private int SalesPerEmployee;
    public EmployeeSales(String EmpName, int TotalCoffeeSold, int SalesPerEmployee) {
        this.EmpName = EmpName;
        this.TotalCoffeeSold = TotalCoffeeSold;
        this.SalesPerEmployee = SalesPerEmployee;
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
    public int getSalesPerEmployee() {
        return SalesPerEmployee;
    }
    public void setSalesPerEmployee(int SalesPerEmployee) {
        this.SalesPerEmployee = SalesPerEmployee;
    }
}
