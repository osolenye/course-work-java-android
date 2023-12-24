package com.example.kursach3.owner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kursach3.R;
import com.example.kursach3.models.Cafe;
import com.example.kursach3.models.FoodOrder;
import com.example.kursach3.models.Type;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TypesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Type> types = new ArrayList<Type>();
    LayoutInflater inflater;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Types");
    String key;

    public TypesAdapter(Context context, ArrayList<Type> types) {
        this.context = context;
        this.types = types;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return types.size();
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
        convertView = inflater.inflate(R.layout.activity_lv_types, null);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_type_name);
        tv_name.setText(types.get(position).getTypeName());

        key = types.get(position).getKey();

        Button btn_owner_type_delete = convertView.findViewById(R.id.btn_owner_type_delete);
        btn_owner_type_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                types.remove(position);
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
                                            Toast.makeText(context, "deleted a cafe with key: " + key, Toast.LENGTH_SHORT).show();
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
