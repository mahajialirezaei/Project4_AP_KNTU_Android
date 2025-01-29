package ir.ac.kntu.project4.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.ac.kntu.project4.Date;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.Transaction;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.util.Calendar;
import ir.ac.kntu.project4.util.TransactionType;


public class ChargeAccountActivity extends AppCompatActivity {
    static User user;
    EditText amount;
    Button buttonChargeAccount;
    private TextView balanceCharge;
    private ImageButton chargeToDashbutton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_account);
        balanceCharge = findViewById(R.id.BalanceCharge);
        if (user == null) {
            user = new User("1", "2", "3", "4", "5", Role.USER);
            user.getAccount().setBalance(1000);
        }
        amount = findViewById(R.id.amountCharge);
        balanceCharge.setText("Balance: " + user.getAccount().getBalance());
        chargeToDashbutton = findViewById(R.id.chargeToDash);
        chargeToDashbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChargeAccountActivity.this, DashboardActivity.class);
                DashboardActivity.user = user;
                startActivity(intent);
            }
        });
        buttonChargeAccount = findViewById(R.id.buttonChargeAccount);
        buttonChargeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().isEmpty()) {
                    Toast.makeText(ChargeAccountActivity.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                double amountValue = Double.parseDouble(amount.getText().toString());
                user.getAccount().setBalance(user.getAccount().getBalance() + amountValue);
                balanceCharge.setText("Balance: " + user.getAccount().getBalance());
                Toast.makeText(ChargeAccountActivity.this, "Charge Successful", Toast.LENGTH_SHORT).show();
                user.getAccount().getTransactions().add(new Transaction(amountValue, user.getAccount().getiDocument(), user.getAccount().getiDocument(), TransactionType.CHARGE, new Date(Calendar.now())));
                DashboardActivity.user = user;
                amount.setText("");
            }
        });
    }

}
