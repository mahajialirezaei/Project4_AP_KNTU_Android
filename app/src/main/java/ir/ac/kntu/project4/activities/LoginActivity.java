package ir.ac.kntu.project4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.R;

public class LoginActivity extends AppCompatActivity {
    EditText phoneNumberEditText, passwordEditText;
    Button finalloginButton;
    TextView signUpLink;
    ImageButton backToRegular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        finalloginButton = findViewById(R.id.logInButton);
        signUpLink = findViewById(R.id.signUpLink);
        backToRegular = findViewById(R.id.logToReg);
        finalloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (phoneNumber.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both phone number and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = checkLogin(phoneNumber, password);
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    DashboardActivity.user = user;
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Phone number or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        backToRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegularUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private User checkLogin(String phoneNumber, String password) {
        for (User user : Bank.getUsers().values()) {
            if (user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
