package ir.ac.kntu.project4;

import ir.ac.kntu.project4.util.RepaidCondition;

public class Installment {
    private double amount;
    private Date dateEnd;
    private int number;
    private RepaidCondition repaidCondition;

    public Installment(double eachAmount, Date date, int number) {
        this.amount = eachAmount;
        this.dateEnd = date;
        this.number = number;
        setRepaidCondition(RepaidCondition.OVERDUE);
    }

    public RepaidCondition getRepaidCondition() {
        return repaidCondition;
    }

    public void setRepaidCondition(RepaidCondition repaidCondition) {
        this.repaidCondition = repaidCondition;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
