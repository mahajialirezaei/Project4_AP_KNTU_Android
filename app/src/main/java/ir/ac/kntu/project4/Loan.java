package ir.ac.kntu.project4;

import ir.ac.kntu.project4.util.Calendar;

import java.util.ArrayList;
import java.util.List;


public class Loan {
    private double amount;
    private Date dateStart;
    private String type;
    private LoanCondition condition;
    private int month;
    private boolean isRepaid;
    private int installmentNum;
    private List<Installment> installments = new ArrayList<Installment>();

    public int getInstallmentNum() {
        return installmentNum;
    }

    public void setInstallmentNum(int installmentNum) {
        this.installmentNum = installmentNum;
    }

    public List<Installment> getInstallments() {
        return installments;
    }

    public void setInstallments(List<Installment> installments) {
        this.installments = installments;
    }

    public Loan(double amount, Date dateStart, String type, LoanCondition condition, int installmentNum) {
        this.amount = amount;
        this.dateStart = dateStart;
        this.type = type;
        this.condition = condition;
        this.installmentNum = installmentNum;
    }

    public Loan(double amount, String type, int month, int installmentNum) {
        this.amount = amount;
        this.type = type;
        this.month = month;
        this.condition = condition;
        this.installmentNum = installmentNum;
        this.installments = new ArrayList<>();
    }

    public void accept() {
        double eachAmount = amount / installmentNum;
        Date date = new Date(Calendar.now());
        setDateStart(date.plusMonths(month));
        for (int i = 0; i < installmentNum; i++) {
            Installment installment = new Installment(eachAmount, dateStart.plusMonths(i), i);
            installments.add(installment);
        }
    }


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isRepaid() {
        return isRepaid;
    }

    public void setRepaid(boolean repaid) {
        isRepaid = repaid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LoanCondition getCondition() {
        return condition;
    }

    public void setCondition(LoanCondition condition) {
        this.condition = condition;
    }
}
