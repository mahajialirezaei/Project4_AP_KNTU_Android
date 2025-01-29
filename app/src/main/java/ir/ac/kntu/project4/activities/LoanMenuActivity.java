package ir.ac.kntu.project4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.User;

public class LoanMenuActivity extends AppCompatActivity {
    public static User user;
    Button buttonLoanRequest;
    Button buttonloanManagement;
    Button loanTodash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_menu);
        buttonLoanRequest = findViewById(R.id.buttonLoanRequest);
        buttonloanManagement = findViewById(R.id.buttonloanManagement);
        loanTodash = findViewById(R.id.loanTodash);
        buttonLoanRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanMenuActivity.this, LoanRequestActivity.class);
                LoanRequestActivity.user = user;
                startActivity(intent);
            }
        });
        buttonloanManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanMenuActivity.this, LoanManagementActivity.class);
                LoanManagementActivity.user = user;
                startActivity(intent);
            }
        });
        loanTodash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanMenuActivity.this, DashboardActivity.class);
                DashboardActivity.user = user;
                startActivity(intent);
            }
        });
    }

}
