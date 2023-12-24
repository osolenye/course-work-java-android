package com.example.kursach3;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursach3.cock.CookActivity;
import com.example.kursach3.models.User;
import com.example.kursach3.owner.OwnerActivity;
import com.example.kursach3.waiter.SearchCafeActivity;
import com.example.kursach3.waiter.WaiterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText et_email, et_password;
    Button btn_login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                    Query query = usersRef.orderByChild("email").equalTo(email);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            User user;
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                user = snapshot.getValue(User.class);

                                                String role = user.getRole();

                                                if (role.equals("owner")) {
                                                    Intent intent = new Intent(LoginActivity.this, OwnerActivity.class);
                                                    startActivity(intent);
                                                } else if (role.equals("waiter")) {
                                                    if (user.getCafe().equals("none")) {
                                                        Intent intent = new Intent(LoginActivity.this, SearchCafeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                    else {
                                                        Intent intent = new Intent(LoginActivity.this, WaiterActivity.class);
                                                        startActivity(intent);
                                                    }
                                                } else if (role.equals("cook")) {
                                                    if (user.getCafe().equals("none")) {
                                                        Intent intent = new Intent(LoginActivity.this, com.example.kursach3.cock.SearchCafeActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Intent intent = new Intent(LoginActivity.this, CookActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                } else {
                                    Toast.makeText(LoginActivity.this, "oh i am sorry you're a nigger", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());

                                }
                            }
                        });
            }
        });
    }
}