package com.example.sri.auth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText e1, e2;
    Button b1, b2;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verification_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification_code=s;
                Toast.makeText(getApplicationContext(),"otp send to your number",Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void send_sms(View view) {
        String number = e1.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,                     // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                MainActivity.this,        // Activity (for callback binding)
                mCallback);
    };
        public  void signInWithPhone(PhoneAuthCredential credential) {
            auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "user signed in", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        public void verify(View view){
            String input_code=e2.getText().toString();
            if (verification_code.equals(""))
            {
                verifyphoneNumber(verification_code,input_code);

            }



        }
        public void verifyphoneNumber(String verifyCode,String input_code)
        {
PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verifyCode,input_code);
signInWithPhone(credential);
}
        }



