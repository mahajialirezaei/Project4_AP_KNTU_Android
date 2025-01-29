package ir.ac.kntu.project4;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SavingsCapitalFunds {
    private double savingBalance;
    private boolean isActive = false;
    private String name;
    private String iDocument;
    private int type;
    int month;
    private List<Transaction> transactions = new ArrayList<>();
    private Date startDate;
    private Date endDate;

    public SavingsCapitalFunds(double savingBalance, String iDocument, String name) {
        this.savingBalance = savingBalance;
        this.iDocument = iDocument;
        this.name = name;
    }

    public SavingsCapitalFunds() {
        transactions = new ArrayList<>();
        Date startDate = new Date();
        Date endDate = new Date();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSavingBalance() {
        return savingBalance;
    }

    public void setSavingBalance(double savingBalance) {
        this.savingBalance = savingBalance;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getiDocument() {
        return iDocument;
    }

    public void setiDocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public SavingsCapitalFunds(double savingBalance, boolean isActive, String iDocument) {
        this.savingBalance = savingBalance;
        this.isActive = isActive;
        this.iDocument = iDocument;

    }


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void date(Instant now) {
        startDate = new Date(now);
        endDate = startDate.plusMonths(month);
    }

    public String getSType() {
        if (type == 0) {
            return "savingfund";
        }
        return "bonusfund";
    }
}
