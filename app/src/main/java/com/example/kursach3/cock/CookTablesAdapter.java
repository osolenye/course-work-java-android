package com.example.kursach3.cock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursach3.R;
import com.example.kursach3.models.Table;

import java.util.ArrayList;

public class CookTablesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Table> tables = new ArrayList<Table>();
    LayoutInflater inflater;

    @Override
    public int getCount() {
        return tables.size();
    }

    public CookTablesAdapter(Context context, ArrayList<Table> tables) {
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
        convertView = inflater.inflate(R.layout.cook_tables_custom, null);
        TextView tv_table_name = convertView.findViewById(R.id.tv_cook_table_name);
        tv_table_name.setText(tables.get(position).getTableName());
        return convertView;
    }
}
