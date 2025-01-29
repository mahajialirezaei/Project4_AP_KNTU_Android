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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.stream.Collectors;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.Contact;
import ir.ac.kntu.project4.Loan;
import ir.ac.kntu.project4.LoanCondition;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.User;

public class LoanRequestActivity extends AppCompatActivity {
    public static User user;
    Button buttonLoanApplicationForm;
    Button LoanRequestMenubutton;
    ListView listView;

    @Override
    public synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_request);
        buttonLoanApplicationForm = findViewById(R.id.buttonLoanApplicationForm);
        LoanRequestMenubutton = findViewById(R.id.LoanRequestMenubutton);
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.loan_item, R.id.title, user.getLoans().stream().map(a -> "condition : " + a.getCondition().toString() + "\ntype : " + a.getType() + "\nmonth : " + a.getMonth() + "\namount : " + a.getAmount()).collect(Collectors.toList()));
        listView.setAdapter(adapter);
        buttonLoanApplicationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLoanRequest();
            }
        });
        LoanRequestMenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanRequestActivity.this, LoanMenuActivity.class);
                LoanMenuActivity.user = user;
                startActivity(intent);
            }
        });
    }

    public void addLoanRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Loan Request");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_loan, null);
        EditText amount = dialogView.findViewById(R.id.amountloan);
        EditText month = dialogView.findViewById(R.id.monthloan);
        EditText type = dialogView.findViewById(R.id.typeloan);
        EditText installmentNum = dialogView.findViewById(R.id.installmentNum);
        builder.setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (amount.getText().toString().isEmpty() || month.getText().toString().isEmpty() || type.getText().toString().isEmpty() || installmentNum.getText().toString().isEmpty()) {
                            Toast.makeText(LoanRequestActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double amountValue = Double.parseDouble(amount.getText().toString());
                        int monthValue = Integer.parseInt(month.getText().toString());
                        String typeValue = type.getText().toString();
                        int installmentNumValue = Integer.parseInt(installmentNum.getText().toString());
                        Loan loan = new Loan(amountValue, typeValue, monthValue, installmentNumValue);
                        Bank.getLoans().add(loan);
                        user.addLoan(loan);
                        Toast.makeText(LoanRequestActivity.this, "Loan added successfully", Toast.LENGTH_SHORT).show();
                        loan.setCondition(LoanCondition.INPROGRESS);
                        updateLoans();
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.show();
    }

    private void updateLoans() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.loan_item, R.id.title, user.getLoans().stream().map(a -> "condition : " + a.getCondition().toString() + "\ntype : " + a.getType() + "\nmonth : " + a.getMonth() + "\namount : " + a.getAmount()).collect(Collectors.toList()));
        listView.setAdapter(adapter);
    }

}
