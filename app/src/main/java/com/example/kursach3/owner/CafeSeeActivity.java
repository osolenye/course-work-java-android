package com.example.kursach3.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Cafe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CafeSeeActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_see);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String email = user.getEmail();

        ArrayList<Cafe> cafes = new ArrayList<Cafe>();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Cafes");

        Query query = usersRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    Cafe cafe = snapshot.getValue(Cafe.class);
                    cafes.add(cafe);
                    // user содержит данные пользователя, где поле fieldName равно desiredValue
                }


                ListView lv_cafes = findViewById(R.id.lv_cafes);
                CafesAdapter cafesAdapter = new CafesAdapter(getApplicationContext(), cafes);
                lv_cafes.setAdapter(cafesAdapter);
                lv_cafes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CafeSeeActivity.this, CafeDetailsActivity.class);
                        intent.putExtra("name", cafes.get(position).getName());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
            }
        });
    }
}