package ir.ac.kntu.project4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.util.MyThread;

public class MainActivity extends AppCompatActivity {
    public static String data = "";
    ProgressBar prgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prgressbar = findViewById(R.id.progressBar);
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
        prgressbar.setActivated(false);
        MyThread appManager = new MyThread();
        appManager.start();
//        User user = new User("mohammad", "haji", "09330426327", "0250332507", "Aa@1", Role.USER);
//        Bank.getUsers().put(user.getPhoneNumber(), user);
//        User user1 = new User("asghar", "asghari", "09011111111", "0211111111", "Aa@1", Role.USER);
//        Bank.getUsers().put(user1.getPhoneNumber(), user1);
        Button regularUserButton = findViewById(R.id.regularUserButton);
        regularUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegularUserActivity.class);
                startActivity(intent);
            }
        });

    }
}
