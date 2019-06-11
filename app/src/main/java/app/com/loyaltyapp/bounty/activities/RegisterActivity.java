package app.com.loyaltyapp.bounty.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.com.loyaltyapp.bounty.R;
import app.com.loyaltyapp.bounty.models.UserDetails;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseReference;
    private ArrayList<UserDetails> userDetails = new ArrayList<>();
    private Long counter;
    private TextInputLayout inputLayoutName, inputLayoutEmail,inputLayoutPassword, inputLayoutMobile;

    EditText et_fullname, et_phnNum, et_email, et_pwd;
    Button btn_signUp;

    Typeface typeface;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {

        typeface = Typeface.createFromAsset(getAssets(), "COMIC.TTF");

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_phone);

        inputLayoutName.setTypeface(typeface);
        inputLayoutEmail.setTypeface(typeface);

        inputLayoutPassword.setTypeface(typeface);
        inputLayoutMobile.setTypeface(typeface);

        //initialization
        et_fullname = (EditText) findViewById(R.id.input_name);
        et_phnNum = (EditText) findViewById(R.id.input_phone);
        et_email = (EditText) findViewById(R.id.input_email);
        et_pwd = (EditText) findViewById(R.id.input_password);

        et_fullname.setTypeface(typeface);
        et_email.setTypeface(typeface);
        et_phnNum.setTypeface(typeface);
        et_pwd.setTypeface(typeface);

        btn_signUp = (Button) findViewById(R.id.btn_signup);
        btn_signUp.setTypeface(typeface);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent
                registerUser();
            }
        });
    }

    private void registerUser() {

        final String name = et_fullname.getText().toString().trim();
        final String mobile = et_phnNum.getText().toString();
        final String email = et_email.getText().toString().trim();
        final String password = et_pwd.getText().toString().trim();


        if (name.isEmpty()) {
            et_fullname.setError("name required");
            et_fullname.requestFocus();
            return;
        }

        if (mobile.isEmpty()) {
            et_phnNum.setError("phone required");
            et_phnNum.requestFocus();
            return;
        }

        if (mobile.length() != 10) {
            et_phnNum.setError("10 Number is required");
            et_phnNum.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            et_email.setError("email required");
            et_email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            et_pwd.setError("email required");
            et_pwd.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_pwd.setError("Password too short, enter minimum 6 characters!");
            et_pwd.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // register extra field in the database

                            final UserDetails user = new UserDetails(name, mobile, email, "", "");

                            // create user in the firebase
                            FirebaseDatabase.getInstance().getReference("Customers").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Registration Sucessfull", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                            Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_LONG).show();
                                        else
                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }

    private boolean validation() {

        if (et_fullname.getText().toString().equals("") || et_fullname.getText().toString().equals(null)) {
            Toast.makeText(this, "Please enter fullname! ", Toast.LENGTH_LONG).show();
        } else {
            return true;
        }
        return false;
    }

    public void redirectToLogin(View view) {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void mainPage(View view) {

    }
}
