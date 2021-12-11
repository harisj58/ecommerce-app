package com.harisjaved.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.harisjaved.ecommerceapp.R;



public class PaymentActivity extends AppCompatActivity {
    double amount = 0.0;
    Toolbar toolbar;
    TextView subTotal, discount, shipping, total;
    Button paymentBtn, go_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //Toolbar
        toolbar = findViewById(R.id.payment_toolbar);
        amount = getIntent().getDoubleExtra("amount", 0.0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        paymentBtn = findViewById(R.id.pay_btn);
        go_home=findViewById(R.id.go_home);
        go_home.setVisibility(View.INVISIBLE);
        subTotal.setText("₹" + amount);
        amount+=50;
        total.setText("₹"+amount);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Thank you for shopping with us!", Toast.LENGTH_SHORT).show();
                go_home.setVisibility(View.VISIBLE);
            }
        });
        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
            }
        });
    }
}