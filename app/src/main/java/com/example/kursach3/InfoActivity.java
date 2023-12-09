package com.example.kursach3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursach3.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class InfoActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser currentUser;
    TextView tv_user_info;
    ArrayList<String> usersEmails = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        tv_user_info = findViewById(R.id.tv_user_info);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = usersRef.orderByChild("role").equalTo("owner");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    usersEmails.add(user.getEmail());
                }

                // Обновление TextView после получения данных из базы
                String tvText = "";
                for (int i = 0; i < usersEmails.size(); i++) {
                    tvText += usersEmails.get(i) + "\n"; // Добавляем новую строку между адресами
                }
                tv_user_info.setText(tvText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок
            }
        });
    }
}