package ir.ac.kntu.project4;

import android.widget.EditText;

import java.io.Serializable;

public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String iDocument;
    private String password;
    private String phoneNumber;
    private Role userRole;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getiDocument() {
        return iDocument;
    }

    public void setiDocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Person(String firstName, String lastName, String phoneNumber, String iDocument, String password, Role userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.iDocument = iDocument;
        this.password = password;
    }
}
