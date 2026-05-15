package data;

import models.Transaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHandler {
    private static final String FILE_NAME = "transactions.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // *Transactions ko file me save karna*
    public static void saveData(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Transaction t : transactions) {
                writer.write(t.getType() + "," + t.getCategory() + "," + t.getAmount() + "," + DATE_FORMAT.format(t.getDate()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // *Transactions ko file se load karna*
    public static List<Transaction> loadData() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return transactions; // *Agar file nahi hai, toh empty list return karein*

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    Date date = DATE_FORMAT.parse(parts[3]);

                    transactions.add(new Transaction(type, category, amount, date));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
}