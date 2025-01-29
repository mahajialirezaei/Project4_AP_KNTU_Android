package ir.ac.kntu.project4.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ir.ac.kntu.project4.*;

public class MyThread extends Thread {
    double bonusPercent = 0.05;

    @Override
    public void run() {
        while (true) {
            try {
                depositBonus();
                acceptLoans();
                answerRequest();
                doPolTransfers();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doPolTransfers() {
        for (TransferPol transferPol : Bank.getTransferPol()) {
            User source = transferPol.getUserSource();
            User dest = transferPol.getUserDest();
            source.getAccount().setBalance(source.getAccount().getBalance() - transferPol.getTransaction().getAmount());
            dest.getAccount().setBalance(dest.getAccount().getBalance() + transferPol.getTransaction().getAmount());
            source.getAccount().getTransactions().add(transferPol.getTransaction());
            dest.getAccount().getTransactions().add(transferPol.getTransaction());
            Bank.getTransferPol().remove(transferPol);
        }
    }

    public void answerRequest() {
        try {
            for (User user : Bank.getUsers().values()) {
                for (SupportRequest request : user.getSupReqUser()) {
                    try {
                        if (request.getAnswer().isEmpty()) {
                            request.setAnswer("Our colleagues will contact you soon.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void acceptLoans() {
        try {
            List<User> users = new ArrayList<>(Bank.getUsers().values());
            for (User user : users) {
                if (checkNotPastInstallment(user)) {
                    for (Loan loan : user.getLoans()) {
                        try {
                            if (loan.getCondition().equals(LoanCondition.INPROGRESS)) {
                                loan.setCondition(LoanCondition.ACCEPTED);
                                user.getAccount().setBalance(user.getAccount().getBalance() + loan.getAmount());
                                user.getAccount().setAmountLoan(user.getAccount().getAmountLoan() + loan.getAmount());
                                loan.accept();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    for (Loan loan : user.getLoans()) {
                        if (loan.getCondition().equals(LoanCondition.INPROGRESS)) {
                            try {
                                loan.setCondition(LoanCondition.REJECTED);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkNotPastInstallment(User user) {
        for (Loan loan : user.getLoans()) {
            try {
                if (loan.getCondition().equals(LoanCondition.ACCEPTED)) {
                    for (Installment installment : loan.getInstallments()) {
                        Date now = new Date(Calendar.now());
                        if ((installment.getDateEnd().sub() < now.sub()) && installment.getRepaidCondition().equals(RepaidCondition.OVERDUE)) {
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void depositBonus() {
        List<User> users = new ArrayList<>(Bank.getUsers().values());
        for (User user : users) {
            for (BonusCapitalFunds bonusFund : user.getAccount().getBonusFunds()) {
                Date now = new Date(Calendar.now());
                try {
                    if (bonusFund.getEndDate().sub() < now.sub() && !bonusFund.isRemovable()) {
                        double bonus = (bonusFund.getSavingBalance() * bonusPercent * bonusFund.getMonth());
                        bonusFund.setSavingBalance(bonus + bonusFund.getSavingBalance());
                        bonusFund.setRemovable(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
