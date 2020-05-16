package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class menu01Activity_1 extends AppCompatActivity {

    private ListView listView;
    private  menu01_ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01_1);

        adapter = new menu01_ListAdapter();

        listView = (ListView)findViewById(R.id.menu01_listview01);
        listView.setAdapter(adapter);

        adapter.addItem("대한민국",R.drawable.koreaflag);



    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
