package app.com.loyaltyapp.bounty.models;

import java.io.Serializable;

public class ContactList implements Serializable

{
    public ContactList() {

    }

    public ContactList(int contactId,
                       String contactImage,
                       String contactName,
                       String contactNumber) {
        this.contactId = contactId;
        ContactImage = contactImage;
        ContactName = contactName;
        ContactNumber = contactNumber;

    }

    public ContactList(String contactName, String contactNumber) {
        ContactName = contactName;
        ContactNumber = contactNumber;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    private int contactId;
    private String ContactImage;
    private String ContactName;
    private String ContactNumber;


    public String getContactImage() {
        return ContactImage;
    }

    public void setContactImage(String contactImage) {
        this.ContactImage = ContactImage;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName.trim();
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber.trim();
    }
}
