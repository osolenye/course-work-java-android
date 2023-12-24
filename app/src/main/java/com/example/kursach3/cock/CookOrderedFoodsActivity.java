package com.example.kursach3.cock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kursach3.R;
import com.example.kursach3.models.FoodOrder;
import com.example.kursach3.waiter.WaiterFoodAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CookOrderedFoodsActivity extends AppCompatActivity {

    Intent intent;
    String tableName;
    String cafeName;
    FirebaseAuth auth;
    FirebaseUser user;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_ordered_foods);

        intent = getIntent();
        tableName = intent.getStringExtra("tableName");
        cafeName = intent.getStringExtra("cafeName");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = user.getEmail();



        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("FoodOrders");
        Query ordersQuery = ordersRef.orderByChild("tableName").equalTo(tableName);
        ordersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FoodOrder> orders= new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                    if (foodOrder.getCafeName().equals(cafeName)) {
                        orders.add(foodOrder);
                    }
                }

                ListView lv_cook_ordered_foods = findViewById(R.id.lv_cook_ordered_foods);
                CookOrderedFoodAdapter cookOrderedFoodAdapter = new CookOrderedFoodAdapter(getApplicationContext(), orders);
                lv_cook_ordered_foods.setAdapter(cookOrderedFoodAdapter);
                lv_cook_ordered_foods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Toast.makeText(WaiterTableFunctionsActivity.this, position + " " + orders.get(position).getCafeName(), Toast.LENGTH_SHORT).show();
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