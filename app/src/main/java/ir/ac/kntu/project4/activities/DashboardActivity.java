package ir.ac.kntu.project4.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.stream.Collectors;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.Contact;
import ir.ac.kntu.project4.Date;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.Transaction;
import ir.ac.kntu.project4.TransferPol;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.util.Calendar;
import ir.ac.kntu.project4.util.TransactionType;

public class DashboardActivity extends AppCompatActivity {
    ListView listView;
    static User user;
    TextView textViewBalance;
    Button buttonRecharge, buttonManageAccount, buttonTransferMoney, buttonInvestmentFunds;
    ImageButton dashToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        textViewBalance = findViewById(R.id.textViewBalance);
        buttonRecharge = findViewById(R.id.buttonRecharge);
        buttonManageAccount = findViewById(R.id.buttonManageAccount);
        buttonTransferMoney = findViewById(R.id.buttonTransferMoney);
        buttonInvestmentFunds = findViewById(R.id.buttonInvestmentFunds);
        dashToLogIn = findViewById(R.id.dashToLogin);
        listView = findViewById(R.id.listView);
        if (user == null) {
            user = new User("1", "2", "3", "4", "5", Role.USER);
            user.getAccount().setBalance(1000);
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            user.getAccount().getTransactions().add(new Transaction(100.0, "1", "2", TransactionType.CHARGE, new Date(ir.ac.kntu.project4.util.Calendar.now())));
            Bank.getUsers().put(user.getPhoneNumber(), user);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.transaction_item, R.id.transactioinfo, user.getAccount().getTransactions().stream().map(transaction -> "date : " + transaction.getDate() + "\n" + "amount : " + transaction.getAmount() + "\n" + "to : " + transaction.getDestination() + "\nfrom : " + transaction.getSource() + "\nType :" + transaction.getTransactionType().toString()).collect(Collectors.toList()));
        listView.setAdapter(adapter);
        buttonRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ChargeAccountActivity.class);
                ChargeAccountActivity.user = user;
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTransactionOption(position, adapter);
            }
        });


        buttonManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ManageAccountActivity.class);
                ManageAccountActivity.user = user;
                startActivity(intent);
            }
        });

        buttonTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, TransferMoneyActivity.class);
                TransferMoneyActivity.user = user;
                startActivity(intent);
            }
        });
        buttonInvestmentFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, InvestmentFundsActivity.class);
                InvestmentFundsActivity.user = user;
                startActivity(intent);
            }
        });

        dashToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        textViewBalance.setText("Balance: " + user.getAccount().getBalance());
    }

    public void showTransactionOption(int position, ArrayAdapter<String> adapter) {
        Transaction selectedTransaction = user.getAccount().getTransactions().get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options")
                .setItems(new String[]{"Transfer", "Cancel"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                User userDest = TransferMoneyActivity.checkExistId(selectedTransaction.getDestination(), DashboardActivity.this);
                                enterAmount(userDest);
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                return;
                        }
                    }
                });
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
                            Toast.makeText(DashboardActivity.this, "Please fill Text", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double amountDouble = Double.parseDouble(amountStr);
                        if (amountDouble <= 0) {
                            Toast.makeText(DashboardActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                        } else if (amountDouble > user.getAccount().getBalance()) {
                            Toast.makeText(DashboardActivity.this, "Balance is not Enough", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DashboardActivity.this, "Transfer Successful", Toast.LENGTH_SHORT).show();
                        user.getAccount().getTransactions().add(transaction);
                        userDest.getAccount().getTransactions().add(transaction);
                    } else {
                        Transaction transaction = new Transaction(amountDouble, user.getAccount().getiDocument(), userDest.getAccount().getiDocument(), TransactionType.TRANSFER_PAYA, new Date(Calendar.now()));
                        TransferPol transferPol = new TransferPol(user, userDest, transaction);
                        Bank.getTransferPol().add(transferPol);
                        Toast.makeText(DashboardActivity.this, "Paya Transfer request Successfully added", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "Please select a transfer type", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateTransactions() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.transaction_item, R.id.transactioinfo, user.getAccount().getTransactions().stream().map(transaction -> "date : " + transaction.getDate() + "\n" + "amount : " + transaction.getAmount() + "\n" + "to : " + transaction.getDestination() + "\nType :" + transaction.getTransactionType().toString()).collect(Collectors.toList()));
        listView.setAdapter(adapter);
    }
}
