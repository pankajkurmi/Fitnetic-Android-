package com.example.fitnetic;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class profile extends AppCompatActivity implements PaymentResultListener {
    TextView tb1,tb2,tb3,tb4,tb5,tb6,tb7;
    Button paybtn;
    TextView paytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tb1 = (TextView)findViewById(R.id.name);
        tb2 = (TextView)findViewById(R.id.qualification);
        tb3 = (TextView)findViewById(R.id.experience);
        tb4 = (TextView)findViewById(R.id.fees);
        tb5 = (TextView)findViewById(R.id.consultation);
        tb6 = (TextView)findViewById(R.id.past_work);
        tb7 = (TextView)findViewById(R.id.achivements);

        tb1.setText(getIntent().getStringExtra("Name"));
        tb2.setText(getIntent().getStringExtra("Qualification"));
        tb3.setText(getIntent().getStringExtra("Experience"));
        tb4.setText(getIntent().getStringExtra("Fees"));
        tb5.setText(getIntent().getStringExtra("Time"));
        tb6.setText(getIntent().getStringExtra("Past Work"));
        tb7.setText(getIntent().getStringExtra("Achivementes"));
        paybtn = findViewById(R.id.paybtn);
        paytext = findViewById(R.id.paytext);


        Checkout.preload(getApplicationContext());
        
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                startPayment();
            }
        });



    }

    private void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_gO55pFKl04kaCS");

        checkout.setImage(R.drawable.applogo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Fitnetic");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "300000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","9309848601");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("ONSUCCESS", "onPaymentSuccess: " + s);

        Intent i = new Intent(profile.this, VideoCallStartButton.class);


        startActivity(i);






    }

    //Intent i = new Intent(profile.this, VideoCallStart)


    @Override
    public void onPaymentError(int i, String s) {
        Log.d("ONERROR", "onPaymentError: "+s);
    }


}