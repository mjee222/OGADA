package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class menu01Activity_2 extends AppCompatActivity {

    private static final String CountryInfoName="Country_Info";
    DBHelper dbHelper;
    SQLiteDatabase db = null;
    private static String CountryID="";
    String PassportNumber="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01_2);

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

    @Override
    public void finish(){
        super.finish();
        db.close();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
