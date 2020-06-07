package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

        ShowCardList(adapter);

        // 선택시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menu03ListItem item = (menu03ListItem)adapter.getItem(i);
                ShowCard(item.getCountryNameStr(), item.getPassportNameStr());
            }
        });

        Button preBtn = (Button)findViewById(R.id.menu03_1_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public void ShowCardList(menu03_ListAdapter adapter){
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath()+"/OGADA");
        File[] listFiles=dir.listFiles();
        String fileName, extname, tempName;
        String[] filenameSplit=new String[3];
        for(File file: listFiles){
            fileName = file.getName();
            extname = fileName.substring(fileName.length()-4);
            tempName = fileName.substring(0, fileName.lastIndexOf("."));
            if(extname.equals((String)"jpeg")) {
                filenameSplit = tempName.split("_");
                adapter.addItem(filenameSplit[1], filenameSplit[2]);
            }
        }
     }

    public void ShowCard(String CountryNameStr, String PassportNameStr){
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath()+"/OGADA");
        String fileName = dir.getAbsolutePath()+"/OGADA_"+CountryNameStr+"_"+PassportNameStr+".jpeg";

        Intent intent=new Intent(getApplicationContext(), menu03Activity_2.class);
        intent.putExtra("fileName", fileName);
        intent.putExtra("CountryNameStr", CountryNameStr);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
