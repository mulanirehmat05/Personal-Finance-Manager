package ui;

import data.DataStore;
import models.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FinanceDashboard extends JFrame {
    private DefaultListModel<String> listModel;
    private JLabel balanceLabel;
    private JComboBox<String> categoryFilter;
    private JList<String> transactionList;
    private boolean sortNewestFirst = true;

    public FinanceDashboard() {
        setTitle("Personal Finance Manager");
        setSize(500, 400);
        setLayout(new BorderLayout());

        balanceLabel = new JLabel("Balance: ₹" + DataStore.getTotalBalance(), SwingConstants.CENTER);
        add(balanceLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        transactionList = new JList<>(listModel);
        updateTransactionList(null);
        add(new JScrollPane(transactionList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Transaction");
        JButton deleteButton = new JButton("Delete Selected");
        JButton editButton = new JButton("Edit Selected");
        JButton sortButton = new JButton("Sort by Date");

        categoryFilter = new JComboBox<>(new String[]{"All", "Food", "Rent", "Shopping"});
        categoryFilter.addActionListener(e -> updateTransactionList((String) categoryFilter.getSelectedItem()));

        addButton.addActionListener(e -> {
            new AddTransactionDialog(this);
            updateTransactionList((String) categoryFilter.getSelectedItem());
            updateBalance(); // Balance update after add
        });

        deleteButton.addActionListener(e -> {
            int selectedIndex = transactionList.getSelectedIndex();
            if (selectedIndex != -1) {
                DataStore.deleteTransaction(selectedIndex);
                updateTransactionList((String) categoryFilter.getSelectedItem());
                updateBalance(); // Balance update after delete
            }
        });

        editButton.addActionListener(e -> {
            int selectedIndex = transactionList.getSelectedIndex();
            if (selectedIndex != -1) {
                new AddTransactionDialog(this, selectedIndex);
                updateTransactionList((String) categoryFilter.getSelectedItem());
                updateBalance(); // Balance update after edit
            }
        });

        sortButton.addActionListener(e -> {
            sortNewestFirst = !sortNewestFirst;
            DataStore.sortByDate();
            updateTransactionList((String) categoryFilter.getSelectedItem());
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(categoryFilter);
        buttonPanel.add(sortButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateTransactionList(String category) {
        listModel.clear();
        List<Transaction> filteredTransactions = (category == null || category.equals("All")) ?
                DataStore.transactions : DataStore.filterByCategory(category);

        for (Transaction t : filteredTransactions) {
            listModel.addElement(t.getType() + ": " + t.getCategory() + " - ₹" + t.getAmount() + " (" + t.getDate() + ")");
        }
        updateBalance(); // Balance update after filtering
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: ₹" + DataStore.getTotalBalance());
    }
}