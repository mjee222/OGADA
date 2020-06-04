package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static com.ogada.DBHelper.LandingCardColNames;

public class menu01Activity_3 extends AppCompatActivity {
    private static final String LandingCardName="Landing_Card";
    ImageView imageView;

    DBHelper dbHelper;
    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01_3);

        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getWritableDatabase();

        Intent intent=getIntent();
        final int resID=intent.getExtras().getInt("resID");
        final String CountryID=intent.getExtras().getString("CountryID");

        Button preBtn = (Button) findViewById(R.id.menu01_3_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int deviceDpi = metrics.densityDpi;

        Bitmap bm = BitmapFactory.decodeResource(getResources(), resID).copy(Bitmap.Config.ARGB_8888, true);
        imageView = (ImageView)findViewById(R.id.menu01_3_imageview);

        List<String[]> data_arr = getItemList(db, CountryID);
        for(int i=0; i<data_arr.size(); i++){
            int xy[] =getXY(data_arr.get(i)[1], deviceDpi);
            String dataName=data_arr.get(i)[0];
            bm = writeOnDrawable(bm, "t", 80, xy[0], xy[1]);
        }
        //int temp[]=getXY(db, CountryID, "LastName");
        //int data[]=px2dp(temp[0], temp[1], deviceDpi);
        //int data[]=px2dp(100, 168, deviceDpi);
        //imageView.setImageBitmap(writeOnDrawable(resID, "t", 80, data[0], data[1]));

        imageView.setImageBitmap(bm);

    }



    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public List<String[]> getItemList(SQLiteDatabase db, String CountryID) {
        String ItemName = null, value = null;
        Cursor cursor = null;
        String[] namelist = LandingCardColNames;
        List<String[]> data_arr=new ArrayList<String[]>();

        for (int i = 1; i < 25; i++) {
            String xydata = getNameXY(db, CountryID, namelist[i]);
            if(xydata.equals("-1")){
                continue;
            }else{
                String[] tmp={namelist[i], xydata};
                data_arr.add(tmp);
            }
        }
        return data_arr;
    }

    public String getNameXY(SQLiteDatabase db, String CountryID, String ItemName){
        Cursor cursor = null;
        String query = "SELECT " + ItemName + " FROM " + LandingCardName + " WHERE CountryID='" + CountryID + "';";
        cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String data = cursor.getString(0);
            data=data.trim();
            if (!data.replace(" ", "").equals("") || !data.isEmpty()) {
                return data;
            }
        }
        return "-1";
    }


    public int[] getXY(String xy, int dpi){
        String split_xy[] = xy.split(",");
        int temp_xy[]={Integer.parseInt(split_xy[0]), Integer.parseInt(split_xy[1])};
        int return_value[]={temp_xy[0]*dpi/160, temp_xy[1]*dpi/160+50};
        return return_value;
    }

    public Bitmap writeOnDrawable(Bitmap bm, String text, int TextSize, int x, int y){


        //BitmapFactory.decodeResource(getResources(),R.drawable.pin_l3x);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(TextSize);
        paint.setTextAlign(Paint.Align.LEFT);

        Canvas canvas = new Canvas(bm);
        //canvas.drawText(text, 100, bm.getHeight()/2, paint);
        canvas.drawText(text,x, y, paint);

        return bm;
    }



    // 이전, 다음 버튼


}
