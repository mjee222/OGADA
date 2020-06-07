package com.ogada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ogada.DBHelper.LandingCardColNames;

public class menu01Activity_3 extends AppCompatActivity {
    private static final String LandingCardName="Landing_Card";
    private static final String PassportInfoName="Passport_Info";
    ImageView imageView;

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    private String PassportNumber="", NickName="";
    ArrayList<String> notinPassportInfo_kor = new ArrayList<>(Arrays.asList(new String[]{"직업", "고향", "비자 번호", "비자 발급일", "비자 만료일", "비자 발급처", "항공기 번호", "출발 도시", "머무는 날", "머무는 곳의 주소", "서명", "주소", "방문 목적(visit)"}));
    ArrayList<String> notinPassportInfo_eng = new ArrayList<>(Arrays.asList(new String[]{"Job", "Hometown", "VisaNumber", "VisaStart", "VisaEnd", "VisaIssuer", "AirplaneNumber", "BoardingCity", "StayDay", "StayAddress", "Sign", "Address", "Others"}));
    ArrayList<String> CountryIDNumlist = new ArrayList<>(Arrays.asList(new String[]{"001", "002", "003", "004", "005"}));
    ArrayList<String> CountryID2Name = new ArrayList<>(Arrays.asList(new String[]{"대한민국", "일본", "중국", "홍콩", "대만"}));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01_3);

        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getWritableDatabase();

        Intent intent=getIntent();
        final int resID=intent.getExtras().getInt("resID");
        final String CountryID=intent.getExtras().getString("CountryID");
        PassportNumber=intent.getExtras().getString("PassportNumber");
        NickName=intent.getExtras().getString("NickName");


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
            int PassportIdx=notinPassportInfo_eng.indexOf(dataName);
            if(-1<PassportIdx){
                bm = writeOnDrawable(bm, notinPassportInfo_kor.get(PassportIdx), 50, xy[0], xy[1]);
                continue;
            }
            String data_info=getUserInfo(db, dataName, PassportNumber);
            bm = writeOnDrawable(bm, data_info, 50, xy[0], xy[1]);
        }
        //int temp[]=getXY(db, CountryID, "LastName");
        //int data[]=px2dp(temp[0], temp[1], deviceDpi);
        //int data[]=px2dp(100, 168, deviceDpi);
        //imageView.setImageBitmap(writeOnDrawable(resID, "t", 80, data[0], data[1]));

        imageView.setImageBitmap(bm);

        Button saveBtn = (Button)findViewById(R.id.menu01_3_savebtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap captureView = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath()+"/OGADA");
                String fileName="OGADA_"+NickName+"_"+CountryID2Name.get(CountryIDNumlist.indexOf(CountryID))+".jpeg";
                FileOutputStream fos;
                boolean isGrantStorage = grantExternalStoragePermission();  //퍼미션 허가 여부 확인
                if (isGrantStorage) {
                    try {
                        if(!dir.exists()){
                            dir.mkdirs();
                        }
                        fos = new FileOutputStream(new File(dir, fileName));
                        captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Captured!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }else{
            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
            return true;
        }

    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public String getUserInfo(SQLiteDatabase db, String colname, String PassportNumber){
        String info="";
        Cursor cursor = null;
        String query = "SELECT " + colname + " FROM " + PassportInfoName + " WHERE PassportNumber='" + PassportNumber + "';";
        cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            info = cursor.getString(0);
        }
        return info;
    }

    // 신고서에 필요한 칼럼명 가져와서 리스트로 반환
    // {"칼럼명", "x좌표,y좌표"}
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

    // 필요한 정보 좌표 이름 반환(디비 사용)
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


    // 좌표랑 화면 속 좌표랑 맞춰줌
    public int[] getXY(String xy, int dpi){
        String split_xy[] = xy.split(",");
        int temp_xy[]={Integer.parseInt(split_xy[0]), Integer.parseInt(split_xy[1])};
        int return_value[]={temp_xy[0]*dpi/160, temp_xy[1]*dpi/160+50};
        return return_value;
    }

    //그림 그려줌
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
