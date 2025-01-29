package ir.ac.kntu.project4;

public class BonusCapitalFunds extends SavingsCapitalFunds {
    private boolean removable = false;

    public BonusCapitalFunds(double savingBalance, boolean isActive, String iDocument) {
        super(savingBalance, isActive, iDocument);
        removable = false;
    }

    public BonusCapitalFunds() {
        super();
        setType(1);
        removable = false;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

}