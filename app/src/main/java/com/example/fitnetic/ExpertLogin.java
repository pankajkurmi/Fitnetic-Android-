package com.example.fitnetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ExpertLogin extends AppCompatActivity implements View.OnClickListener {

    private TextView X_Login_CreateAcc;
     private EditText X_Email,X_Pass;
    private FirebaseAuth mAuth;
    private TextView X_Login_Forgetpass;
    private Button X_Login_Loginbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_login);

        // Assigning Ids to Variables...........

        X_Email = findViewById(R.id.X_Login_Email);
        X_Pass = findViewById(R.id.X_Login_Password);
        X_Login_Loginbtn = findViewById(R.id.X_Login_Loginbtn);
        X_Login_Loginbtn.setOnClickListener(this);
        X_Login_Forgetpass = findViewById(R.id.X_Login_ForgetPass);
        X_Login_Forgetpass.setOnClickListener(this);

        // Firebase Authentication Connection.......

        mAuth = FirebaseAuth.getInstance();

        X_Login_CreateAcc = findViewById(R.id.X_Login_CreateAcc);
        X_Login_CreateAcc.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.X_Login_CreateAcc:
                startActivity(new Intent(this, ExpertRegisterUser.class));
                break;

            case R.id.X_Login_Loginbtn:
                userlogin();
                break;

            case R.id.X_Login_ForgetPass:
                startActivity(new Intent(ExpertLogin.this, ExpertPasswordReset.class));
                break;
        }
    }

    public void userlogin() {
          String email = X_Email.getText().toString().trim();
          String pass = X_Pass.getText().toString().trim();

        // Condition for login
        if(email.isEmpty()){
            X_Email.setError("Email is required !!!");
            X_Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            X_Email.setError("Please Provide Valid Email");
            X_Email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            X_Pass.setError("Password is required !!!");
            X_Pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser X_User = FirebaseAuth.getInstance().getCurrentUser();
                    {
                        if (X_User.isEmailVerified()) {

                            Toast.makeText(ExpertLogin.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();





                            startActivity(new Intent(getApplicationContext(), ExpertProfile.class));
                        } else {
                            X_User.sendEmailVerification();
                            Toast.makeText(ExpertLogin.this, "Check Your Email to Verify Your Account", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                else{
                    Toast.makeText(ExpertLogin.this, "Error ! Check Your Credentials " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}