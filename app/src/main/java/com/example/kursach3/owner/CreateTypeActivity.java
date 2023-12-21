package com.example.kursach3.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Table;
import com.example.kursach3.models.Type;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTypeActivity extends AppCompatActivity {

    Button btn_create_type;
    EditText et_type_name;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Types");
    String cafeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_type);

        Intent intent = getIntent();
        cafeName = intent.getStringExtra("cafeName");


        btn_create_type = findViewById(R.id.btn_create_type);
        et_type_name = findViewById(R.id.et_type_name);


        btn_create_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeName = et_type_name.getText().toString();
                Type type = new Type(typeName, cafeName);
                String key = myRef.push().getKey();
                myRef.child(key).setValue(type);
                Toast.makeText(CreateTypeActivity.this, "added a new type", Toast.LENGTH_SHORT).show();
            }
        });
    }
}