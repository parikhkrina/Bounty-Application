package app.com.loyaltyapp.bounty.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.adapters.DataManager;
import app.com.loyaltyapp.bounty.models.Contact;
import app.com.loyaltyapp.bounty.utils.RecyclerListener;


public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv); // layout reference

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true); // to improve performance

        rv.setAdapter(new DataManager()); // the data manager is assigner to the RV
        rv.addOnItemTouchListener( // and the click is handled
                new RecyclerListener(this, new RecyclerListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // STUB:
                        Intent intent = new Intent(ContactActivity.this, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.ID, Contact.CONTACTS[position].getId());
                        startActivity(intent);
                    }
                }));
    }
}
