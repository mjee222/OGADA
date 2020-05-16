package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class menu04Activity_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu04_1);
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
