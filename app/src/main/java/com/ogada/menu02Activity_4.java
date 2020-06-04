package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class menu02Activity_4 extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    private static final String UserInfoName="User_Info";

    EditText NickName, PhoneNum, Email, Address;
    String PassportNumber_data, NickName_data, PhoneNum_data, Email_data, Address_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu02_4);


        Intent intent=getIntent();
        PassportNumber_data=intent.getExtras().getString("PassportNumber");

        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getWritableDatabase();

        Button preBtn = (Button)findViewById(R.id.menu02_4_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        NickName = (EditText) findViewById(R.id.menu02_4_edittext03);
        PhoneNum = (EditText) findViewById(R.id.menu02_4_edittext04);
        Email = (EditText) findViewById(R.id.menu02_4_edittext05);
        Address = (EditText) findViewById(R.id.menu02_4_edittext06);

        Button goBtn = (Button) findViewById(R.id.menu02_4_gobtn);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NickName_data=NickName.getText().toString();
                PhoneNum_data=PhoneNum.getText().toString();
                Email_data=Email.getText().toString();
                Address_data=Address.getText().toString();

                String[] dataarr={PassportNumber_data, NickName_data, PhoneNum_data, Email_data, Address_data};
                DBHelper.Insert2Table(db,UserInfoName, dataarr);

                AlertDialog.Builder builder = new AlertDialog.Builder(menu02Activity_4.this);
                builder.setTitle("OGADA").setMessage("여권 정보 등록 완료!");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


    }


    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
