public class BankTransaction {
    private Double income = 0.0;
    private Double expenditure = 0.0;
    private String itemOfExpenditure = "";

    BankTransaction(Double income, Double expenditure, String itemOfExpenditure){
        this.income = income;
        this.expenditure = expenditure;
        this.itemOfExpenditure = itemOfExpenditure;
    }

    public Double getIncome() {
        return this.income;
    }

    public Double getExpenditure() {
        return this.expenditure;
    }

    public String getItemOfExpenditure() {
        return this.itemOfExpenditure;
    }
}
