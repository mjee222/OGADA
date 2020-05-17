package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //////////////// 필요 변수 선언
    /// 메뉴
    ImageButton menu01, menu02, menu03, menu04;

    /// DB
    private static final String CountryInfoName="Country_Info";
    private static final String LandingCardName="Landing_Card";
    private static final String PassportInfoName="Passport_Info";
    private static final String UserInfoName="User_Info";

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 스플래시 화면 관련 코드 (3초)
        Intent splash_intent = new Intent(this, SplashActivity.class);
        startActivity(splash_intent);

        //메뉴 이동
         menu01 = (ImageButton)findViewById(R.id.main_menu01_btn);
         menu02 = (ImageButton)findViewById(R.id.main_menu02_btn);
         menu03 = (ImageButton)findViewById(R.id.main_menu03_btn);
         menu04 = (ImageButton)findViewById(R.id.main_menu04_btn);

        menu01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), menu01Activity_1.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        menu02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), menu02Activity_1.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        menu03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), menu03Activity_1.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        menu04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), menu04Activity_1.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });




        //////////////////////////// DB 관리
        dbHelper = new DBHelper(this, 2);
        db = dbHelper.getWritableDatabase();
        String[] test = {"gdfsd","sfsdr","asdf@ASDF.com","asdf"};
        dbHelper.Delete2Table(db, UserInfoName, "gdfsd");
        db.close();


    }

}
