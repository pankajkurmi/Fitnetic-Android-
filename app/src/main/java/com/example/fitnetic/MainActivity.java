package com.example.fitnetic;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText LoginEmail,LoginPassword;
        Button LoginBtn;
        TextView LoginForgetPassword,CreateAccount;
        ProgressBar LoginProgressBar;
        FirebaseAuth fAuth;



        LoginEmail = findViewById(R.id.LoginEmail);
        LoginPassword = findViewById(R.id.LoginPassword);
        LoginProgressBar = findViewById(R.id.LoginProgressBar);
        fAuth = FirebaseAuth.getInstance();
        LoginBtn = findViewById(R.id.LoginBtn);
        CreateAccount =  findViewById(R.id.CreateAccount);
        LoginForgetPassword = findViewById(R.id.LoginForgetPassword);




        CreateAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registration.class);
                startActivity(i);
            }
        });


        LoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = LoginEmail.getText().toString().trim();
                String password = LoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    LoginEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    LoginPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    LoginPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                LoginProgressBar.setVisibility(View.VISIBLE);

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(),Categories.class));
                            Intent i = new Intent(MainActivity.this,Categories.class);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            LoginProgressBar.setVisibility(View.GONE);
                        }
                        LoginProgressBar.setVisibility(View.GONE);

                    }
                });

            }
        });





        LoginForgetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });

    }


// before adding  this code please add menu.xml file in menu folder by creating it
//  login page menu


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){


            case R.id.menuExpertLogInbtn:
//                Toast.makeText(this, "you clicked on Expert login on which our team members are working", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,ExpertLogin.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //login page menu ...giving path of menu.xml file
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }





}