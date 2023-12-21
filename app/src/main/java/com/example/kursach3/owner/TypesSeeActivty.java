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
import com.example.kursach3.models.Cafe;
import com.example.kursach3.models.Type;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TypesSeeActivty extends AppCompatActivity {
    Button btn_create_type_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_see_activty);

        btn_create_type_activity = findViewById(R.id.btn_create_type_activity);
        Intent intent = getIntent();
        String cafeName = intent.getStringExtra("cafeName");




        ArrayList<Type> types= new ArrayList<Type>();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Types");

        Query query = usersRef.orderByChild("cafeName").equalTo(cafeName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Обработка каждой записи
                    Type type = snapshot.getValue(Type.class);
                    types.add(type);
                    // user содержит данные пользователя, где поле fieldName равно desiredValue
                }


                ListView lv_types = findViewById(R.id.lv_types);
                TypesAdapter typesAdapter = new TypesAdapter(getApplicationContext(), types);
                lv_types.setAdapter(typesAdapter);
                lv_types.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(TypesSeeActivty.this, position + " " + types.get(position).getCafeName(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(TypesSeeActivty.this, Ty.class);
//                        intent.putExtra("cafeName", types.get(position).getCafeName());
//                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок, если они возникли
            }
        });



        btn_create_type_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypesSeeActivty.this, CreateTypeActivity.class);
                intent.putExtra("cafeName", cafeName);
                startActivity(intent);
            }
        });
    }
}