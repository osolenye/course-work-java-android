package com.example.kursach3.waiter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.kursach3.R;

public class WaiterActivity extends AppCompatActivity {
    ListView lv_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);
    }
}