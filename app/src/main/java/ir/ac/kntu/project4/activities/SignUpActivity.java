package ir.ac.kntu.project4.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.R;

public class SignUpActivity extends AppCompatActivity {
    EditText firstNameEditText, lastNameEditText, phoneNumberEditText, nationalCodeEditText, passwordEditText;
    Button signUpButton;
    ImageButton backToRegular;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        nationalCodeEditText = findViewById(R.id.nationalCodeEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        backToRegular = findViewById(R.id.signUpToReg);
        progressBar = findViewById(R.id.progressBar);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText[] fields = {firstNameEditText, lastNameEditText, phoneNumberEditText, nationalCodeEditText, passwordEditText};
                if (User.check(fields, SignUpActivity.this)) {
                    Bank.getUsers().put(fields[3].getText().toString(), new User(fields));
                    Toast.makeText(SignUpActivity.this, "successfully signed up", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, RegularUserActivity.class);
                    startActivity(intent);
                }
            }

        });
        backToRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, RegularUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
