package app.com.loyaltyapp.bounty.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.models.UserDetails;
import app.com.loyaltyapp.bounty.utils.ComicTextView;
import app.com.loyaltyapp.bounty.utils.SharedPreferenceHelper;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseReference;
    private ArrayList<UserDetails> userDetails = new ArrayList<>();
    EditText et_email, et_password;
    TextInputLayout txtEmail, txtPassword;
    Typeface typeface;
    Button btnLogin;
    FirebaseAuth mAuth;
    ComicTextView tv_register;
    UserDetails userData;

    ComicTextView tv_forgotPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();
        typeface = Typeface.createFromAsset(getAssets(),"COMIC.TTF");
        et_email = (EditText) findViewById(R.id.input_email);
        et_password = (EditText) findViewById(R.id.input_password);

        txtEmail = (TextInputLayout) findViewById(R.id.txtEmail);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);
        tv_forgotPwd = (ComicTextView)findViewById(R.id.link_forgotpwd);

        tv_register = (ComicTextView) findViewById(R.id.btn_register);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setTypeface(typeface);

        txtEmail.setTypeface(typeface);
        txtPassword.setTypeface(typeface);

        et_email.setTypeface(typeface);
        et_password.setTypeface(typeface);
        mFirebaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseReference.keepSynced(true);
        //loadData();



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = et_email.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(name, pwd).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
        tv_forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPwdActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });

    }


//    private void loadData() {
//
//        mFirebaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                DataSnapshot loginSnap = dataSnapshot.child("Customers").getKey().;
//                Iterable<DataSnapshot> loginChildren = loginSnap.getChildren();
//
//                for (DataSnapshot snap : loginChildren) {
//                    UserDetails customer = snap.getValue(UserDetails.class);
//                    userDetails.add(customer);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
