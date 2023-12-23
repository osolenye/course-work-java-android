package com.example.kursach3.waiter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursach3.R;
import com.example.kursach3.models.Food;
import com.example.kursach3.models.FoodOrder;


import java.util.ArrayList;

public class WaiterFoodAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodOrder> foods = new ArrayList<FoodOrder>();
    LayoutInflater inflater;

    public WaiterFoodAdapter(Context context, ArrayList<FoodOrder> foods) {
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
        convertView = inflater.inflate(R.layout.activity_waiter_food_custom, null);
        TextView tv_waiter_food_name = convertView.findViewById(R.id.tv_waiter_food_name);
        tv_waiter_food_name.setText(foods.get(position).getFoodName());
        return convertView;
    }
}
