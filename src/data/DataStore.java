package data;

import models.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataStore {
    public static List<Transaction> transactions = new ArrayList<>();

    // *App start hone pe transactions load karein*
    static {
        transactions = FileHandler.loadData();
    }

    public static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        FileHandler.saveData(transactions); // *Save to file after adding*
    }

    public static void deleteTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
            FileHandler.saveData(transactions); // *Save to file after deleting*
        }
    }

    public static double getTotalBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income")) {
                balance += t.getAmount();
            } else {
                balance -= t.getAmount();
            }
        }
        return balance;
    }

    public static void sortByDate() {
        transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
        FileHandler.saveData(transactions); // *Save to file after sorting*
    }

    public static List<Transaction> filterByCategory(String category) {
        List<Transaction> filteredList = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(t);
            }
        }
        return filteredList;
    }
}