


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PersonalEconomy {
    private final List<BasTransaction> transactions;
    private final String filePath = "transactions.txt";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public PersonalEconomy() {
        this.transactions = new ArrayList<>();
        loadTransactions(); 
    }

    public void addTransaction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        System.out.print("Enter type (Income/Expense): ");
        String type = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        try{
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        BasTransaction bTransaction;
        if(type.equalsIgnoreCase("Income")){
            bTransaction = new IncomeTransaction(date, amount);
        }else if(type.equalsIgnoreCase("Expense")){
            bTransaction = new ExpenseTransaction(date, amount);
        }else {System.out.println("invalid input. From the addTransaction method.");
            return;
        }

        transactions.add(bTransaction);
        saveTransaction(bTransaction);
        System.out.println("Income Transaction added.");
        
        
        }catch (Exception e){
            System.out.println("Invalid Transaction type, from add Transaction method.");
        }
    }

    public void deleteTransaction() {
        showAllTransactions();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter transaction index to delete: ");
        int index = scanner.nextInt();

        if (index >= 1 && index <= transactions.size()) {
            transactions.remove(index - 1);
            saveAllTransactions(); // Re-save all transactions after deletion
            System.out.println("Transaction deleted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    public double showAccountBalance() {
        return transactions.stream().mapToDouble(BasTransaction::getAmount).sum();
    }

    public void showAllTransactions() {
        for (int i = 0; i < transactions.size(); i++) {
            BasTransaction transaction = transactions.get(i);
            System.out.println((i + 1) + ". " + transaction.getDate() + " " + transaction.getType() + " " + transaction.getAmount());
        }
    }

    public void showAllDeposits() {
        transactions.stream()
            .filter(t -> t.getType().equalsIgnoreCase("Income"))
            .forEach(t -> System.out.println(t.getDate() + " " + t.getType() + " " + t.getAmount()));
    }

    public void showAllExpenses() {
        transactions.stream()
            .filter(t -> t.getType().equalsIgnoreCase("Expense"))
            .forEach(t -> System.out.println(t.getDate() + " " + t.getType() + " " + t.getAmount()));
    }

    // Method to load transactions from the file
    private void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Date date = dateFormat.parse(parts[0]);
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);
    
                    
                    BasTransaction transaction;
                    if (type.equalsIgnoreCase("Income")) {
                        transaction = new IncomeTransaction(date, amount);
                        
                    } else if (type.equalsIgnoreCase("Expense")) {
                        transaction = new ExpenseTransaction(date, amount);
                    } else {
                        System.out.println("Unknown transaction type: " + type);
                        continue;
                    }
    
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }
    

    // Method to save a single transaction
    private void saveTransaction(BasTransaction transaction) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String record = dateFormat.format(transaction.getDate()) + "," + transaction.getType() + "," + transaction.getAmount() + "\n";
            writer.write(record);
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    // Method to save all transactions (e.g., after deleting)
    private void saveAllTransactions() {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (BasTransaction transaction : transactions) {
                String record = dateFormat.format(transaction.getDate()) + "," + transaction.getType() + "," + transaction.getAmount() + "\n";
                writer.write(record);
            }
        } catch (IOException e) {
            System.out.println("Error saving all transactions: " + e.getMessage());
        }
    }
}

