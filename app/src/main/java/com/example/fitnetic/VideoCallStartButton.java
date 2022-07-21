package com.example.fitnetic;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Random;

//import java.util.Random;
//
//import papaya.in.sendmail.SendMail;

public class VideoCallStartButton extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;
    String userEmail;
    String expertEmail="drashtigoswami009@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_start_button);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();


        //displaying data on dashboard
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    userEmail = documentSnapshot.getString("email");

                }
            }
        });



        Button sendCode=findViewById(R.id.button4);
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
                Intent i = new Intent(VideoCallStartButton.this,VideoCallUserExpert.class);
                startActivity(i);
            }
        });

    }

    private String GenerateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String code=Integer.toString(number);
        return code;
    }

    private void sendMail() {

        //Send Mail
        String enterCode=GenerateCode();
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,userEmail,"FitneTick - Code to join video meeting ","Use code : "+enterCode+" to join the video meeting \n After video call please provide feedback");
        JavaMailAPI javaMailAPI1 = new JavaMailAPI(this,expertEmail,"FitneTick - Code to join video meeting ","Use code : "+enterCode+" to join the video meeting ");

        javaMailAPI.execute();
        javaMailAPI1.execute();

    }


}

//    private String GenerateCode() {
//        Random rnd = new Random();
//        int number = rnd.nextInt(999999);
//        String code = Integer.toString(number)
