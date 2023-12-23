package com.example.kursach3.waiter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursach3.R;
import com.example.kursach3.models.Table;

import java.util.ArrayList;

public class TablesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Table> tables = new ArrayList<Table>();
    LayoutInflater inflater;

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
        convertView = inflater.inflate(R.layout.activity_lv_tables2, null);
        TextView tv_table_name = convertView.findViewById(R.id.tv_table_name_waiter);
        tv_table_name.setText(tables.get(position).getTableName());
        return convertView;
    }
}
