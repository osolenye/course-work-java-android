package com.example.kursach3.waiter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Table;
import com.example.kursach3.models.Type;
import com.example.kursach3.models.User;
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

//public class WaiterActivity extends AppCompatActivity {
//    String cafeName;
//    ListView lv_table;
//
//    FirebaseUser user;
//    FirebaseAuth auth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_waiter);
//
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//
//        String email = user.getEmail();
//
//
//        ArrayList<User> users = new ArrayList<User>();
//
//
//
//        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
//
//        Query query = usersRef.orderByChild("email").equalTo(email);
//
//
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    User user = snapshot.getValue(User.class);
//                    users.add(user);
//                }
//                cafeName = users.get(0).getCafe();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Обработка ошибок, если они возникли
//            }
//        });
//
//
//
//        ArrayList<Table> tables = new ArrayList<Table>();
//
//        DatabaseReference usersRef1 = FirebaseDatabase.getInstance().getReference().child("Tables");
//
//        Query query1 = usersRef1.orderByChild("cafeName").equalTo(cafeName);
//        query1.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    // Обработка каждой записи
//                    Table table = snapshot.getValue(Table.class);
//                    tables.add(table);
//                }
//
//
//
//                ListView lv_tables = findViewById(R.id.lv_tables);
//                TablesAdapter tablesAdapter = new TablesAdapter(getApplicationContext(), tables);
//                lv_tables.setAdapter(tablesAdapter);
//                lv_tables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(WaiterActivity.this, position + " " + tables.get(position).getCafeName(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Обработка ошибок, если они возникли
//            }
//        });
//    }
//}





public class WaiterActivity extends AppCompatActivity {
    String cafeName;
    ListView lv_table;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

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

                DatabaseReference tablesRef = FirebaseDatabase.getInstance().getReference().child("Tables");
                Query tablesQuery = tablesRef.orderByChild("cafeName").equalTo(cafeName);
                tablesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Table> tables = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Table table = snapshot.getValue(Table.class);
                            tables.add(table);
                        }

                        ListView lv_tables = findViewById(R.id.lv_tables);
                        TablesAdapter tablesAdapter = new TablesAdapter(getApplicationContext(), tables);
                        lv_tables.setAdapter(tablesAdapter);
                        lv_tables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(WaiterActivity.this, position + " " + tables.get(position).getCafeName(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WaiterActivity.this, WaiterTableFunctionsActivity.class);
                                intent.putExtra("tableName", tables.get(position).getTableName());
                                intent.putExtra("cafeName", tables.get(position).getCafeName());
                                startActivity(intent);
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
    }
}
