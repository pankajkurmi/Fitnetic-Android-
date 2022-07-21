package com.example.fitnetic;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread thread =new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }
                catch(Exception e){
                    e.printStackTrace();

                }
                finally{
                    Intent intent=new Intent(splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }; thread.start();
    }
}