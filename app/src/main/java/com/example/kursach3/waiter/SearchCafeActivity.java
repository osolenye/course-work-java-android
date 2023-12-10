package com.example.kursach3.waiter;

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
import com.example.kursach3.models.User;
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
    Button btn_search;
    EditText et_search_cafe;
    Button btn_add;
    TextView tv_cafe;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cafe);

        btn_search = findViewById(R.id.btn_search_cafe);
        et_search_cafe = findViewById(R.id.et_search_cafe);
        btn_add = findViewById(R.id.btn_add);
        tv_cafe = findViewById(R.id.tv_cafe);
        btn_add.setVisibility(View.GONE);
        tv_cafe.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();




        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Cafes");

                Query query = usersRef.orderByChild("name").equalTo(et_search_cafe.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Обработка каждой записи
                            Cafe cafe = snapshot.getValue(Cafe.class);
                            tv_cafe.setVisibility(View.VISIBLE);
                            tv_cafe.setText(cafe.getName());
                            btn_add.setVisibility(View.VISIBLE);
                            btn_add.setOnClickListener(new View.OnClickListener() {
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
                                                            Toast.makeText(SearchCafeActivity.this, "error adding cafe", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            // Значение успешно изменено
                                                            Toast.makeText(SearchCafeActivity.this, "successfully added", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SearchCafeActivity.this, "no cafe", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}