package com.example.kursach3.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kursach3.R;
import com.example.kursach3.models.Table;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTableActivity extends AppCompatActivity {
    Button btn_create_table_db;
    EditText et_table_name;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Tables");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);

        Intent intent = getIntent();
        String cafeName = intent.getStringExtra("name");


        btn_create_table_db = findViewById(R.id.btn_create_table_db);
        et_table_name = findViewById(R.id.et_table_name);


        btn_create_table_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = et_table_name.getText().toString();
                String key = myRef.push().getKey();
                Table table = new Table(tableName, cafeName, key);
                myRef.child(key).setValue(table);
            }
        });
    }
}