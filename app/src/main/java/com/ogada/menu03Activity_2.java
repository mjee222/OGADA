package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class menu03Activity_2 extends AppCompatActivity {

    String fileName, CountryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu03_2);

        Intent intent=getIntent();
        fileName=intent.getExtras().getString("fileName");
        CountryName=intent.getExtras().getString("CountryNameStr");

        TextView CountryNameTextView = (TextView)findViewById(R.id.menu03_2_text01);
        CountryNameTextView.setText(CountryName+"님의 "+CountryName+" 신고서");

        final File imgFile = new File(fileName);
        System.out.println(imgFile.getAbsolutePath());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.menu03_2_imageview);
            myImage.setImageBitmap(myBitmap);
        }

        Button preBtn = (Button)findViewById(R.id.menu03_2_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button delBtn = (Button)findViewById(R.id.menu03_2_removebtn);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(menu03Activity_2.this);

                oDialog.setMessage("작성한 신고서를 삭제하시겠습니까?")
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

                                imgFile.delete();
                                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), menu03Activity_1.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                        .show();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
