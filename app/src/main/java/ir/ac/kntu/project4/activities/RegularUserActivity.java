package ir.ac.kntu.project4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.User;

public class RegularUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);
        ImageButton regToMainMenuButton = findViewById(R.id.RegToMainMenu);
        Button regToSignupButton = findViewById(R.id.regToSignupButton);
        regToSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegularUserActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button regtoLoginButton = findViewById(R.id.regtoLoginButton);
        regtoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegularUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        regToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegularUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
