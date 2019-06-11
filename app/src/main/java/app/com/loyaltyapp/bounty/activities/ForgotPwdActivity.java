package app.com.loyaltyapp.bounty.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import app.com.loyaltyapp.bounty.R;

public class ForgotPwdActivity extends AppCompatActivity {


    Button  btn_sendEmail;
    EditText  et_email;
    Context mContext;
    private FirebaseAuth firebaseAuth;
//    TextView set_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        firebaseAuth = FirebaseAuth.getInstance();
//        set_message = (TextView) findViewById(R.id.msgText);

        et_email = (EditText)findViewById(R.id.input_email);

        btn_sendEmail =  (Button) findViewById(R.id.btn_sendEmail);

        mContext = getApplicationContext();
        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = et_email.getText().toString().trim();

                if (useremail.equals("")) {
                    Toast.makeText(mContext, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                set_message.setVisibility(View.VISIBLE);
//                                set_message.setText("Passowrd reset email sent");
                                Toast.makeText(getApplicationContext(), "Password reset email sent", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ForgotPwdActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
//                                set_message.setVisibility(View.VISIBLE);
                                //Toast.makeText(ForgotPwdActivity.this, "Error sending Password reset email",Toast.LENGTH_SHORT).show();
//                                set_message.setText("Error sending Password reset email");
                                Toast.makeText(getApplicationContext(), "Error sending Password reset email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
