package ir.ac.kntu.project4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.User;


public class ManageAccountActivity extends AppCompatActivity {
    static User user;
    TextView name;
    TextView iDocument;
    Button buttonSupportRequests;
    Button buttonInvestmentFunds;
    Button buttonLoanRequest;
    Button buttonContactManagement;
    Button buttonManToDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        if (user == null) {
            user = new User("mohammad", "haji", "09330426327", "0250332507", "Aa@1", Role.USER);
        }
        buttonSupportRequests = findViewById(R.id.buttonSupportRequests);
        buttonInvestmentFunds = findViewById(R.id.buttonInvestmentFunds);
        buttonLoanRequest = findViewById(R.id.buttonLoanRequest);
        buttonManToDash = findViewById(R.id.buttonManToDash);
        buttonContactManagement = findViewById(R.id.buttonContactManagement);
        name = findViewById(R.id.accountNameButton);
        iDocument = findViewById(R.id.accountIDocument);
        name.setText(user.getFirstName() + " " + user.getLastName());
        iDocument.setText(user.getAccount().getiDocument());
        buttonSupportRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, SupportRequestsActivity.class);
                SupportRequestsActivity.user = user;
                startActivity(intent);
            }
        });
        buttonInvestmentFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, InvestmentFundsActivity.class);
                InvestmentFundsActivity.user = user;
                startActivity(intent);
            }
        });
        buttonContactManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, ContactsManagementActivity.class);
                ContactsManagementActivity.user = user;
                startActivity(intent);
            }
        });
        buttonLoanRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, LoanMenuActivity.class);
                LoanMenuActivity.user = user;
                LoanRequestActivity.user = user;
                LoanManagementActivity.user = user;
                startActivity(intent);
            }
        });
        buttonManToDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAccountActivity.this, DashboardActivity.class);
                DashboardActivity.user = user;
                startActivity(intent);
            }
        });

    }


}
