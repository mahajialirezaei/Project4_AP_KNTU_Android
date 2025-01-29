package ir.ac.kntu.project4.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.stream.Collectors;

import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.SupportRequest;
import ir.ac.kntu.project4.User;

public class ShowRequestActivity extends AppCompatActivity {
    static User user;
    ImageButton backToRequestMenu;
    ListView listView;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrequests);
        if (user == null) {
            user = new User("1", "2", "3", "4", "5", Role.USER);
            user.getSupReqUser().add(new SupportRequest("ask", "answer"));
            user.getSupReqUser().add(new SupportRequest("ask", "answer"));
            user.getSupReqUser().add(new SupportRequest("ask", "answer"));
            user.getSupReqUser().add(new SupportRequest("ask", "answer"));
            user.getSupReqUser().add(new SupportRequest("ask", "answer"));
        }
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.supreq_item, R.id.title, user.getSupReqUser().stream().map(a -> a.getRequestText()).collect(Collectors.toList()));
        listView.setAdapter(adapter);
        backToRequestMenu = findViewById(R.id.backToRequestMenu);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showRequest(position);
            }
        });
        backToRequestMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowRequestActivity.this, SupportRequestsActivity.class);
                SupportRequestsActivity.user = user;
                startActivity(intent);
            }
        });
    }

    private void showRequest(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("show Request");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_showreq_items, null);
        final TextView ask = dialogView.findViewById(R.id.ask);
        final TextView answer = dialogView.findViewById(R.id.answer);
        ask.setText("ask : " + user.getSupReqUser().get(position).getRequestText());
        answer.setText("answer : " + user.getSupReqUser().get(position).getAnswer());
        builder.setView(dialogView).show();
    }
}
