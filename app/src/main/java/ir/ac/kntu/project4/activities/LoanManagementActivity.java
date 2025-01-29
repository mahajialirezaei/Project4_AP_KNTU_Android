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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ir.ac.kntu.project4.Contact;
import ir.ac.kntu.project4.Date;
import ir.ac.kntu.project4.Installment;
import ir.ac.kntu.project4.Loan;
import ir.ac.kntu.project4.util.Calendar;
import ir.ac.kntu.project4.LoanCondition;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Transaction;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.util.RepaidCondition;
import ir.ac.kntu.project4.util.TransactionType;

public class LoanManagementActivity extends AppCompatActivity {
    public static User user;
    Button LoanRequestMenubutton;
    List<Loan> loans = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loanmanagement);
        LoanRequestMenubutton = findViewById(R.id.LoanRequestMenubutton);
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.loan_item, R.id.title, user.getLoans().stream().filter(a -> a.getCondition().equals(LoanCondition.ACCEPTED) && !a.isRepaid()).map(a -> "condition : " + a.getCondition() + "\ntype : " + a.getType() + "\ndate start repay : " + a.getMonth() + "\namount : " + a.getAmount()).collect(Collectors.toList()));
        loans = user.getLoans().stream().filter(a -> a.getCondition().equals(LoanCondition.ACCEPTED) && !a.isRepaid()).collect(Collectors.toList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showLoanInstallments(position, adapter, loans);
            }
        });
        LoanRequestMenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanManagementActivity.this, LoanMenuActivity.class);
                LoanMenuActivity.user = user;
                startActivity(intent);
            }
        });
    }


    private void showLoanInstallments(int position, ArrayAdapter<String> adapter, List<Loan> loans) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("show installments");
        Loan loan = loans.get(position);
        List<Installment> installments = loan.getInstallments();
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_show_loans, null);
        ListView listView = dialogView.findViewById(R.id.listView);
        ArrayAdapter<String> adapterInstall = new ArrayAdapter<>(this, R.layout.loan_item, R.id.title, installments.stream().map(a -> "index : " + (a.getNumber() + 1) + "\namount : " + a.getAmount() + "\ndate should repay : " + a.getDateEnd() + "\nrepaid condition : " + a.getRepaidCondition()).collect(Collectors.toList()));
        listView.setAdapter(adapterInstall);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                repayInstallment(position, adapterInstall, installments);
            }
        });
        builder.setView(dialogView).show();
    }

    private void repayInstallment(int position, ArrayAdapter<String> adapter, List<Installment> installments) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("repay Loan");
        Installment installment = installments.get(position);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_repay_loan, null);
        TextView remainingInstallment = dialogView.findViewById(R.id.remainingInstallment);
        EditText amountrepay = dialogView.findViewById(R.id.amountrepay);
        remainingInstallment.setText(String.valueOf(installment.getAmount()));
        builder.setView(dialogView)
                .setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get new values from edit text views
                        if (amountrepay.getText().toString().isEmpty()) {
                            Toast.makeText(LoanManagementActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double amount = Double.parseDouble(amountrepay.getText().toString());
                        if (user.getAccount().getBalance() < amount) {
                            Toast.makeText(LoanManagementActivity.this, "Not enough money", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (amount > installment.getAmount()) {
                            Toast.makeText(LoanManagementActivity.this, "Amount to pay is more than the remaining amount", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (installment.getAmount() == 0) {
                            Toast.makeText(LoanManagementActivity.this, "this installments have been paid", Toast.LENGTH_SHORT).show();
                            installment.setRepaidCondition(RepaidCondition.REPAID);
                            return;
                        }
                        installment.setAmount(installment.getAmount() - amount);
                        user.getAccount().setBalance(user.getAccount().getBalance() - amount);
                        user.getAccount().getTransactions().add(new Transaction(amount, user.getAccount().getiDocument(), "installment", TransactionType.REPAY, new Date(Calendar.now())));
                        if (installment.getAmount() == 0) {
                            installment.setRepaidCondition(RepaidCondition.REPAID);
                            Toast.makeText(LoanManagementActivity.this, "Installment is fully paid", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(LoanManagementActivity.this, "successfully paid", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.setView(dialogView).setNegativeButton("Cancel", null);
        builder.show();

    }


}
