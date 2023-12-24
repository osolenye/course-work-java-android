package com.example.kursach3.cock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Cafe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

public class SearchCafeActivity extends AppCompatActivity {
    Button cook_btn_search;
    EditText cook_et_search_cafe;
    Button cook_btn_add;
    TextView cook_tv_cafe;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cafe2);



        cook_btn_search = findViewById(R.id.cook_btn_search_cafe);
        cook_et_search_cafe = findViewById(R.id.cook_et_search_cafe);
        cook_btn_add = findViewById(R.id.cook_btn_add);
        cook_tv_cafe = findViewById(R.id.cook_tv_cafe);
        cook_btn_add.setVisibility(View.GONE);
        cook_tv_cafe.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        cook_btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Cafes");

                Query query = usersRef.orderByChild("name").equalTo(cook_et_search_cafe.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Обработка каждой записи
                            Cafe cafe = snapshot.getValue(Cafe.class);
                            cook_tv_cafe.setVisibility(View.VISIBLE);
                            cook_tv_cafe.setText(cafe.getName());
                            cook_btn_add.setVisibility(View.VISIBLE);
                            cook_btn_add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Получение ссылки на вашу базу данных
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Users");

// Создание запроса для поиска записи по определенному полю
                                    Query query = myRef.orderByChild("email").equalTo(user.getEmail());

                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                // Получение найденной записи и изменение другого поля
                                                snapshot.getRef().child("cafe").setValue(cafe.getName(), new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                        if (error != null) {
                                                            // Обработка ошибки, если что-то пошло не так
                                                            Toast.makeText(com.example.kursach3.cock.SearchCafeActivity.this, "error adding cafe", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            // Значение успешно изменено
                                                            Toast.makeText(com.example.kursach3.cock.SearchCafeActivity.this, "successfully added", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Обработка ошибок запроса, если не удалось получить данные
                                        }
                                    });

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(com.example.kursach3.cock.SearchCafeActivity.this, "no cafe", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}