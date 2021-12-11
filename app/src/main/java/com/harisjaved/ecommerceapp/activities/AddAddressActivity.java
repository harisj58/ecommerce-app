package com.harisjaved.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harisjaved.ecommerceapp.R;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    EditText name, address, city, postalCode, phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        name=findViewById(R.id.ad_name);
        address=findViewById(R.id.ad_address);
        city=findViewById(R.id.ad_city);
        phoneNumber=findViewById(R.id.ad_phone);
        postalCode=findViewById(R.id.ad_code);
        addAddressBtn=findViewById(R.id.ad_add_address);
        double amount=getIntent().getDoubleExtra("amount", 0.0);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, userCity, userAddress, userCode, userNumber;
                userName=name.getText().toString();
                userCity=city.getText().toString();
                userAddress=address.getText().toString();
                userCode=postalCode.getText().toString();
                userNumber=phoneNumber.getText().toString();
                String final_address="";
                if(!userName.isEmpty())
                    final_address+=userName;
                if(!userAddress.isEmpty())
                    final_address+=", "+userAddress;
                if(!userCity.isEmpty())
                    final_address+=", "+userCity;
                if(!userCode.isEmpty())
                    final_address+=", "+userCode;
                if(!userNumber.isEmpty())
                    final_address+=", "+userNumber;
                if(!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty())
                {
                    Map<String, String> map=new HashMap<>();
                    map.put("userAddress", final_address);
                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(AddAddressActivity.this, "Address added", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AddAddressActivity.this, AddressActivity.class);
                                intent.putExtra("amount", amount);
                                startActivity(intent);
                                finish();
                            }
                            else
                                Toast.makeText(AddAddressActivity.this, "Fill all fields properly", Toast.LENGTH_SHORT).show();
                        }
                    });
                    
                }
            }
        });
    }
}