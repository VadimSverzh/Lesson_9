import java.math.BigDecimal;

public class BankTransaction {
    private final BigDecimal income;
    private final BigDecimal expenditure;
    private final String itemOfExpenditure;

    BankTransaction(BigDecimal income, BigDecimal expenditure, String itemOfExpenditure){
        this.income = income;
        this.expenditure = expenditure;
        this.itemOfExpenditure = itemOfExpenditure;
    }

    public BigDecimal getIncome() {
        return this.income;
    }

    public BigDecimal getExpenditure() {
        return this.expenditure;
    }

    public String getItemOfExpenditure() {
        return this.itemOfExpenditure;
    }
}
