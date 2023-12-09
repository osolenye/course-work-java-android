package com.example.kursach3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OwnerActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    Button btn_create_cafe, btn_see_cafes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);


        auth = FirebaseAuth.getInstance();
        btn_create_cafe = findViewById(R.id.btn_create_cafe);
        btn_see_cafes = findViewById(R.id.btn_see_cafes);
        user = auth.getCurrentUser();

        btn_create_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity.this, CafeCreateActivity.class);
                startActivity(intent);
            }
        });

        btn_see_cafes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerActivity.this, CafeSeeActivity.class);
                startActivity(intent);
            }
        });
    }
}