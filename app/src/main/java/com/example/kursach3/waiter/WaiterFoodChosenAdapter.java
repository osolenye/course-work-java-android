package com.example.kursach3.waiter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kursach3.R;
import com.example.kursach3.models.Food;
import com.example.kursach3.models.FoodOrder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WaiterFoodChosenAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodOrder> orderedFoods = new ArrayList<FoodOrder>();
    LayoutInflater inflater;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("FoodOrders");

    public WaiterFoodChosenAdapter(Context context, ArrayList<FoodOrder> orderedFoods) {
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
        convertView = inflater.inflate(R.layout.lv_waiter_food_chosen, null);
        TextView tv_waiter_food_chosen = convertView.findViewById(R.id.tv_waiter_food_chosen);
        tv_waiter_food_chosen.setText(orderedFoods.get(position).getFoodName());

        Button btn_waiter_food_chosen_delete = convertView.findViewById(R.id.btn_waiter_food_chosen_delete);
        btn_waiter_food_chosen_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cafeName = orderedFoods.get(position).getCafeName();
                String foodName = orderedFoods.get(position).getFoodName();
                String tableName = FoodListActivity.tableName;
                String email = WaiterActivity.email;

                // Удаление из списка orderedFoods
                orderedFoods.remove(position);
                notifyDataSetChanged(); // Обновление списка после удаления

                // Удаление из базы данных
                Query query = myRef.orderByChild("foodName").equalTo(foodName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FoodOrder foodOrder = snapshot.getValue(FoodOrder.class);
                            if (
                                foodOrder.getTableName().equals(tableName)&&
                                foodOrder.getUserName().equals(email)) {
                                snapshot.getRef().removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Успешное удаление из базы данных
                                                // Дополнительная обработка при необходимости
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
