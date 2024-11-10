
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileTransactionSaver implements TransactionSaver {

    private final String filePath;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public FileTransactionSaver(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String record = dateFormat.format(transaction.getDate()) + "," + transaction.getType() + "," + transaction.getAmount() + "\n";
            writer.write(record);
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    @Override
    public void deleteTransaction(int id) {
        List<Transaction> transactions = loadTransactions();
        if (id >= 0 && id < transactions.size()) {
            transactions.remove(id);
            try (FileWriter writer = new FileWriter(filePath)) {
                for (Transaction transaction : transactions) {
                    String record = dateFormat.format(transaction.getDate()) + "," + transaction.getType() + "," + transaction.getAmount() + "\n";
                    writer.write(record);
                }
            } catch (IOException e) {
                System.out.println("Error deleting transaction: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid transaction ID.");
        }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Date date = dateFormat.parse(parts[0]);
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    transactions.add(new Transaction(date, type, amount));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }

}
