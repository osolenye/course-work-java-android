package com.example.kursach3.owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Food;
import com.example.kursach3.models.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TypeDetailsActivity extends AppCompatActivity {
    Intent intent;
    String cafeName;
    String typeName;
    Button btn_create_food_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_details);


        btn_create_food_activity = findViewById(R.id.btn_create_food_acitivity);
        intent = getIntent();
        cafeName = intent.getStringExtra("cafeName");
        typeName = intent.getStringExtra("typeName");



        ArrayList<Food> foods= new ArrayList<Food>();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Food");

        Query query = usersRef.orderByChild("cafeName").equalTo(cafeName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    Food food = snapshot.getValue(Food.class);
                    if (food.getTypeName().equals(typeName)) {
                        foods.add(food);
                    }
                    // user содержит данные пользователя, где поле fieldName равно desiredValue
                }


                ListView lv_food = findViewById(R.id.lv_food);
                FoodAdapter foodAdapter = new FoodAdapter(getApplicationContext(), foods);
                lv_food.setAdapter(foodAdapter);
                lv_food.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(TypeDetailsActivity.this, position + " " + foods.get(position).getCafeName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
            }
        });



        btn_create_food_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeDetailsActivity.this, CreateFoodActivity.class);
                intent.putExtra("cafeName", cafeName);
                intent.putExtra("typeName", typeName);
                startActivity(intent);
            }
        });
    }
}