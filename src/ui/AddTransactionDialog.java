package ui;

import data.DataStore;
import models.Transaction;

import javax.swing.*;
import java.util.Date;

public class AddTransactionDialog extends JDialog {
    public AddTransactionDialog(FinanceDashboard parent) {
        this(parent, -1);
    }

    public AddTransactionDialog(FinanceDashboard parent, int editIndex) {
        setTitle(editIndex == -1 ? "Add Transaction" : "Edit Transaction");
        setSize(300, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        String[] types = {"Income", "Expense"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();
        JButton saveButton = new JButton(editIndex == -1 ? "Add" : "Save");

        add(new JLabel("Type:"));
        add(typeBox);
        add(new JLabel("Category:"));
        add(categoryField);
        add(new JLabel("Amount:"));
        add(amountField);
        add(saveButton);

        saveButton.addActionListener(e -> {
            String type = (String) typeBox.getSelectedItem();
            String category = categoryField.getText();
            double amount = Double.parseDouble(amountField.getText());

            if (editIndex == -1) {
                DataStore.addTransaction(new Transaction(type, category, amount, new Date()));
            } else {
                DataStore.transactions.set(editIndex, new Transaction(type, category, amount, new Date()));
                DataStore.addTransaction(DataStore.transactions.get(editIndex));
            }
            parent.updateTransactionList(null);
            dispose();
        });

        setVisible(true);
    }
}