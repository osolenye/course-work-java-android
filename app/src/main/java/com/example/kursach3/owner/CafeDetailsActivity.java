package com.example.kursach3.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button btn_create_table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_details);

        Intent intent = getIntent();
        String cafeName = intent.getStringExtra("name");
        ArrayList<Table> tables = new ArrayList<Table>();
        btn_create_table = findViewById(R.id.btn_create_table);
        Toast.makeText(this, cafeName, Toast.LENGTH_SHORT).show();
        lv_tables = findViewById(R.id.lv_tables);


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Tables");

        Query query = usersRef.orderByChild("cafeName").equalTo(cafeName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    Table table = snapshot.getValue(Table.class);
                    tables.add(table);
                    // user содержит данные пользователя, где поле fieldName равно desiredValue
                }
                TablesAdapter tablesAdapter = new TablesAdapter(getApplicationContext(), tables);
                lv_tables.setAdapter(tablesAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
                Toast.makeText(CafeDetailsActivity.this, "mat' ebal", Toast.LENGTH_SHORT).show();
            }
        });








        btn_create_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CafeDetailsActivity.this, CreateTableActivity.class);
                intent.putExtra("name", cafeName);
                startActivity(intent);
            }
        });
    }
}