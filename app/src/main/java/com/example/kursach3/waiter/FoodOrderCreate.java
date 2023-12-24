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
import com.example.kursach3.models.FoodOrder;
import com.example.kursach3.models.Type;
import com.example.kursach3.owner.TypeDetailsActivity;
import com.example.kursach3.owner.TypesAdapter;
import com.example.kursach3.owner.TypesSeeActivty;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodOrderCreate extends AppCompatActivity {
    Intent intent;
    String tableName;
    String cafeName;
    FirebaseAuth auth;
    FirebaseUser user;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_create);

        intent = getIntent();
        tableName = intent.getStringExtra("tableName");
        cafeName = intent.getStringExtra("cafeName");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        email = user.getEmail();
        updateOrdered();


        ArrayList<Type> types = new ArrayList<Type>();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Types");

        Query query = usersRef.orderByChild("cafeName").equalTo(cafeName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    Type type = snapshot.getValue(Type.class);
                    Log.d("FirebaseData", "Cafe Name: " + type.getCafeName() + ", Type Name: " + type.getTypeName());
                    types.add(type);
                    // user содержит данные пользователя, где поле fieldName равно desiredValue

                    ListView lv_waiter_type_choose = findViewById(R.id.lv_waiter_type_choose);
                    WaiterTypeAdapter waiterTypeAdapter = new WaiterTypeAdapter(getApplicationContext(), types);
                    lv_waiter_type_choose.setAdapter(waiterTypeAdapter);
                    lv_waiter_type_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(FoodOrderCreate.this, FoodListActivity.class);
                            intent.putExtra("cafeName", types.get(position).getCafeName());
                            intent.putExtra("typeName", types.get(position).getTypeName());
                            intent.putExtra("tableName", tableName);
                            startActivity(intent);
                        }
                    });
                }


//                ListView lv_waiter_type_choose = findViewById(R.id.lv_waiter_type_choose);
//                WaiterTypeAdapter waiterTypeAdapter = new WaiterTypeAdapter(getApplicationContext(), types);
//                lv_waiter_type_choose.setAdapter(waiterTypeAdapter);
//                lv_waiter_type_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent(FoodOrderCreate.this, FoodListActivity.class);
//                        intent.putExtra("cafeName", types.get(position).getCafeName());
//                        intent.putExtra("typeName", types.get(position).getTypeName());
//                        startActivity(intent);
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
            }
        });


        ArrayList<FoodOrder> foodOrders = new ArrayList<FoodOrder>();

        DatabaseReference usersRef1 = FirebaseDatabase.getInstance().getReference().child("FoodOrders");

        Query query1 = usersRef1.orderByChild("userName").equalTo(email);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                    foodOrders.add(foodOrder);

                    ListView lv_waiter_food_chosen = findViewById(R.id.lv_waiter_food_chosen);
                    WaiterFoodChosenAdapter waiterFoodChosenAdapter = new WaiterFoodChosenAdapter(getApplicationContext(), foodOrders);
                    lv_waiter_food_chosen.setAdapter(waiterFoodChosenAdapter);
                    lv_waiter_food_chosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
            }

        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // В этом методе можно выполнить какие-то действия при возвращении на активность
        updateOrdered();
        // Например, обновить данные, вызвать методы и т.д.
    }

    public void updateOrdered() {
        ArrayList<FoodOrder> foodOrders = new ArrayList<FoodOrder>();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("FoodOders");

        Query query = usersRef.orderByChild("userName").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                    foodOrders.add(foodOrder);

                    ListView lv_waiter_food_chosen = findViewById(R.id.lv_waiter_food_chosen);
                    WaiterFoodChosenAdapter waiterFoodChosenAdapter = new WaiterFoodChosenAdapter(getApplicationContext(), foodOrders);
                    lv_waiter_food_chosen.setAdapter(waiterFoodChosenAdapter);
                    lv_waiter_food_chosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
            }

        });
    }
}
