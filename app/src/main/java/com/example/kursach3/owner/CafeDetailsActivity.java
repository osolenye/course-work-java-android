package com.example.kursach3.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Cafe;
import com.example.kursach3.models.Table;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CafeDetailsActivity extends AppCompatActivity {
    ListView lv_tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_details);

        Intent intent = getIntent();
        String cafeName = intent.getStringExtra("name");
        ArrayList<Table> tables = new ArrayList<Table>();


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        Query query = usersRef.orderByChild("fieldName").equalTo("desiredValue");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    Table table = snapshot.getValue(Table.class);
                    tables.add(table);
                    // user содержит данные пользователя, где поле fieldName равно desiredValue
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
            }
        });




        lv_tables = findViewById(R.id.lv_tables);
        TablesAdapter tablesAdapter = new TablesAdapter(getApplicationContext(), tables);
        lv_tables.setAdapter(tablesAdapter);
    }
}