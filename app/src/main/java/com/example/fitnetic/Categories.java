package com.example.fitnetic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {


    Button Dietitianbtn,Physicianbtn,trainerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Dietitianbtn=findViewById(R.id.Dietitianbtn);
        Physicianbtn=findViewById(R.id.physicianbtn);
        trainerbtn=findViewById(R.id.Trainerbtn);

        Dietitianbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Categories.this,Dietitian.class));
            }
        });
        trainerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Categories.this,Trainer.class));
            }
        });
        Physicianbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Categories.this,Physician.class));
            }
        });

    }



    // menu user
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.yourprofile:
//                Toast.makeText(this, "Help..!!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Categories.this,UserProfile.class);
                startActivity(intent);
                return true;

            case R.id.logout:
//                Toast.makeText(this, "you clicked on Expert login on which our team members are working", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Categories.this,MainActivity.class);
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
        inflater.inflate(R.menu.menuuser,menu);
        return true;
    }




}