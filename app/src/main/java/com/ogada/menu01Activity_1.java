package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class menu01Activity_1 extends AppCompatActivity {

    private static final String CountryInfoName="Country_Info";

    private ListView listView;
    private  menu01_ListAdapter adapter;

    // db
    DBHelper dbHelper;
    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01_1);

        adapter = new menu01_ListAdapter();

        listView = (ListView)findViewById(R.id.menu01_listview01);
        listView.setAdapter(adapter);


        //db
        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getReadableDatabase();
        ShowCountryList(db, adapter);


        // 선택시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menu01ListItem item = (menu01ListItem)adapter.getItem(i);
                ShowBlankLandingCard(item.getCountryTitle(), item.getCountryTitleEng());
                // Toast.makeText(view.getContext(), item.getCountryTitle(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void ShowCountryList(SQLiteDatabase db, menu01_ListAdapter adapter){
        Cursor cursor=null;
        String query="SELECT CountryID, CountryNameKor, CountryNameEng, CountryFlagImg FROM "+ CountryInfoName+";";
        cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            String resName = cursor.getString(3);
            String packName = this.getPackageName();
            int resID = getResources().getIdentifier(resName, "drawable", packName);
            adapter.addItem(cursor.getString(1), cursor.getString(2), resID);
            System.out.println(resID);
        }

    }

    public void ShowBlankLandingCard(String CountryNameKor, String CountryNameEng){
        Intent intent=new Intent(getApplicationContext(), menu01Activity_2.class);
        intent.putExtra("CountryNameKor", CountryNameKor);
        intent.putExtra("CountryNameEng", CountryNameEng);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }



    @Override
    public void finish(){
        super.finish();
        db.close();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
