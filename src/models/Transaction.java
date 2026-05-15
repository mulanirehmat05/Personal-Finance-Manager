package models;

import java.util.Date;

public class Transaction {
    private String type;      // Income ya Expense
    private String category;
    private double amount;
    private Date date;

    public Transaction(String type, String category, double amount, Date date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}