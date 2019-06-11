package app.com.loyaltyapp.bounty.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.activities.LentPoints;
import app.com.loyaltyapp.bounty.models.UserDetails;
import app.com.loyaltyapp.bounty.utils.ComicTextView;


public class TransferFragment extends Fragment {

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;


    Button btn_borrow , btn_lent;
    ComicTextView tv_userPoints;
    UserDetails userDetails;
    public TransferFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_transfer, container, false);

        init(view);
        return  view;
    }

    private void init(View view) {


        tv_userPoints = (ComicTextView)  view.findViewById(R.id.tv_points);
        btn_borrow = (Button) view.findViewById(R.id.tv_borrow);
        btn_lent = (Button) view.findViewById(R.id.tv_lent);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        String uid = mFirebaseAuth.getUid();
        //uname = userDetails.getName();
        loadData(uid);
       // tv_userPoints.setText(userDetails.getPoints());

        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String shareBody = "Wants to Borrow Points On Bounty";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Can i borrow your points???");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));


            }
        });


        btn_lent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LentPoints.class);
                startActivity(i);
            }
        });
    }

    private void loadData(final String uid) {
//        String cardNumber;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DataSnapshot threadSnap = dataSnapshot.child("Customers");

                Iterable<DataSnapshot> threadDetails = threadSnap.getChildren();

                System.out.println("ThreadDetails -----> "+threadDetails.toString());


                for (DataSnapshot snap : threadDetails) {

                    //String uid  = snap.getKey();

                    System.out.println("UID -----> "+uid);
                    //currentUser = snap.getValue(UserDetails.class);


                    if(snap.getKey().equals(uid)) {
                        userDetails = snap.getValue(UserDetails.class);
                        String cardNumber = userDetails.getCardNumber();
                        String points;
                        points  = userDetails.getPoints();
                        System.out.println("cardNumber -----> "+cardNumber);
                        System.out.println("currentUser -----> "+userDetails.toString());
                        System.out.println("Points ---> "+ points);
                        tv_userPoints.setText(points);
//                        tv_userEmail.setText(userData.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
