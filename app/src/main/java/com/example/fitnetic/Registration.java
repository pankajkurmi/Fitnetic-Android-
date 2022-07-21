package com.example.fitnetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText RegisFullName,RegisPhone, RegisEmail, RegisPassword, RegisConfPassword;
    Button RegisterBtn;
    TextView RegisLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final String TAG = "TAG";
        RegisFullName   = findViewById(R.id.RegisFullName);
        RegisPhone      = findViewById(R.id.RegisPhone);
        RegisEmail      = findViewById(R.id.RegisEmail);
        RegisPassword   = findViewById(R.id.RegisPassword);
        RegisConfPassword = findViewById(R.id.RegisConfPassword);
        RegisterBtn= findViewById(R.id.RegisterBtn);
        RegisLoginBtn   = findViewById(R.id.RegisLoginBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.LoginProgressBar);

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName = RegisFullName.getText().toString();

                final String phone    = RegisPhone.getText().toString();
                final String email = RegisEmail.getText().toString().trim();
                String password = RegisPassword.getText().toString().trim();
                String confPassword = RegisConfPassword.getText().toString().trim();

                if(TextUtils.isEmpty(fullName)){
                    RegisFullName.setError("Full Name is required.");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    RegisEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    RegisPhone.setError("Phone number is Required.");
                    return;
                }
                if(phone.length() != 10){
                    RegisPhone.setError("Phone number must contain 10 digits.");
                }

                if(TextUtils.isEmpty(password)){
                    RegisPassword.setError("Password is required.");
                    return;
                }
                if(password.length()<6){
                    RegisPassword.setError("Password must have at least 6 characters.");
                    return;
                }
                if(TextUtils.isEmpty(confPassword)){
                    RegisConfPassword.setError("Confirm Password is required.");
                    return;
                }
                if(!password.equals(confPassword)){
                    RegisConfPassword.setError("Password do not match.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Registration.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();


                            //storing data into firestore database*****


                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("password",password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });


                            // Sending user phone number to otp verify page
                            Intent intent =new Intent(Registration.this, Categories.class);
                            intent.putExtra("UserPhoneNumberVerify",phone);
                            startActivity(intent);
                            finish();

                        }else {

                            Toast.makeText(Registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                progressBar.setVisibility(View.GONE);
            }
        });





        RegisLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}