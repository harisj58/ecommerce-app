package com.harisjaved.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.harisjaved.ecommerceapp.R;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    TextView tvSignUp;
    Button button;
    FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.button);
        tvSignUp=findViewById(R.id.signUp);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

//        getSupportActionBar().hide();
        authStateListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser mFireBaseUser= auth.getCurrentUser();
                if(mFireBaseUser!=null){
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId=email.getText().toString();
                String pwd=password.getText().toString();
                if(emailId.isEmpty())
                {
                    email.setError("Please enter email ID");
                    email.requestFocus();
                }
                else if(pwd.isEmpty())
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                else if(emailId.isEmpty() && pwd.isEmpty())
                    Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                else if(!(emailId.isEmpty() && pwd.isEmpty()))
                {
                    auth.signInWithEmailAndPassword(emailId, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                                Toast.makeText(LoginActivity.this, "Login error, please login again", Toast.LENGTH_SHORT).show();
                            else
                            {
                                Intent intToHome=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else
                    Toast.makeText(LoginActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
}