package com.example.kursach3.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Food;
import com.example.kursach3.models.Type;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateFoodActivity extends AppCompatActivity {
    EditText et_food_name;
    Button btn_create_food;
    String cafeName;
    String typeName;
    Intent intent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Food");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);

        et_food_name = findViewById(R.id.et_food_name);
        btn_create_food = findViewById(R.id.btn_create_food);


        intent = getIntent();
        cafeName = intent.getStringExtra("cafeName");
        typeName = intent.getStringExtra("typeName");

        btn_create_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = et_food_name.getText().toString();
                Food food = new Food(cafeName, typeName, foodName);
                String key = myRef.push().getKey();
                myRef.child(key).setValue(food);
                Toast.makeText(CreateFoodActivity.this, "added a new food", Toast.LENGTH_SHORT).show();
            }
        });
    }
}