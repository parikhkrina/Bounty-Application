package app.com.loyaltyapp.bounty.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.models.Contact;


public class DetailsActivity extends AppCompatActivity {

    public final static String ID = "ID";
    public Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mContact = Contact.getItem(getIntent().getIntExtra(ID, 0));
    }
}
