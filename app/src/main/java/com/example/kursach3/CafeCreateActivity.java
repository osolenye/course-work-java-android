package com.example.kursach3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursach3.models.Cafe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class CafeCreateActivity extends AppCompatActivity {
    EditText et_name, et_password;
    Button btn_create;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_create);

        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        btn_create= findViewById(R.id.btn_create);


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                String email = user.getEmail();

                Cafe cafe = new Cafe(name, password, email);
                FirebaseDatabase.getInstance().getReference("Cafes")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(cafe).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CafeCreateActivity.this, "created a cafe", Toast.LENGTH_SHORT).show();
                                } else{
                                    Toast.makeText(CafeCreateActivity.this, "error creating a cafe", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}