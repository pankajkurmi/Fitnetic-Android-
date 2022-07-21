package com.example.fitnetic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ExpertEditProfile extends AppCompatActivity {

    public static final String TAG="TAG";
    EditText edname , edphone , edemail , edexperience, edqualification , edlocation ,edtime , edfees;
    Button edSave;
    ImageView edprofileImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_edit_profile);

        Intent data = getIntent();
        String fullName = data.getStringExtra("fullname");
        String Contact = data.getStringExtra("Contact");
        String email = data.getStringExtra("email");
        String experience = data.getStringExtra("experience");
        String Location = data.getStringExtra("Location");
        String Qualification = data.getStringExtra("Qualification");
        String PreferableTime = data.getStringExtra("PreferableTime");
        String fees = data.getStringExtra("fees");

        fAuth= FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        edname = findViewById(R.id.edName);
        edphone = findViewById(R.id.edphone);
        edemail = findViewById(R.id.edemail);
        edexperience = findViewById(R.id.edexperience);
        edlocation = findViewById(R.id.edlocation);
        edqualification = findViewById(R.id.edqualification);
        edtime = findViewById(R.id.edtime);
        edfees = findViewById(R.id.edfees);
        edSave = findViewById(R.id.edSave);
        edprofileImage = findViewById(R.id.EdprofileImage);

        StorageReference profileRef = storageReference.child("Experts/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(edprofileImage);
            }
        });


        edprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(openGalleryIntent,1000);

            }
        });
        edSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edname.getText().toString().isEmpty() || edphone.getText().toString().isEmpty() || edemail.getText().toString().isEmpty() || edexperience.getText().toString().isEmpty() || edlocation.getText().toString().isEmpty() || edqualification.getText().toString().isEmpty() || edtime.getText().toString().isEmpty() || edfees.getText().toString().isEmpty()) {
                    Toast.makeText(ExpertEditProfile.this, "Data Not Added", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = edemail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference documentReference = fstore.collection("Expert_Details").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("Name",edname.getText().toString());
                        edited.put("Email",email);
                        edited.put("PhoneNo",edphone.getText().toString());
                        edited.put("Experience",edexperience.getText().toString());
                        edited.put("Qualification",edqualification.getText().toString());
                        edited.put("Location",edlocation.getText().toString());
                        edited.put("Time",edtime.getText().toString());
                        edited.put("Fees",edfees.getText().toString());
                        documentReference.update(edited);
                        Toast.makeText(ExpertEditProfile.this, "Profile Updated!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ExpertProfile.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ExpertEditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        edname.setText(fullName);
        edphone.setText(Contact);
        edemail.setText(email);
        edexperience.setText(experience);
        edlocation.setText(Location);
        edqualification.setText(Qualification);
        edtime.setText(PreferableTime);
        edfees.setText(fees);


        Log.d(TAG,"onCreate:" +fullName + " " + Contact + " " + email + " " + experience + " " + Location + " " + Qualification + " " + PreferableTime + " " + fees );




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);

            }
        }
    }


    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("Experts/" + fAuth.getCurrentUser().getUid() + "profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(edprofileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ExpertEditProfile.this,"Image Not Uploaded",Toast.LENGTH_SHORT).show();

            }
        });
    }
}