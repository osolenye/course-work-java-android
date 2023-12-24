package com.example.kursach3.waiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Food;
import com.example.kursach3.owner.FoodAdapter;
import com.example.kursach3.owner.TypeDetailsActivity;
import com.example.kursach3.owner.TypesAdapter;
import com.example.kursach3.owner.TypesSeeActivty;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {
    String cafeName;
    static String tableName;
    Intent intent;
    String typeName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        intent = getIntent();
        typeName = intent.getStringExtra("typeName");
        cafeName = intent.getStringExtra("cafeName");
        tableName = intent.getStringExtra("tableName");


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
                        Log.d("food" ,"food name: " + foods.get(0).getTypeName());
                    }
                    ListView lv_waiter_food_choose = findViewById(R.id.lv_waiter_food_choose);
                    WaiterFoodChooseAdapter waiterFoodChooseAdapter = new WaiterFoodChooseAdapter(getApplicationContext(), foods);
                    lv_waiter_food_choose.setAdapter(waiterFoodChooseAdapter);
                    lv_waiter_food_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });
                    // user содержит данные пользователя, где поле fieldName равно desiredValue
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
            }
        });
    }
}