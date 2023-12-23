package com.example.kursach3.waiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.FoodOrder;
import com.example.kursach3.models.Table;
import com.example.kursach3.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WaiterTableFunctionsActivity extends AppCompatActivity {
    String tableName;
    Intent intent;
    String cafeName;

    FirebaseAuth auth;
    FirebaseUser user;
    Button btn_waiter_add_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_table_functions);

        intent = getIntent();
        tableName = intent.getStringExtra("tableName");
        cafeName = intent.getStringExtra("cafeName");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btn_waiter_add_food = findViewById(R.id.btn_waiter_add_food);



        String email = user.getEmail();

        ArrayList<User> users = new ArrayList<>();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        Query query = usersRef.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
                cafeName = users.get(0).getCafe();

                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
                Query ordersQuery = ordersRef.orderByChild("tableName").equalTo(tableName);
                ordersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<FoodOrder> orders= new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                            orders.add(foodOrder);
                        }

                        ListView lv_orders= findViewById(R.id.lv_waiter_food);
                        WaiterFoodAdapter waiterFoodAdapter = new WaiterFoodAdapter(getApplicationContext(), orders);
                        lv_orders.setAdapter(waiterFoodAdapter);
                        lv_orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
            }
        });


        btn_waiter_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}