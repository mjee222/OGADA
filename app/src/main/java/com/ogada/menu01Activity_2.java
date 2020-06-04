package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class menu01Activity_2 extends AppCompatActivity {

    private static final String CountryInfoName="Country_Info";
    DBHelper dbHelper;
    SQLiteDatabase db = null;
    private static String CountryID="";
    String PassportNumber="";

    private Activity activity;
    private View view;
    public static menu01_ListAdapter listviewadapter;
    public static ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01_2);
        activity = this;
        Intent intent=getIntent();
        String CountryNameKor=intent.getExtras().getString("CountryNameKor");
        String CountryNameEng=intent.getExtras().getString("CountryNameEng");

        TextView CountryNameTextView=(TextView)findViewById(R.id.menu01_2_text02);
        CountryNameTextView.setText(CountryNameKor);

        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getReadableDatabase();

        ImageView CardImgView=(ImageView)findViewById(R.id.menu01_2_imageview);
        final int resID = showBlankLandingCard(db, CountryNameEng, CardImgView);

        Button goBtn = (Button)findViewById(R.id.menu01_2_gobtn);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                Intent intent=new Intent(getApplicationContext(), menu01Activity_3.class);
                intent.putExtra("resID", resID);
                intent.putExtra("CountryID", CountryID);
                intent.putExtra("PassportNumber", PassportNumber);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        Button preBtn = (Button)findViewById(R.id.menu01_2_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public int showBlankLandingCard(SQLiteDatabase db, String CountrynameEng, ImageView imgV){
        Cursor cursor=null;
        String query="SELECT LandingCardImg, CardType, CountryID FROM "+ CountryInfoName+" WHERE CountryNameEng='"+ CountrynameEng+"';";
        cursor = db.rawQuery(query, null);
        int resID=-1;

        while(cursor.moveToNext()){
            String resName = cursor.getString(0);
            String packName = this.getPackageName();
            resID = getResources().getIdentifier(resName, "drawable", packName);
            imgV.setImageResource(resID);
            CountryID=cursor.getString(2);
        }

        return resID;
    }

    public void showDialog(){
        // 뷰 호출
        view = activity.getLayoutInflater().inflate(R.layout.activity_menu01_select_passport, null);
        // 해당 뷰에 리스트뷰 호출
        listview = (ListView)view.findViewById(R.id.menu01_select_list01);
        // 리스트뷰에 어뎁터 설정
        listview.setAdapter(listviewadapter);

        // 다이얼로그 생성
        AlertDialog.Builder listViewDialog = new AlertDialog.Builder(activity);
        // 리스트뷰 설정된 레이아웃
        listViewDialog.setView(view);
        // 확인버튼
        listViewDialog.setPositiveButton("확인", null);
        // 타이틀
        listViewDialog.setTitle("ListView DiaLog");
        // 다이얼로그 보기
        listViewDialog.show();
    }

    @Override
    public void finish(){
        super.finish();
        db.close();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
