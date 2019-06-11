package app.com.loyaltyapp.bounty.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.com.loyaltyapp.bounty.R;


public class ProfileFragment extends Fragment {



    ImageView imageView;
    private TextInputLayout inputLayoutName, inputLayoutEmail,inputLayoutConfirmEmail, inputLayoutPassword , inputLayoutMobile;

    EditText et_fullname,et_phnNum,et_email,et_pwd,et_confirmEmail;
    Button btn_signUp;
    FirebaseDatabase database ;
    DatabaseReference mDatabaseRef;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    Context mcontext;

    Typeface typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mcontext = this.getActivity();


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        return view;
      //  return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    private void init(View view) {


         et_fullname = (EditText) view.findViewById(R.id.input_profile_name);
         et_phnNum = (EditText) view.findViewById(R.id.input_profile_phone);
//         et_email = (EditText) view.findViewById(R.id.input_email);
        btn_signUp = (Button) view.findViewById(R.id.btn_profile_update);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = mFirebaseAuth.getUid();
                mDatabaseRef.child("Customers").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("name").setValue(et_fullname.getText().toString());
                        dataSnapshot.getRef().child("phoneNumber").setValue(et_phnNum.getText().toString());
                        et_fullname.setText("");
                        et_phnNum.setText("");
                        Toast.makeText(mcontext, "Profile Updated!",Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());
                    }
                });
            }
        });

    }


}
