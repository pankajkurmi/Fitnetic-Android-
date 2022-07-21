package com.example.fitnetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ExpertPasswordReset extends AppCompatActivity {
    private EditText X_Email_ResetPass;
    private Button X_ResetPass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_password_reset);

        // Assigning Ids to Variables...........

        X_Email_ResetPass = findViewById(R.id.X_Email_ResetPass);
        X_ResetPass = findViewById(R.id.X_ResetPass);



        // Firebase Authentication Connection .........

        auth = FirebaseAuth.getInstance();


        // Redirect to Reset password page ........

        X_ResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling Function
                X_ResetPassword();
            }
        });
    }

    private void X_ResetPassword() {
        String X_Email = X_Email_ResetPass.getText().toString().trim();

        // Conditions.....

        if (X_Email.isEmpty()) {
            X_Email_ResetPass.setError("Email Required");
            X_Email_ResetPass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(X_Email).matches()) {
            X_Email_ResetPass.setError("Please Provide Valid Email");
            X_Email_ResetPass.requestFocus();
            return;

        }
        auth.sendPasswordResetEmail(X_Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ExpertPasswordReset.this,"Check Your email to Reset Password !",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ExpertPasswordReset.this,"Not Registered Email !! ",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
