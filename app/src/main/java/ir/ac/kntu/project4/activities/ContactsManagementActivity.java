package ir.ac.kntu.project4.activities;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import ir.ac.kntu.project4.Bank;
import ir.ac.kntu.project4.Contact;
import ir.ac.kntu.project4.Date;
import ir.ac.kntu.project4.R;
import ir.ac.kntu.project4.Role;
import ir.ac.kntu.project4.Transaction;
import ir.ac.kntu.project4.TransferPol;
import ir.ac.kntu.project4.User;
import ir.ac.kntu.project4.util.Calendar;
import ir.ac.kntu.project4.util.TransactionType;

public class ContactsManagementActivity extends AppCompatActivity {
    ListView listView;
    static User user;
    Button buttonAddContact;
    ArrayAdapter<String> adapter;
    Button buttonAccountManagement;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_management);
        buttonAddContact = findViewById(R.id.buttonAddContact);
        buttonAccountManagement = findViewById(R.id.buttonaccountmanagement);
        if (user == null) {
            user = new User("1", "2", "3", "4", "5", Role.USER);
            user.getUserContacts().add(new Contact("1", "2", "3"));
            Bank.getUsers().put(user.getPhoneNumber(), user);
        }
        makeAdapter();
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(adapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showContactOptions(position, adapter);
            }
        });

        buttonAccountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsManagementActivity.this, ManageAccountActivity.class);
                ManageAccountActivity.user = user;
                startActivity(intent);
            }
        });
    }

    private void makeAdapter() {
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, R.layout.contact_item, R.id.contactinformation, user.getUserContacts().stream().map(contact -> "name : " + contact.getFirstName() + " " + contact.getLastName() + "\n" + "phone number : " + contact.getPhoneNumber()).collect(Collectors.toList()));
        listView.setAdapter(adapter);
    }

    private void addContact(ArrayAdapter<String> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_contact, null);
        EditText editTextEditName = dialogView.findViewById(R.id.editTextEditName);
        EditText editTextEditLastName = dialogView.findViewById(R.id.editTextEditLastName);
        EditText editTextEditPhone = dialogView.findViewById(R.id.editTextEditPhone);
        builder.setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get new values from edit text views
                        String newName = editTextEditName.getText().toString().trim();
                        String newLastName = editTextEditLastName.getText().toString().trim();
                        String newPhone = editTextEditPhone.getText().toString().trim();
                        if (newName.isEmpty() || newLastName.isEmpty() || newPhone.isEmpty()) {
                            Toast.makeText(ContactsManagementActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Contact newContact = new Contact(newName, newLastName, newPhone);
                        user.getUserContacts().add(newContact);
                        adapter.notifyDataSetChanged();
                        editTextEditName.setText("");
                        editTextEditLastName.setText("");
                        editTextEditPhone.setText("");
                        Toast.makeText(ContactsManagementActivity.this, "Contact added successfully", Toast.LENGTH_SHORT).show();
                        makeAdapter();
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.show();
    }

    private void showContactOptions(int position, ArrayAdapter<String> adapter) {
        Contact selectedContact = user.getUserContacts().get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options")
                .setItems(new String[]{"Edit", "Delete", "Transfer"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                editContact(position, selectedContact, adapter);
                                break;
                            case 1:
                                deleteContact(position, adapter);
                                break;
                            case 2:
                                User userDest = TransferMoneyActivity.checkExistnumber(selectedContact.getPhoneNumber(), ContactsManagementActivity.this);
                                if (userDest != null) {
                                    enterAmount(userDest);
                                }
                                break;
                        }
                    }
                });
        builder.show();
        makeAdapter();
    }

    private void editContact(int position, Contact contact, ArrayAdapter<String> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Contact");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_contact, null);
        final EditText editTextEditName = dialogView.findViewById(R.id.editTextEditName);
        final EditText editTextEditLastName = dialogView.findViewById(R.id.editTextEditLastName);
        final EditText editTextEditPhone = dialogView.findViewById(R.id.editTextEditPhone);
        editTextEditName.setText(contact.getFirstName());
        editTextEditLastName.setText(contact.getLastName());
        editTextEditPhone.setText(contact.getPhoneNumber());
        builder.setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get new values from edit text views
                        String newName = editTextEditName.getText().toString().trim();
                        String newLastName = editTextEditLastName.getText().toString().trim();
                        String newPhone = editTextEditPhone.getText().toString().trim();
                        if (newName.isEmpty() || newLastName.isEmpty() || newPhone.isEmpty()) {
                            Toast.makeText(ContactsManagementActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                            return; // Exit the method if any field is empty
                        }
                        contact.setFirstName(newName);
                        contact.setLastName(newLastName);
                        contact.setPhoneNumber(newPhone);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ContactsManagementActivity.this, "Contact edited successfully", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setView(dialogView).setNegativeButton("Cancel", null);
        builder.show();
        makeAdapter();
        clearInputFields(editTextEditName, editTextEditLastName, editTextEditPhone);
    }

    public void deleteContact(int position, ArrayAdapter<String> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.getUserContacts().remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ContactsManagementActivity.this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null);
        makeAdapter();
        builder.show();
    }

    private void clearInputFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }

    public void enterAmount(User userDest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("enter amount");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_enteramount, null);
        EditText amount = dialogView.findViewById(R.id.amounttransfer);
        builder.setView(dialogView)
                .setPositiveButton("transfer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get new values from edit text views
                        String amountStr = amount.getText().toString().trim();
                        if (amountStr.isEmpty()) {
                            Toast.makeText(ContactsManagementActivity.this, "Please fill Text", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double amountDouble = Double.parseDouble(amountStr);
                        if (amountDouble <= 0) {
                            Toast.makeText(ContactsManagementActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                        } else if (amountDouble > user.getAccount().getBalance()) {
                            Toast.makeText(ContactsManagementActivity.this, "Balance is not Enough", Toast.LENGTH_SHORT).show();
                        } else {
                            acceptTransferMoney(amountDouble, userDest);
                        }
                    }
                }).setNegativeButton("Cancel", null);
        builder.show();
    }

    public void acceptTransferMoney(double amountDouble, User userDest) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("accept Transfer Money");
        final String[] trType = new String[1];
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_accept_transfer, null);
        RadioGroup radioGroup = dialogView.findViewById(R.id.transferType);
        builder.setView(dialogView).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton radioButton = dialogView.findViewById(selectedId);
                    trType[0] = radioButton.getText().toString();
                    if (trType[0].equals("simple")) {
                        user.getAccount().setBalance(user.getAccount().getBalance() - amountDouble);
                        userDest.getAccount().setBalance(userDest.getAccount().getBalance() + amountDouble);
                        Transaction transaction = new Transaction(amountDouble, user.getAccount().getiDocument(), userDest.getAccount().getiDocument(), TransactionType.TRANSFER_SIMPLE, new Date(Calendar.now()));
                        Toast.makeText(ContactsManagementActivity.this, "Transfer Successful", Toast.LENGTH_SHORT).show();
                        user.getAccount().getTransactions().add(transaction);
                        userDest.getAccount().getTransactions().add(transaction);
                    } else {
                        Transaction transaction = new Transaction(amountDouble, user.getAccount().getiDocument(), userDest.getAccount().getiDocument(), TransactionType.TRANSFER_PAYA, new Date(Calendar.now()));
                        TransferPol transferPol = new TransferPol(user, userDest, transaction);
                        Bank.getTransferPol().add(transferPol);
                        Toast.makeText(ContactsManagementActivity.this, "Paya Transfer request Successfully added", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ContactsManagementActivity.this, "Please select a transfer type", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", null);
        builder.show();
    }
}