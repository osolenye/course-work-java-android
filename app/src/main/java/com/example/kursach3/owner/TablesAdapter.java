package com.example.kursach3.owner;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kursach3.R;
import com.example.kursach3.models.FoodOrder;
import com.example.kursach3.models.Table;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class TablesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Table> tables = new ArrayList<Table>();
    LayoutInflater inflater;
    String key;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Tables");
    @Override
    public int getCount() {
        return tables.size();
    }

    public TablesAdapter(Context context, ArrayList<Table> tables) {
        this.context = context;
        this.tables = tables;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_lv_tables, null);

        key = tables.get(position).getKey();

        TextView tv_table_name = convertView.findViewById(R.id.tv_table_name);
        tv_table_name.setText(tables.get(position).getTableName());

        Button btn_owner_table_delete = convertView.findViewById(R.id.btn_owner_table_delete);
        btn_owner_table_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tables.remove(position);
                notifyDataSetChanged(); // Обновление списка после удаления


                // Создание запроса для поиска записи по определенному полю
                Query query = myRef.orderByChild("key").equalTo(key);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                            snapshot.getRef().removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Успешное удаление из базы данных
                                            // Дополнительная обработка при необходимости
                                            Toast.makeText(context, "deleted a table with key: " + key, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Обработка ошибок при удалении из базы данных
                                            // При необходимости можно добавить дополнительные действия
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Обработка ошибок при запросе к базе данных
                    }
                });
            }
        });
        return convertView;
    }
}
