package ir.ac.kntu.project4.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.stream.Collectors;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.Date;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.SupportRequest;
import ir.ac.kntu.project4.Transaction;
import ir.ac.kntu.project4.TransferPol;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.util.Calendar;
import ir.ac.kntu.project4.util.TransactionType;

public class TransferMoneyActivity extends AppCompatActivity {
    static User user;
    Button buttonCustomTransfer;
    Button buttonContactTransfer;
    Button buttonLastTransacion;
    Button buttonTraToDash;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        if (user == null) {
            user = new User("mohammad", "haji", "09330426327", "0250332507", "Aa@1", Role.USER);
            Bank.getUsers().put(user.getPhoneNumber(), user);
        }
        buttonCustomTransfer = findViewById(R.id.buttonCustomTransfer);
        buttonContactTransfer = findViewById(R.id.buttonContactTransfer);
        buttonLastTransacion = findViewById(R.id.buttonLastTransacion);
        buttonTraToDash = findViewById(R.id.buttonTraToDash);
        buttonCustomTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterIdocument();
            }
        });
        buttonContactTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContacts();
            }
        });
        buttonLastTransacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLastTransactions();
            }
        });
        buttonTraToDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransferMoneyActivity.this, DashboardActivity.class);
                DashboardActivity.user = user;
                startActivity(intent);
            }
        });

    }

    public void showLastTransactions() {
        Intent intent = new Intent(TransferMoneyActivity.this, DashboardActivity.class);
        DashboardActivity.user = user;
        Toast.makeText(this, "choose from transactions", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void showContacts() {
        Intent intent = new Intent(TransferMoneyActivity.this, ContactsManagementActivity.class);
        ContactsManagementActivity.user = user;
        Toast.makeText(this, "choose from contacts", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void enterIdocument() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("enter id");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_enteridocument, null);
        EditText iDocument = dialogView.findViewById(R.id.idaccount);
        builder.setView(dialogView)
                .setPositiveButton("next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String iDocumentAc = iDocument.getText().toString().trim();
                        if (iDocumentAc.isEmpty()) {
                            Toast.makeText(TransferMoneyActivity.this, "Please fill Text", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        User userDest = checkExistId(iDocumentAc, TransferMoneyActivity.this);
                        if (userDest == null) {
                            Toast.makeText(TransferMoneyActivity.this, "Does not exist", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        enterAmount(userDest);
                    }
                })
                .setNegativeButton("Cancel", null);
        builder.show();
    }

    public void enterAmount(User userDest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("enter amount");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_enteramount, null);
        EditText amount = dialogView.findViewById(R.id.amounttransfer);
        builder.setView(dialogView)
                .setPositiveButton("transfer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get new values from edit text views
                        String amountStr = amount.getText().toString().trim();
                        if (amountStr.isEmpty()) {
                            Toast.makeText(TransferMoneyActivity.this, "Please fill Text", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double amountDouble = Double.parseDouble(amountStr);
                        if (amountDouble <= 0) {
                            Toast.makeText(TransferMoneyActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                        } else if (amountDouble > user.getAccount().getBalance()) {
                            Toast.makeText(TransferMoneyActivity.this, "Balance is not Enough", Toast.LENGTH_SHORT).show();
                        } else {
                            acceptTransferMoney(amountDouble, userDest);
                        }
                    }
                }).setNegativeButton("Cancel", null);
        builder.show();
    }

    public void acceptTransferMoney(double amountDouble, User userDest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("accept Transfer Money");
        final String[] trType = new String[1];
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_accept_transfer, null);
        RadioGroup radioGroup = dialogView.findViewById(R.id.transferType);
        builder.setView(dialogView).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton radioButton = dialogView.findViewById(selectedId);
                    trType[0] = radioButton.getText().toString();
                    if (trType[0].equals("simple")) {
                        user.getAccount().setBalance(user.getAccount().getBalance() - amountDouble);
                        userDest.getAccount().setBalance(userDest.getAccount().getBalance() + amountDouble);
                        Transaction transaction = new Transaction(amountDouble, user.getAccount().getiDocument(), userDest.getAccount().getiDocument(), TransactionType.TRANSFER_SIMPLE, new Date(Calendar.now()));
                        Toast.makeText(TransferMoneyActivity.this, "Transfer Successful", Toast.LENGTH_SHORT).show();
                        user.getAccount().getTransactions().add(transaction);
                        userDest.getAccount().getTransactions().add(transaction);
                    } else {
                        Transaction transaction = new Transaction(amountDouble, user.getAccount().getiDocument(), userDest.getAccount().getiDocument(), TransactionType.TRANSFER_PAYA, new Date(Calendar.now()));
                        TransferPol transferPol = new TransferPol(user, userDest, transaction);
                        Bank.getTransferPol().add(transferPol);
                        Toast.makeText(TransferMoneyActivity.this, "Paya Transfer request Successfully added", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TransferMoneyActivity.this, "Please select a transfer type", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", null);
        builder.show();
    }


    public static User checkExistId(String iDocumentAc, Context context) {
        for (User user : Bank.getUsers().values()) {
            if (user.getAccount().getiDocument().equals(iDocumentAc)) {
                return user;
            }
        }
        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
        return null;
    }

    public static User checkExistnumber(String phonenumber, Context context) {
        for (User user : Bank.getUsers().values()) {
            if (user.getPhoneNumber().equals(phonenumber)) {
                return user;
            }
        }
        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
        return null;
    }
}
