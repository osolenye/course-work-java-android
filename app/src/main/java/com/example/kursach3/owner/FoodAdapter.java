package com.example.kursach3.owner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursach3.R;
import com.example.kursach3.models.Food;
import com.example.kursach3.models.Type;

import java.util.ArrayList;

public class FoodAdapter extends BaseAdapter {
    Context context;
    ArrayList<Food> foods = new ArrayList<Food>();
    LayoutInflater inflater;

    public FoodAdapter(Context context, ArrayList<Food> foods) {
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
        convertView = inflater.inflate(R.layout.activity_lv_food, null);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_food_name);
        tv_name.setText(foods.get(position).getFoodName());
        return convertView;
    }
}
