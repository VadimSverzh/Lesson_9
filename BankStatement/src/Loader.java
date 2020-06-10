import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Loader {
    private static final Path TRANSACTIONS_PATH = Paths.get("data/movementList.csv");

    private static final String EXPENDITURE_SUM_STRING = "Сумма расходов: ";
    private static final String INCOME_SUM_STRING = "Сумма доходов: ";

    private static final String EXPENDITURE_HEADER = "Суммы расходов по организациям: ";
    private static final String INCOME_HEADER = "Суммы доходов по организациям: ";

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
        List <BankTransaction> transactions = readTransactions(TRANSACTIONS_PATH);

        BigDecimal expenditures = getExpendituresSum(transactions);
        BigDecimal income = getIncomeSum(transactions);
        printSum(EXPENDITURE_SUM_STRING, expenditures);
        printSum(INCOME_SUM_STRING, income);
        System.out.println();

        Map<String, BigDecimal> expendituresList = getExpendituresList(transactions);
        printItemDetailed(EXPENDITURE_HEADER, expendituresList);

    }

    private static List <BankTransaction> readTransactions (Path transactionsPath) {
        final int START_LINE = 2;

        List<BankTransaction> transactionsList = new ArrayList<>();
        List<String> operation;
        String[] transactionInfoArray;

        try {
            operation = Files.readAllLines(transactionsPath);

            for (int i = START_LINE - 1; i < operation.size(); i++) {
                String transactionLine = operation.get(i);
                String transactionLineWithoutQuotes = transactionLine
                        .replaceAll(CLEAN_TRANSACTION_LINE_FROM_QUOTES_REGEX, "$1.$2");
                transactionInfoArray = transactionLineWithoutQuotes.split(SPLIT_TRANSACTION_LINE_REGEX );

                if (transactionInfoArray.length != COLUMNS_AMOUNT_IN_TRANSACTIONS_LIST) {
                    System.out.println("Error!");
                }

                BigDecimal income = new BigDecimal(transactionInfoArray[INCOME_COLUMN_INDEX]);
                BigDecimal expenditure = new BigDecimal(transactionInfoArray[EXPENDITURE_COLUMN_INDEX]);
                String[] itemOfExpenditure = transactionInfoArray[ITEM_OF_EXPENDITURE_COLUMN_INDEX]
                        .split(SPLIT_ITEM_OF_EXPENDITURE_REGEX);
                String itemOfExpenditureCleaned = itemOfExpenditure[itemOfExpenditure.length - 1]
                        .split(SPLIT_ITEM_OF_EXPENDITURE_DETAILED_REGEX)[DETAILED_ITEM_OF_EXPENDITURE_INDEX].trim();
                transactionsList.add(new BankTransaction(income, expenditure, itemOfExpenditureCleaned));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionsList;
    }

    private static Map <String, BigDecimal> getExpendituresList(List<BankTransaction> transaction) {
        Map<String, BigDecimal> expenditures = new HashMap<>();

        final BigDecimal ZERO_EXPENDITURES = new BigDecimal("0.00");

        for (BankTransaction bankTransaction : transaction) {

            String itemOfExpenditure = bankTransaction.getItemOfExpenditure();
            BigDecimal expenditure = bankTransaction.getExpenditure();

            if (!(expenditure.equals(ZERO_EXPENDITURES))) {
                if (expenditures.containsKey(itemOfExpenditure)) {
                    expenditures.put(itemOfExpenditure, expenditures.get(itemOfExpenditure).add(expenditure));
                }
                else expenditures.put(itemOfExpenditure, expenditure);
            }
        }
        return expenditures;
    }

    private static BigDecimal getExpendituresSum(List<BankTransaction> transaction){
        return transaction.stream().map(BankTransaction::getExpenditure).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getIncomeSum(List<BankTransaction> transaction) {
        return transaction.stream().map(BankTransaction::getIncome).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void printSum(String item, BigDecimal sum) {
        System.out.printf("%-30s%,10.2f%s\n", item, sum, " руб.");
    }

    private static void printItemDetailed(String itemInfo, Map <String, BigDecimal> itemsList) {
        System.out.println(itemInfo);
        for (Map.Entry<String, BigDecimal> list : itemsList.entrySet()) {
            System.out.printf("%-30s%,10.2f%s\n", list.getKey(), list.getValue(), " руб.");
        }
    }

}
