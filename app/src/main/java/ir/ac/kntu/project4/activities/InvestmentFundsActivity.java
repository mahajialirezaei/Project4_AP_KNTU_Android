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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ir.ac.kntu.project4.Date;
import ir.ac.kntu.project4.Transaction;
import ir.ac.kntu.project4.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.stream.Collectors;

import ir.ac.kntu.project4.BonusCapitalFunds;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.SavingsCapitalFunds;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.util.TransactionType;

public class InvestmentFundsActivity extends AppCompatActivity {
    ListView listView;
    static User user;
    EditText fundnameedittext, fundamount, fundmonth;
    String fundType;
    RadioGroup fundTypeRadioGroup;
    Button accept;
    ImageButton fundtodashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_fund);
        fundnameedittext = findViewById(R.id.fundnameedittext);
        fundamount = findViewById(R.id.fundamount);
        fundmonth = findViewById(R.id.fundmonth);
        fundTypeRadioGroup = findViewById(R.id.fundTypeRadioGroup);
        fundtodashboard = findViewById(R.id.fundtodashboard);
        listView = findViewById(R.id.listview);
        accept = findViewById(R.id.createFundButton);
        updateListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.fund_item, R.id.fundinfo, user.getAccount().getFunds().stream().map(a -> "name :" + a.getName() + "\namount : " + a.getSavingBalance() + "\nidocument: " + a.getiDocument() + "\nmonth : " + a.getMonth() + "\ntype:" + a.getSType()).collect(Collectors.toList()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFund(position, adapter);
            }
        });
        fundTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                fundType = radioButton.getText().toString();
            }
        });
        accept.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fundnameedittext.getText().toString().isEmpty() || fundamount.getText().toString().isEmpty() || fundmonth.getText().toString().isEmpty()) {
                    Toast.makeText(InvestmentFundsActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                double amount = Double.parseDouble(fundamount.getText().toString());
                if (amount < 0) {
                    Toast.makeText(InvestmentFundsActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                    return;
                } else if (amount > user.getAccount().getBalance()) {
                    Toast.makeText(InvestmentFundsActivity.this, "user Balance is not enough", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fundType.equals("bonusfund")) {
                    BonusCapitalFunds fund = new BonusCapitalFunds();
                    fund.setName(fundnameedittext.getText().toString());
                    fund.setSavingBalance(Double.parseDouble(fundamount.getText().toString()));
                    fund.setMonth(Integer.parseInt(fundmonth.getText().toString()));
                    fund.setSavingBalance(amount);
                    fund.setActive(true);
                    fund.setiDocument(user.getAccount().getiDocument() + Math.random() * 1000 + 17);
                    fund.date(Calendar.now());
                    fund.setType(1);
                    user.getAccount().setBalance(user.getAccount().getBalance() - amount);
                    user.getAccount().getBonusFunds().add(fund);
                } else if (fundType.equals("savingfund")) {
                    SavingsCapitalFunds fund = new SavingsCapitalFunds();
                    fund.setName(fundnameedittext.getText().toString());
                    fund.setSavingBalance(Double.parseDouble(fundamount.getText().toString()));
                    fund.setMonth(Integer.parseInt(fundmonth.getText().toString()));
                    fund.setSavingBalance(amount);
                    fund.setActive(true);
                    fund.setiDocument(user.getAccount().getiDocument() + Math.random() * 1001 + 17);
                    fund.date(Calendar.now());
                    fund.setType(0);
                    user.getAccount().setBalance(user.getAccount().getBalance() - amount);
                    user.getAccount().getSavingFunds().add(fund);
                }
                updateListView();
            }
        });
        fundtodashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvestmentFundsActivity.this, DashboardActivity.class);
                DashboardActivity.user = user;
                startActivity(intent);
            }
        });
        fundnameedittext.setText("");
        fundamount.setText("");
        fundmonth.setText("");
    }

    public void showFund(int position, ArrayAdapter<String> adapter) {
        SavingsCapitalFunds fund = user.getAccount().getFunds().get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fund Details")
                .setItems(new String[]{"Transactions", "deposit", "withdraw"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showTransactions(fund);
                                break;
                            case 1:
                                deposit(fund);
                                adapter.notifyDataSetChanged();
                                break;
                            case 2:
                                withdraw(fund);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
        builder.show();
    }

    private void withdraw(SavingsCapitalFunds fund) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("amount withdraw");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_withdraw, null);
        EditText amountWithdraw = dialogView.findViewById(R.id.amount);
        builder.setView(dialogView).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                double amountdouble = Double.parseDouble(amountWithdraw.getText().toString());
                if (amountdouble < 0) {
                    Toast.makeText(InvestmentFundsActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                    return;
                } else if (amountdouble > fund.getSavingBalance()) {
                    Toast.makeText(InvestmentFundsActivity.this, "balance fund is not enough", Toast.LENGTH_SHORT).show();
                    return;
                } else if (fund.getType() == 1) {
                    BonusCapitalFunds bonus = (BonusCapitalFunds) fund;
                    if (!bonus.isRemovable()) {
                        Toast.makeText(InvestmentFundsActivity.this, "You can't withdraw in bonus fund because it's not time to do it", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    fund.setSavingBalance(fund.getSavingBalance() - amountdouble);
                    Transaction tx = new Transaction(amountdouble, fund.getiDocument(), user.getAccount().getiDocument(), TransactionType.WITHDRAW, new Date(Calendar.now()));
                    user.getAccount().setBalance(user.getAccount().getBalance() + amountdouble);
                    fund.addTransaction(tx);
                    user.getAccount().getTransactions().add(tx);
                    Toast.makeText(InvestmentFundsActivity.this, "Withdrawn Successfully", Toast.LENGTH_SHORT).show();
                    updateListView();
                } else {
                    fund.setSavingBalance(fund.getSavingBalance() - amountdouble);
                    Transaction tx = new Transaction(amountdouble, fund.getiDocument(), user.getAccount().getiDocument(), TransactionType.WITHDRAW, new Date(Calendar.now()));
                    user.getAccount().setBalance(user.getAccount().getBalance() + amountdouble);
                    fund.addTransaction(tx);
                    user.getAccount().getTransactions().add(tx);
                    Toast.makeText(InvestmentFundsActivity.this, "Withdrawn Successfully", Toast.LENGTH_SHORT).show();
                    updateListView();
                }
            }
        }).setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.fund_item, R.id.fundinfo, user.getAccount().getFunds().stream().map(a -> "name :" + a.getName() + "\namount : " + a.getSavingBalance() + "\nidocument: " + a.getiDocument() + "\nmonth : " + a.getMonth() + "\ntype:" + a.getSType()).collect(Collectors.toList()));
        listView.setAdapter(adapter);
    }

    private void deposit(SavingsCapitalFunds fund) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("amount deposit");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_withdraw, null);
        EditText amountWithdraw = dialogView.findViewById(R.id.amount);
        builder.setView(dialogView).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                double amountdouble = Double.parseDouble(amountWithdraw.getText().toString());
                if (amountdouble < 0) {
                    Toast.makeText(InvestmentFundsActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                    return;
                } else if (amountdouble > user.getAccount().getBalance()) {
                    Toast.makeText(InvestmentFundsActivity.this, "balance user is not enough", Toast.LENGTH_SHORT).show();
                } else if (fund.getType() == 1) {
                    BonusCapitalFunds bonus = (BonusCapitalFunds) fund;
                    if (!bonus.isRemovable()) {
                        Toast.makeText(InvestmentFundsActivity.this, "You can't deposite in bonus fund because it's not time to do it", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    fund.setSavingBalance(fund.getSavingBalance() + amountdouble);
                    user.getAccount().setBalance(user.getAccount().getBalance() - amountdouble);
                    Transaction tx = new Transaction(amountdouble, user.getAccount().getiDocument(), fund.getiDocument(), TransactionType.DEPOSITE, new Date(Calendar.now()));
                    fund.addTransaction(tx);
                    user.getAccount().getTransactions().add(tx);
                    Toast.makeText(InvestmentFundsActivity.this, "deposit Successfully", Toast.LENGTH_SHORT).show();
                    updateListView();
                }
            }
        }).setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showTransactions(SavingsCapitalFunds fund) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_showlasttransacions, null);
        ListView listViewTrans = dialogView.findViewById(R.id.listView);
        builder.setView(dialogView);
        builder.setTitle("show transactions");
        ArrayAdapter<String> tranAdapter = new ArrayAdapter<>(this, R.layout.transaction_item, R.id.transactioinfo, fund.getTransactions().stream().map(a -> "date : " + a.getDate() + "\n" + "amount : " + a.getAmount() + "\n" + "to : " + a.getDestination() + "\nType :" + a.getTransactionType().toString()).collect(Collectors.toList()));
        listViewTrans.setAdapter(tranAdapter);
        builder.show();
    }
}