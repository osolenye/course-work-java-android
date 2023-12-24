package com.example.kursach3.waiter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationAction;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursach3.R;
import com.example.kursach3.models.Type;

import java.util.ArrayList;

public class WaiterTypeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Type> types = new ArrayList<Type>();
    LayoutInflater inflater;

    public WaiterTypeAdapter(Context context, ArrayList<Type> types) {
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
        convertView = inflater.inflate(R.layout.lv_waiter_type_choose, null);
        TextView tv_type_name = (TextView) convertView.findViewById(R.id.tv_waiter_type_choose);
        tv_type_name.setText(types.get(position).getTypeName());
        return convertView;
    }
}
