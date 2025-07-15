package ATMinterface;

import java.io.*;
import java.util.*;

public class ATMSystem {

    private static final String FILE_PATH = "accounts.csv";

    public static void main(String[] args) {
        ensureFileExists();
        Map<String, BankAccount> users = loadAccounts();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM System");
        System.out.println("1. Login");
        System.out.println("2. Create New Account");
        System.out.print("Choose an option (1 or 2): ");
        int option = scanner.nextInt();
        scanner.nextLine(); 

        BankAccount account = null;

        if (option == 1) {
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            account = users.get(username);
            if (account != null && account.getPin().equals(pin)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or PIN.");
                scanner.close();
                return;
            }

        } else if (option == 2) {
            
            System.out.print("Choose a username: ");
            String username = scanner.nextLine();
            if (users.containsKey(username)) {
                System.out.println("Username already exists.");
                scanner.close();
                return;
            }

            System.out.print("Choose a 4-digit PIN: ");
            String pin = scanner.nextLine();
            if (!pin.matches("\\d{4}")) {
                System.out.println("Invalid PIN format.");
                scanner.close();
                return;
            }

            System.out.print("Enter initial deposit: ₹");
            double initialDeposit = scanner.nextDouble();
            scanner.nextLine();

            account = new BankAccount(username, pin, initialDeposit);
            users.put(username, account);
            appendAccountToFile(account);
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Invalid choice.");
            scanner.close();
            return;
        }

        //START
        ATM atm = new ATM(account);
        atm.start();

        
        users.put(account.getUsername(), account);
        updateAllAccounts(users);

        scanner.close();
    }

    private static void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("username,pin,balance\n");
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }

    private static Map<String, BankAccount> loadAccounts() {
        Map<String, BankAccount> accounts = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String pin = parts[1].trim();
                    double balance = Double.parseDouble(parts[2].trim());
                    accounts.put(username, new BankAccount(username, pin, balance));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return accounts;
    }

    private static void appendAccountToFile(BankAccount account) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(account.getUsername() + "," + account.getPin() + "," + account.getBalance() + "\n");
        } catch (IOException e) {
            System.out.println("Error appending account: " + e.getMessage());
        }
    }

    private static void updateAllAccounts(Map<String, BankAccount> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("username,pin,balance\n");
            for (BankAccount acc : accounts.values()) {
                writer.write(acc.getUsername() + "," + acc.getPin() + "," + acc.getBalance() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating accounts: " + e.getMessage());
        }
    }
}
