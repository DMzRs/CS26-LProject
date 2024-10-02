package Database;

public class EmployeeSales {
    private String EmpName;
    private int TotalCoffeeSold;
    private int SalesPerEmployee;
    private String date;
    public EmployeeSales(String EmpName,String date, int TotalCoffeeSold, int SalesPerEmployee) {
        this.EmpName = EmpName;
        this.date = date;
        this.TotalCoffeeSold = TotalCoffeeSold;
        this.SalesPerEmployee = SalesPerEmployee;
    }
    public String getEmpName() {
        return EmpName;
    }
    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
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
