package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;

public class menu03Activity_1 extends AppCompatActivity {

    private static final String PassportInfoName="Passport_Info";
    private static final String UserInfoName="User_Info";

    private ListView listView;
    private  menu03_ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu03_1);

        adapter = new menu03_ListAdapter();

        listView = (ListView)findViewById(R.id.menu03_listview01);
        listView.setAdapter(adapter);


        // 선택시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menu03ListItem item = (menu03ListItem)adapter.getItem(i);
                ShowCard(item.getCountryNameStr(), item.getPassportNameStr());
            }
        });
    }


    public void ShowCardList(menu03_ListAdapter adapter){
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath()+"/OGADA");
        File[] listFiles=dir.listFiles();
        String fileName, extname;
        String[] filenameSplit=new String[3];
        for(File file: listFiles){
            fileName = file.getName();
            extname = fileName.substring(fileName.length()-3);
            if(extname.equals((String)"jpeg"))
                filenameSplit=fileName.split("_");
                adapter.addItem(filenameSplit[1], filenameSplit[2]);
        }
     }

    public void ShowCard(String CountryNameStr, String PassportNameStr){
        Intent intent=new Intent(getApplicationContext(), menu03Activity_2.class);
        String fileName = "/OGADA"+PassportNameStr+"_"+CountryNameStr+".jpeg";
        intent.putExtra("fileName", fileName);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
