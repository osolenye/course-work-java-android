package com.example.kursach3.cock;

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
import com.example.kursach3.models.FoodOrder;
import com.example.kursach3.waiter.FoodListActivity;
import com.example.kursach3.waiter.SearchCafeActivity;
import com.example.kursach3.waiter.WaiterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class CookOrderedFoodAdapter extends BaseAdapter {
    String key;

    Context context;
    ArrayList<FoodOrder> orderedFoods = new ArrayList<FoodOrder>();
    LayoutInflater inflater;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("FoodOrders");

    public CookOrderedFoodAdapter(Context context, ArrayList<FoodOrder> orderedFoods) {
        this.context = context;
        this.orderedFoods = orderedFoods;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderedFoods.size();
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
        convertView = inflater.inflate(R.layout.cook_ordered_foods_custom, null);
        TextView tv_cook_ordered_food_name = convertView.findViewById(R.id.tv_cook_ordered_food_name);
        tv_cook_ordered_food_name.setText(orderedFoods.get(position).getFoodName());
        key = orderedFoods.get(position).getKey();

        Button btn_cook_ordered_food_start = convertView.findViewById(R.id.btn_cook_ordered_food_start);
        btn_cook_ordered_food_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("FoodOrders");

                // Создание запроса для поиска записи по определенному полю
                Query query = myRef.orderByChild("key").equalTo(key);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Получение найденной записи и изменение другого поля
                            snapshot.getRef().child("status").setValue("yesCooking", new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if (error != null) {
                                        // Обработка ошибки, если что-то пошло не так
                                    } else {
                                        // Значение успешно изменено
                                    }
                                }
                            }); // Semicolon added here
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Обработка ошибок запроса, если не удалось получить данные
                    }
                });
            }
        });

        return convertView;
    }
}
