package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class menu04Activity_1 extends AppCompatActivity {


    private static final String PassportInfoName="Passport_Info";
    private static final String UserInfoName="User_Info";

    private ListView listView;
    private  menu01_ListAdapter adapter;

    // db
    DBHelper dbHelper;
    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu04_1);

        adapter = new menu01_ListAdapter();

        listView = (ListView)findViewById(R.id.menu04_listview01);
        listView.setAdapter(adapter);


        //db
        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getReadableDatabase();
        ShowPassportList(db, adapter);


        // 선택시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menu01ListItem item = (menu01ListItem)adapter.getItem(i);
                ShowPassportList(item.getCountryTitleEng(), item.getCountryTitle());
            }
        });
    }

    public void ShowPassportList(SQLiteDatabase db, menu01_ListAdapter adapter){
        Cursor cursor=null;
        String query="SELECT PassportNumber, Nickname FROM "+ UserInfoName+";";
        //String query="SELECT PassportNumber, FirstName FROM "+ PassportInfoName+";";
        cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            adapter.addItem(cursor.getString(1), cursor.getString(0), R.drawable.flag_korea);
        }

    }

    public void ShowPassportList(String PassportNumber, String Nickname){
        Intent intent=new Intent(getApplicationContext(), menu04Activity_2.class);
        intent.putExtra("PassportNumber", PassportNumber);
        intent.putExtra("NickName", Nickname);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
