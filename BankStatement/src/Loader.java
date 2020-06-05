import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private static final Path TRANSACTIONS_PATH = Paths.get("data/movementList.csv");
    private static final List<BankTransaction> TRANSACTIONS_LIST = new ArrayList<>();

    private static final int COLUMNS_AMOUNT_IN_TRANSACTIONS_LIST = 8;
    private static final int INCOME_COLUMN_INDEX = 6;
    private static final int EXPENDITURE_COLUMN_INDEX = 7;
    private static final int ITEM_OF_EXPENDITURE_COLUMN_INDEX = 5;
    private static final int DETAILED_ITEM_OF_EXPENDITURE_INDEX = 0;

    private static final String CLEAN_TRANSACTION_LINE_FROM_QUOTES_REGEX = "[\"]([\\d]+)[,]([\\d]+)[\"]";
    private static final String SPLIT_TRANSACTION_LINE_REGEX = "[,]";
    private static final String SPLIT_ITEM_OF_EXPENDITURE_REGEX = "[\\\\/]";
    private static final String SPLIT_ITEM_OF_EXPENDITURE_DETAILED_REGEX = "\\s{2,}";

    public static void main(String[] args) {
        readTransactions(TRANSACTIONS_PATH,2, TRANSACTIONS_LIST);
        getExpendituresList(TRANSACTIONS_LIST);
        getExpendituresSum(TRANSACTIONS_LIST);
        getIncomeSum(TRANSACTIONS_LIST);
    }

    private static void readTransactions(Path transactionsPath, int startLine, List<BankTransaction> transactionsList) {
        List<String> operation;
        String[] transactionInfoArray;

        try {
            operation = Files.readAllLines(transactionsPath);

            for (int i = startLine - 1; i < operation.size(); i++) {
                String transactionLine = operation.get(i);
                String transactionLineWithoutQuotes = transactionLine
                        .replaceAll(CLEAN_TRANSACTION_LINE_FROM_QUOTES_REGEX, "$1.$2");
                transactionInfoArray = transactionLineWithoutQuotes.split(SPLIT_TRANSACTION_LINE_REGEX );

                if (transactionInfoArray.length != COLUMNS_AMOUNT_IN_TRANSACTIONS_LIST) {
                    System.out.println("Error!");
                }

                String income = transactionInfoArray[INCOME_COLUMN_INDEX];
                String expenditure = transactionInfoArray[EXPENDITURE_COLUMN_INDEX];
                String[] itemOfExpenditure = transactionInfoArray[ITEM_OF_EXPENDITURE_COLUMN_INDEX]
                        .split(SPLIT_ITEM_OF_EXPENDITURE_REGEX);
                String itemOfExpenditureCleaned = itemOfExpenditure[itemOfExpenditure.length - 1]
                        .split(SPLIT_ITEM_OF_EXPENDITURE_DETAILED_REGEX)[DETAILED_ITEM_OF_EXPENDITURE_INDEX].trim();
                transactionsList.add(new BankTransaction(income, expenditure, itemOfExpenditureCleaned));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getExpendituresList(List<BankTransaction> transaction) {
        System.out.printf("%-30s%10s\n\n", "Cтатья расходов", "Сумма, руб.");
        final String ZERO_EXPENDITURES_CHECK_REGEX = "0(\\.00)*";
        for (BankTransaction bankTransaction : transaction) {
            if (!(bankTransaction.getExpenditure().toString().matches(ZERO_EXPENDITURES_CHECK_REGEX))) {
                System.out.printf("%-30s%,10.2f\n", bankTransaction.getItemOfExpenditure(), bankTransaction.getExpenditure());
            }
        }
    }

    private static void getExpendituresSum(List<BankTransaction> transaction){
        BigDecimal expenditureSum = new BigDecimal("0.00");
        for (BankTransaction bank: transaction){
            expenditureSum = expenditureSum.add(bank.getExpenditure());
        }
        System.out.printf("%-30s%,10.2f\n", "ОБЩИЙ РАСХОД ", expenditureSum);
    }

    private static void getIncomeSum(List<BankTransaction> transaction) {
        BigDecimal incomeSum = new BigDecimal("0.00");
        for (BankTransaction bank : transaction) {
            incomeSum = incomeSum.add(bank.getIncome());
        }
        System.out.printf("%-30s%,10.2f\n", "ОБЩИЙ ПРИХОД ", incomeSum);
    }
}
