
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        PersonalEconomy economy = new PersonalEconomy();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Transaction");
            System.out.println("2. Delete Transaction");
            System.out.println("3. Show Account Balance");
            System.out.println("4. Show All Transactions");
            System.out.println("5. Show All Deposits");
            System.out.println("6. Show All Expenses");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1 ->
                    economy.addTransaction();
                case 2 ->
                    economy.deleteTransaction();
                case 3 ->
                    System.out.println("Current balance: " + economy.showAccountBalance() + " kr");
                case 4 ->
                    economy.showAllTransactions();
                case 5 ->
                    economy.showAllDeposits();
                case 6 ->
                    economy.showAllExpenses();
                case 7 -> {
                    System.out.println("Thanks for using Personal Finance App!");
                    scanner.close();
                    System.exit(0);
                }
                default ->
                    System.out.println("Invalid Option, Try again.");
            }
        }
    }
}
