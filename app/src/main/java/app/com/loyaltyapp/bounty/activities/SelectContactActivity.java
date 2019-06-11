package app.com.loyaltyapp.bounty.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.adapters.AllContactAdapters;
import app.com.loyaltyapp.bounty.interfaceListeners.onMemberClick;
import app.com.loyaltyapp.bounty.models.ContactList;
import app.com.loyaltyapp.bounty.models.UserDetails;

public class SelectContactActivity extends AppCompatActivity {


    Context mContext;

    ProgressBar progressBar;
    RecyclerView rv_contact;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private AllContactAdapters mAdapter;
    private LinearLayoutManager layoutManager;
    List<UserDetails> userList = new ArrayList();
    UserDetails userDetails;

    DatabaseReference databaseReference;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);


        mContext = this;
        rv_contact = (RecyclerView) findViewById(R.id.rvContacts);
        progressBar = (ProgressBar) findViewById(R.id.p_bar);

        progressBar.setBackgroundColor(getResources().getColor(R.color.white));


        // Read and show the contacts
        getUserFromApp();


    }

    private void getUserFromApp() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot threadSnap = dataSnapshot.child("Customers");

                Iterable<DataSnapshot> threadDetails = threadSnap.getChildren();
                for (DataSnapshot snap : threadDetails) {
                    //for (DataSnapshot locationItem : dataSnapshot.getChildren()) {

                    userDetails = snap.getValue(UserDetails.class);
                    userList.add(userDetails);
                    Log.d("list size-->", userList.size() + "");
                }

                mAdapter = new AllContactAdapters(userList, mContext);
                layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                rv_contact.setLayoutManager(layoutManager);
                rv_contact.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
