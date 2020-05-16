package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class menu03Activity_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu03_2);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
