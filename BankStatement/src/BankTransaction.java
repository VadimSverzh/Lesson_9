import java.math.BigDecimal;

public class BankTransaction {
    private String income;
    private String expenditure;
    private String itemOfExpenditure;

    BankTransaction(String income, String expenditure, String itemOfExpenditure){
        this.income = income;
        this.expenditure = expenditure;
        this.itemOfExpenditure = itemOfExpenditure;
    }

    public BigDecimal getIncome() {
        return new BigDecimal(this.income);
    }

    public BigDecimal getExpenditure() {
        return new BigDecimal(this.expenditure);
    }

    public String getItemOfExpenditure() {
        return this.itemOfExpenditure;
    }
}
