package ir.ac.kntu.project4.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.ac.kntu.project4.Contact;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.SupportRequest;
import ir.ac.kntu.project4.Transaction;
import ir.ac.kntu.project4.User;

public class SupportRequestsActivity extends AppCompatActivity {
    Button buttonAddSupReq, buttonShowRequests, buttonAccountManagement;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportrequest);
        buttonAddSupReq = findViewById(R.id.buttonAddSupportRequests);
        buttonShowRequests = findViewById(R.id.buttonShowRequests);
        buttonAccountManagement = findViewById(R.id.buttonaccountmanagement);
        buttonAddSupReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest();
            }
        });
        buttonShowRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportRequestsActivity.this, ShowRequestActivity.class);
                ShowRequestActivity.user = user;
                startActivity(intent);
            }
        });
        buttonAccountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportRequestsActivity.this, ManageAccountActivity.class);
                ManageAccountActivity.user = user;
                startActivity(intent);
            }
        });
    }

    private void addRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Request");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_addrequest, null);
        final EditText ask = dialogView.findViewById(R.id.requestask);
        builder.setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get new values from edit text views
                        String askText = ask.getText().toString().trim();
                        if (askText.isEmpty()) {
                            Toast.makeText(SupportRequestsActivity.this, "Please fill Text", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SupportRequest supportRequest = new SupportRequest(askText);
                        user.getSupReqUser().add(supportRequest);
                        ask.setText("");
                        Toast.makeText(SupportRequestsActivity.this, "Request added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.show();
    }
}
