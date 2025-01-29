package ir.ac.kntu.project4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private static Map<String, User> users = new HashMap<>();
    private static List<Loan> loans = new ArrayList<>();
    private static List<TransferPol> transferPol = new ArrayList<>();


    public static List<TransferPol> getTransferPol() {
        return transferPol;
    }

    public static void setTransferPol(List<TransferPol> transferPol) {
        Bank.transferPol = transferPol;
    }

    public static Map<String, User> getUsers() {
        return users;
    }

    public static List<Loan> getLoans() {
        return loans;
    }

    public static void setLoans(List<Loan> loans) {
        Bank.loans = loans;
    }

    public static void setUsers(Map<String, User> users) {
        Bank.users = users;
    }

}
