package app.com.loyaltyapp.bounty.activities;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.fragments.TransferFragment;
import app.com.loyaltyapp.bounty.interfaceListeners.onMemberClick;
import app.com.loyaltyapp.bounty.models.PointsHistory;
import app.com.loyaltyapp.bounty.models.UserDetails;

public class LentPoints extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    FirebaseDatabase mFirebaseDatabase;
    UserDetails currentUser = new UserDetails();
    EditText et_points, et_addContacts;
    UserDetails userDetails = new UserDetails();
    Button  btn_transfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lent_points);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String uid = mFirebaseUser.getUid();
        loadCurrentUser(uid);
        et_points = findViewById(R.id.et_points);
        et_addContacts = findViewById(R.id.et_contacts);
        btn_transfer = findViewById(R.id.btn_transfer);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            userDetails = (UserDetails) getIntent().getSerializableExtra("transfer_username");
            et_addContacts.setText(userDetails.getName());

        }

        et_addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LentPoints.this,SelectContactActivity.class);
                startActivity(i);
            }
        });


        btn_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int points = Integer.parseInt(userDetails.getPoints());
                points = points + Integer.parseInt(et_points.getText().toString());
                int currentUserPoints = Integer.parseInt(currentUser.getPoints());
                if(currentUserPoints !=0) {
                    currentUserPoints = currentUserPoints - Integer.parseInt(et_points.getText().toString());
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String formattedDate = df.format(c);
                    DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference().child("PointsHistory");
                    String key = mDatabaseReference.push().getKey();
                    mDatabaseReference.child(key).setValue(new PointsHistory(
                            currentUser.getCardNumber(),currentUser.getEmail(),et_points.getText().toString(),formattedDate,userDetails.getEmail()));
                    DatabaseReference mCustomerReference = mFirebaseDatabase.getReference();
                    mCustomerReference.child("Customers").child(mFirebaseUser.getUid()).child("points").setValue(currentUserPoints+"");
                    updateOtherUserPoints(userDetails,points);
                    et_points.setText("");
                    et_addContacts.setText("");
                    Toast.makeText(getApplicationContext(), "Points transferred", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Not enough points left!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void loadCurrentUser(final String uid){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot threadSnap = dataSnapshot.child("Customers");
                Iterable<DataSnapshot> threadDetails = threadSnap.getChildren();
                for (DataSnapshot snap : threadDetails) {

                    if(snap.getKey().equals(uid)) {
                        currentUser = snap.getValue(UserDetails.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateOtherUserPoints(final UserDetails otherUser, final int points){
        String uid;
        mFirebaseDatabase.getReference().child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> users = dataSnapshot.getChildren();
                for(DataSnapshot snap : users){
                    UserDetails user = snap.getValue(UserDetails.class);
                    if(user.getCardNumber().equals(otherUser.getCardNumber())){
                        DatabaseReference mCustomerReference = mFirebaseDatabase.getReference();
                        mCustomerReference.child("Customers").child(snap.getKey()).child("points").setValue(points+"");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}





