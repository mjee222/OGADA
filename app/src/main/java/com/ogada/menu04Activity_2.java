package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class menu04Activity_2 extends AppCompatActivity {

    private static final String PassportInfoName="Passport_Info";
    private static final int PassportInfoNum=10;
    private static final String UserInfoName="User_Info";
    private static final int UserInfoNum=5;

    // db
    DBHelper dbHelper;
    SQLiteDatabase db = null;

    TextView text01, text02, text03,text04, text05, text06, text07, text08, text09, text10, text11, text12, text13, text14;
    String PassportNumber, NickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu04_2);

        Intent intent=getIntent();
        PassportNumber=intent.getExtras().getString("PassportNumber");
        NickName=intent.getExtras().getString("NickName");

        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getWritableDatabase();


        Button preBtn = (Button) findViewById(R.id.menu04_2_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Button removeBtn = (Button)findViewById(R.id.menu04_2_removebtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder oDialog = new AlertDialog.Builder(menu04Activity_2.this);

                oDialog.setMessage(NickName+" 여권 정보를 삭제하시겠습니까?")
                        .setTitle("OGADA")
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton("예", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                DBHelper.Delete2Value(db, PassportInfoName, PassportNumber);
                                DBHelper.Delete2Value(db, UserInfoName, PassportNumber);
                                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), menu04Activity_1.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                        .show();

            }
        });

        text01=(TextView)findViewById(R.id.menu04_2_text01);
        text01.setText(NickName);

        String PassportInfo_StringArr[]=getInfo(db, PassportInfoName, PassportNumber, PassportInfoNum);
        String UserInfo_StringArr[]=getInfo(db, UserInfoName, PassportNumber, UserInfoNum);

        text02=(TextView)findViewById(R.id.menu04_2_text02);
        text03=(TextView)findViewById(R.id.menu04_2_text03);
        text04=(TextView)findViewById(R.id.menu04_2_text04);
        text05=(TextView)findViewById(R.id.menu04_2_text05);
        text06=(TextView)findViewById(R.id.menu04_2_text06);
        text07=(TextView)findViewById(R.id.menu04_2_text07);
        text08=(TextView)findViewById(R.id.menu04_2_text08);
        text09=(TextView)findViewById(R.id.menu04_2_text09);
        text10=(TextView)findViewById(R.id.menu04_2_text10);
        text11=(TextView)findViewById(R.id.menu04_2_text11);
        text12=(TextView)findViewById(R.id.menu04_2_text12);
        text13=(TextView)findViewById(R.id.menu04_2_text13);
        text14=(TextView)findViewById(R.id.menu04_2_text14);

        text02.setText(PassportInfo_StringArr[0]);
        text03.setText(PassportInfo_StringArr[1]);
        text04.setText(PassportInfo_StringArr[2]);
        text05.setText(PassportInfo_StringArr[3]);
        text06.setText(PassportInfo_StringArr[4]);
        text07.setText(PassportInfo_StringArr[5]);
        text08.setText(PassportInfo_StringArr[6]);
        text09.setText(PassportInfo_StringArr[7]);
        text10.setText(PassportInfo_StringArr[8]);
        text11.setText(PassportInfo_StringArr[9]);

        text12.setText(UserInfo_StringArr[2]);
        text13.setText(UserInfo_StringArr[3]);
        text14.setText(UserInfo_StringArr[4]);

    }


    public String[] getInfo(SQLiteDatabase db, String dbName, String PassportNumber, int col_num){
        Cursor cursor=null;
        String query="SELECT * FROM "+ dbName+" WHERE PassportNumber='"+ PassportNumber+"';";
        System.out.println(query);
        cursor = db.rawQuery(query, null);
        String[] result=new String[col_num];
        while(cursor.moveToNext()){
            for(int i=0; i<col_num; i++) {
                result[i]=cursor.getString(i);
                System.out.println(result[i]);
            }
        }
        return result;
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
