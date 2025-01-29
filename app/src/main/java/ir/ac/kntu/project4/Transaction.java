package ir.ac.kntu.project4;

import ir.ac.kntu.project4.util.TransactionType;

public class Transaction {
    private double amount;
    private String source = new String();
    private String destination = new String();
    private TransactionType transactionType;
    private Date date = new Date();

    public Transaction(double amount, String source, String destination, TransactionType transactionType, Date date) {
        this.amount = amount;
        this.source = source;
        this.destination = destination;
        this.transactionType = transactionType;
        this.date = date;
    }

    public Transaction() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
