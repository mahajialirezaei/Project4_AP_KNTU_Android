package ir.ac.kntu.project4;

import java.util.ArrayList;
import java.util.List;

public class TransferPol {
    private User userSource;
    private User userDest;
    private Transaction transaction;

    public TransferPol(User user, User userDest, Transaction transaction) {
        this.userSource = user;
        this.userDest = userDest;
        this.transaction = transaction;
    }

    public User getUserSource() {
        return userSource;
    }

    public void setUserSource(User userSource) {
        this.userSource = userSource;
    }

    public User getUserDest() {
        return userDest;
    }

    public void setUserDest(User userDest) {
        this.userDest = userDest;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
