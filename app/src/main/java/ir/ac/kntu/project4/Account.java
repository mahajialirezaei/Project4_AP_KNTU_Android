package ir.ac.kntu.project4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    private String iDocument;
    private double balance;
    private double amountLoan;
    private List<Transaction> transactions = new ArrayList<>();
    private List<String> lastAcountNumber = new ArrayList<>();

    private List<SavingsCapitalFunds> savingFunds = new ArrayList<>();

    private List<BonusCapitalFunds> bonusFunds = new ArrayList<>();

    private ResidualCapitalFunds residualFunds = new ResidualCapitalFunds();

    public Account() {
        transactions = new ArrayList<>();
        lastAcountNumber = new ArrayList<>();
        savingFunds = new ArrayList<>();
        bonusFunds = new ArrayList<>();
        residualFunds = new ResidualCapitalFunds();
    }

    public Account(String iDocument, double balance, List<Transaction> transactions, List<String> lastAcountNumber, List<SavingsCapitalFunds> savingFunds, List<BonusCapitalFunds> bonusFunds, ResidualCapitalFunds residualFunds) {
        this.iDocument = iDocument;
        this.balance = balance;
        this.transactions = transactions;
        this.lastAcountNumber = lastAcountNumber;
        this.savingFunds = savingFunds;
        this.bonusFunds = bonusFunds;
        this.residualFunds = residualFunds;
    }

    public List<SavingsCapitalFunds> getFunds() {
        List<SavingsCapitalFunds> funds = new ArrayList<SavingsCapitalFunds>();
        funds.addAll(savingFunds);
        funds.addAll(bonusFunds);
        return funds;
    }

    public double getAmountLoan() {
        return amountLoan;
    }

    public synchronized void setAmountLoan(double amountLoan) {
        this.amountLoan = amountLoan;
    }

    public String getiDocument() {
        return iDocument;
    }

    public void setiDocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public double getBalance() {
        return balance;
    }

    public synchronized void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<String> getLastAcountNumber() {
        return lastAcountNumber;
    }

    public void setLastAcountNumber(List<String> lastAcountNumber) {
        this.lastAcountNumber = lastAcountNumber;
    }

    public List<SavingsCapitalFunds> getSavingFunds() {
        return savingFunds;
    }

    public void setSavingFunds(List<SavingsCapitalFunds> savingFunds) {
        this.savingFunds = savingFunds;
    }

    public List<BonusCapitalFunds> getBonusFunds() {
        return bonusFunds;
    }

    public void setBonusFunds(List<BonusCapitalFunds> bonusFunds) {
        this.bonusFunds = bonusFunds;
    }

    public ResidualCapitalFunds getResidualFunds() {
        return residualFunds;
    }

    public void setResidualFunds(ResidualCapitalFunds residualFunds) {
        this.residualFunds = residualFunds;
    }
}
