import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    final static Path bankStatement = Paths.get("data/movementList.csv");
    static List<String> operation = new ArrayList<>();
    static List<BankTransaction> transaction = new ArrayList<>();
    private static String[] transactionsInfo;

    public static void main(String[] args) {
        try {
            operation = Files.readAllLines(bankStatement);
            for (int i = 1; i < operation.size(); i++) {
                String noQuotes = operation.get(i).replaceAll("[\"]([\\d]+)[,]([\\d]+)[\"]", "$1.$2");
                transactionsInfo = noQuotes.split("[,]");

                if (transactionsInfo.length != 8) {
                    System.out.println("Error!");
                }

                Double income = Double.parseDouble(transactionsInfo[6]);
                Double expenditure = Double.parseDouble(transactionsInfo[7]);
                String itemOfExpenditure = transactionsInfo[5]
                        .replaceAll(".+\\b[\\\\/]([\\w]+([\\s]*([\\w]+)*)+)+[>]*\\s+[\\d\\D]+", "$1");

                transaction.add(new BankTransaction(income, expenditure, itemOfExpenditure));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (BankTransaction bank: transaction){
            System.out.println(bank.getItemOfExpenditure() + " " + bank.getIncome() + "\t" + bank.getExpenditure());
        }
    }
}
