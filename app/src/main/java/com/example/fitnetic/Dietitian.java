package com.example.fitnetic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Dietitian extends AppCompatActivity {


      RecyclerView recyclerView;
      ArrayList<Users> usersArrayList;
      MyAdapter myAdapter;
      FirebaseFirestore db;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietitian);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data ......");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleview_dietition);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        usersArrayList = new ArrayList<Users>();
        myAdapter = new MyAdapter(Dietitian.this,usersArrayList,getApplicationContext());

        recyclerView.setAdapter(myAdapter);








        db.collection("Expert_Details").whereEqualTo("Category","Dietitian")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                if(error != null){

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();


                    Log.e("Firebase error",error.getMessage());
                    return;
                }



                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){



                        usersArrayList.add(dc.getDocument().toObject(Users.class));

                    }
                    myAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }


            }
        });



    }


}