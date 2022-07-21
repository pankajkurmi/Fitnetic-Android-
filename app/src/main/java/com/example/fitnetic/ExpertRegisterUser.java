package com.example.fitnetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ExpertRegisterUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner X_Register2_Category, X_Register_Gender;
    private EditText X_Register_Name, X_Register_PhoneNo, X_Register_Email, X_Register_DOB, X_Password , X_Time , X_Fees;
    private EditText X_Register2_Exp, X_Register2_Location, X_Register2_Higher;
    Button X_Submit;
    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_register_user);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        X_Register_Name = findViewById(R.id.X_Register_Name);
        X_Register_PhoneNo = findViewById(R.id.X_Register_PhoneNo);
        X_Register_Email = findViewById(R.id.X_Register_Email);
        X_Register_DOB = findViewById(R.id.X_Register_DOB);

        X_Password = findViewById(R.id.X_Password);
        X_Register2_Exp = findViewById(R.id.X_Register2_Exp);
        X_Register2_Location = findViewById(R.id.X_Register2_Location);
        X_Register2_Higher = findViewById(R.id.X_Register2_higher);
        X_Fees = findViewById(R.id.X_Fees_);
        X_Time = findViewById(R.id.X_Time);

        X_Submit = findViewById(R.id.X_Submit);
        X_Submit.setOnClickListener(this);

        X_Register2_Category = findViewById(R.id.X_Register2_Category);
        X_Register_Gender = findViewById(R.id.X_Register_Gender);

        //spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        X_Register_Gender.setAdapter(adapter);
        X_Register_Gender.setOnItemSelectedListener(this);
        adapter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        X_Register2_Category.setAdapter(adapter);
        X_Register2_Category.setOnItemSelectedListener(this);


    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.X_Submit:
                registerUser();
                break;

        }

    }

    private void registerUser() {
        String X_Name = X_Register_Name.getText().toString().trim();
        String X_Email = X_Register_Email.getText().toString().trim();
        String X_PhoneNo = X_Register_PhoneNo.getText().toString().trim();
        String X_DOB = X_Register_DOB.getText().toString().trim();
        String X_Pass = X_Password.getText().toString().trim();
        String X_Experience = X_Register2_Exp.getText().toString().trim();
        String X_Location = X_Register2_Location.getText().toString().trim();
        String X_HigherQualifications = X_Register2_Higher.getText().toString().trim();
        String x_time = X_Time.getText().toString().trim();
        String x_fees = X_Fees.getText().toString().trim();
        String X_Category = X_Register2_Category.getSelectedItem().toString().trim();
        String X_Gender = X_Register_Gender.getSelectedItem().toString().trim();

        // Conditions...............

        if (X_Name.isEmpty()) {
            X_Register_Name.setError("Full Name is Required !");
            X_Register_Name.requestFocus();
            return;
        }
        if (X_PhoneNo.isEmpty()) {
            X_Register_PhoneNo.setError("Phone Number is Required !");
            X_Register_PhoneNo.requestFocus();
            return;
        }
        if (X_PhoneNo.length() != 10) {
            X_Register_PhoneNo.setError("Enter Valid Phone Number !");
            X_Register_PhoneNo.requestFocus();
            return;
        }
        if (X_Email.isEmpty()) {
            X_Register_Email.setError("Email is Required !");
            X_Register_Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(X_Email).matches()) {
            X_Register_Email.setError("Please Provide Valid Email");
            X_Register_Email.requestFocus();
            return;
        }


        if (X_DOB.isEmpty()) {
            X_Register_DOB.setError("Date of Birth is Required !");
            X_Register_DOB.requestFocus();
            return;
        }
        if (X_Pass.isEmpty()) {
            X_Password.setError("Password is Required !");
            X_Password.requestFocus();
            return;
        }
        if (X_Pass.length() < 8) {
            X_Password.setError("Minimum Password Length Should be 8 characters!");
            X_Password.requestFocus();
            return;
        }
        if (X_Experience.isEmpty()) {
            X_Register2_Exp.setError("Experience is Required !");
            X_Register2_Exp.requestFocus();
            return;
        }
        if (X_Location.isEmpty()) {
            X_Register2_Location.setError("Location is Required !");
            X_Register2_Location.requestFocus();
            return;
        }
        if (X_HigherQualifications.isEmpty()) {
            X_Register2_Higher.setError("Higher Qualification is Required !");
            X_Register2_Higher.requestFocus();
            return;
        }
        if (x_time.isEmpty()) {
            X_Time.setError("Preferable Time is Required !");
            X_Time.requestFocus();
            return;
        }
        if (x_fees.isEmpty()) {
            X_Fees.setError("Enter Fees !");
            X_Fees.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(X_Email,X_Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ExpertRegisterUser.this,"User details added!!",Toast.LENGTH_LONG).show();
                    userId = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("Expert_Details").document(userId);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Name",X_Name);
                    user.put("PhoneNo",X_PhoneNo);
                    user.put("Email",X_Email);
                    user.put("Gender",X_Gender);
                    user.put("Password",X_Pass);
                    user.put("DateOfBirth",X_DOB);
                    user.put("Category",X_Category);
                    user.put("Experience",X_Experience);
                    user.put("Location",X_Location);
                    user.put("Qualification",X_HigherQualifications);
                    user.put("Time",x_time);
                    user.put("Fees",x_fees);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.d("TAG","User Profile Created !!!");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"Failed " + e.toString());

                        }
                    });

                   startActivity(new Intent(getApplicationContext(), ExpertLogin.class));
                }
                else{
                    Toast.makeText(ExpertRegisterUser.this,"Failed to Register!!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}