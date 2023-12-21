package com.example.kursach3.owner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursach3.R;
import com.example.kursach3.models.Cafe;
import com.example.kursach3.models.Type;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TypesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Type> types = new ArrayList<Type>();
    LayoutInflater inflater;

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
        TextView tv_cafe_name = (TextView) convertView.findViewById(R.id.tv_type_cafe_name);
        tv_name.setText(types.get(position).getTypeName());
        tv_cafe_name.setText(types.get(position).getCafeName());
        return convertView;
    }
}
