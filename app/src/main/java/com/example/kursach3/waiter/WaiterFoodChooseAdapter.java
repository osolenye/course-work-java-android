package com.example.kursach3.waiter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursach3.R;
import com.example.kursach3.models.Food;
import com.example.kursach3.models.FoodOrder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WaiterFoodChooseAdapter extends BaseAdapter {
    Context context;
    ArrayList<Food> foods = new ArrayList<Food>();
    LayoutInflater inflater;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("FoodOrders");


    public WaiterFoodChooseAdapter(Context context, ArrayList<Food> foods) {
        this.context = context;
        this.foods= foods;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return foods.size();
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
        convertView = inflater.inflate(R.layout.lv_waiter_food_choose, null);
        TextView tv_waiter_food_choose = convertView.findViewById(R.id.tv_waiter_food_choose);
        tv_waiter_food_choose.setText(foods.get(position).getFoodName());

        Button btn_waiter_food_choose = convertView.findViewById(R.id.btn_waiter_food_choose);
        btn_waiter_food_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cafeName = foods.get(position).getCafeName();
                String tableName = FoodListActivity.tableName;
                String foodName = foods.get(position).getFoodName();
                String email = WaiterActivity.email;



                String key = myRef.push().getKey();
                FoodOrder foodOrder = new FoodOrder(cafeName, tableName, foodName, email, key);
                myRef.child(key).setValue(foodOrder);
                Toast.makeText(context, "nigga: " + key, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
