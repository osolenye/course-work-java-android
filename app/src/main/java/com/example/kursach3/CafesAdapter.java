package com.example.kursach3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursach3.models.Cafe;

import java.util.ArrayList;
import java.util.Base64;

public class CafesAdapter extends BaseAdapter {

    Context context;
    ArrayList<Cafe> cafes = new ArrayList<Cafe>();
    LayoutInflater inflater;

    public CafesAdapter(Context context, ArrayList<Cafe> cafes) {
        this.context = context;
        this.cafes = cafes;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cafes.size();
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
        convertView = inflater.inflate(R.layout.activity_lv_cafes, null);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name_lv_cafes);
        TextView tv_email = (TextView) convertView.findViewById(R.id.tv_email_lv_cafes);
        tv_name.setText(cafes.get(position).getName());
        tv_email.setText(cafes.get(position).getEmail());
        return convertView;
    }
}
