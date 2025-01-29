package ir.ac.kntu.project4;

public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String iDocument;

    public Contact(String name, String lastName, String phone) {
        setFirstName(name);
        setLastName(lastName);
        setPhoneNumber(phone);
        setiDocument("IR" + phone.substring(1));
    }

    public Contact() {

    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getiDocument() {
        return iDocument;
    }

    public void setiDocument(String iDocument) {
        this.iDocument = iDocument;
    }
}
