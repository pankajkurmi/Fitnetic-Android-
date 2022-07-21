package com.example.fitnetic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ExpertProfile extends AppCompatActivity {

    TextView name , phone,email,gender,dateofbirth,category,experience,qualification,location,time,fees,welcome;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userid;
    Button logout , changepass, changeprofile;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_profile);
        name = findViewById(R.id.Name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        dateofbirth = findViewById(R.id.dateofbirth);
        category = findViewById(R.id.category);
        experience = findViewById(R.id.experience);
        qualification = findViewById(R.id.qualification);
        // adpterview
        // location = findviewTd(R.Id.location);


        location = findViewById(R.id.location);
        changeprofile= findViewById(R.id.changeprofile);
        time = findViewById(R.id.time);
        fees = findViewById(R.id.fees);
        welcome = findViewById(R.id.welcome);
        logout=findViewById(R.id.logout);
        profileImage=findViewById(R.id.profileImage);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("Experts/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        DocumentReference documentReference = fstore.collection("Expert_Details").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String type = value.getString("Category");

                  name.setText(value.getString("Name"));
                welcome.setText(value.getString("Name"));
                email.setText(value.getString("Email"));
                phone.setText(value.getString("PhoneNo"));
                gender.setText(value.getString("Gender"));
                dateofbirth.setText(value.getString("DateOfBirth"));
                category.setText(value.getString("Category"));
                experience.setText(value.getString("Experience"));
                qualification.setText(value.getString("Qualification"));
                location.setText(value.getString("Location"));

                time.setText(value.getString("Time"));
                fees.setText(value.getString("Fees"));

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(), ExpertLogin.class));
                finish();
            }
        });
        changepass=findViewById(R.id.changepass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetPassword = new EditText(view.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter New Password more than 6 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ExpertProfile.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ExpertProfile.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();
            }
        });

        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent,1000);
                Intent i = new Intent(view.getContext(),ExpertEditProfile.class);
                i.putExtra("fullname",name.getText().toString());
                i.putExtra("Contact",phone.getText().toString());
                i.putExtra("email",email.getText().toString());
                i.putExtra("experience",experience.getText().toString());
                i.putExtra("Location",location.getText().toString());
                i.putExtra("Qualification",qualification.getText().toString());
                i.putExtra("PreferableTime",time.getText().toString());
                i.putExtra("fees",fees.getText().toString());
                startActivity(i);

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.VideoCall:
                startActivity(new Intent(ExpertProfile.this, VideoCallUserExpert.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(), ExpertLogin.class));
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.expet_menu,menu);
        return true;
    }


}
