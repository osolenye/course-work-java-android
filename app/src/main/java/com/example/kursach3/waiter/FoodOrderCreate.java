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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_create);

        intent = getIntent();
        tableName = intent.getStringExtra("tableName");
        cafeName = intent.getStringExtra("cafeName");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        ArrayList<Type> types= new ArrayList<Type>();

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
    }
}