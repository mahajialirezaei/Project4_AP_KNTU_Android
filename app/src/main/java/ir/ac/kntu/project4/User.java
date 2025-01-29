package ir.ac.kntu.project4;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.ac.kntu.project4.activities.SignUpActivity;

public class User extends Person implements Serializable {
    private Account account = new Account();
    private List<Contact> userContacts = new ArrayList<>();
    private List<SupportRequest> supReqUser = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public User(String firstName, String lastName, String phoneNumber, String iDocument, String password, Role userRole) {
        super(firstName, lastName, phoneNumber, iDocument, password, userRole);
        account = new Account();
        getAccount().setiDocument("IR" + getPhoneNumber().substring(1));
        userContacts = new ArrayList<>();
        supReqUser = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public User(EditText[] fields) {
        super(fields[0].getText().toString(), fields[1].getText().toString(), fields[2].getText().toString(), fields[3].getText().toString(), fields[4].getText().toString(), Role.USER);
        account = new Account();
        getAccount().setiDocument("IR" + getPhoneNumber().substring(1));
        userContacts = new ArrayList<>();
        supReqUser = new ArrayList<>();
        loans = new ArrayList<>();
    }

    public static boolean check(EditText[] fields, SignUpActivity signUpActivity) {
        if (TextUtils.isEmpty(fields[0].getText().toString()) || TextUtils.isEmpty(fields[1].getText().toString()) || TextUtils.isEmpty(fields[2].getText().toString()) || TextUtils.isEmpty(fields[3].getText().toString()) || TextUtils.isEmpty(fields[4].getText().toString())) {
            Toast.makeText(signUpActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.PHONE.matcher(fields[2].getText().toString()).matches()) {
            Toast.makeText(signUpActivity, "Invalid phone number", Toast.LENGTH_SHORT).show();
        } else if (fields[3].getText().toString().length() != 10) {
            Toast.makeText(signUpActivity, "National code must be 10 digits", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(fields[4].getText().toString())) {
            Toast.makeText(signUpActivity, "Password must contain uppercase, lowercase, digit, and special character", Toast.LENGTH_SHORT).show();
        } else if (repetious(fields)) {
            Toast.makeText(signUpActivity, "Phone Number or IDocument is Repetitious", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    private static boolean repetious(EditText[] fields) {
        for (User user : Bank.getUsers().values()) {
            if (fields[2].getText().toString().equals(user.getPhoneNumber()) || fields[3].getText().toString().equals(user.getiDocument())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidPassword(String password) {
        if (!(password.matches(".*[A-Z]+.*") && password.matches(".*[a-z]+.*") && password.matches(".*[0-9]+.*")
                && password.matches(".*[!|@|#|$|%|^|&|*|(|)|_|+|-|?|\\|/]+.*"))) {
            return false;
        }
        return true;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Contact> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(List<Contact> userContacts) {
        this.userContacts = userContacts;
    }

    public List<SupportRequest> getSupReqUser() {
        return supReqUser;
    }

    public List<Loan> getLoans() {
        return this.loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public void setSupReqUser(List<SupportRequest> supReqUser) {
        this.supReqUser = supReqUser;
    }

    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }
}
