package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu01_3);

        // 스플래시 화면 관련 코드 (3초)
        Intent splash_intent = new Intent(this, SplashActivity.class);
        startActivity(splash_intent);
    }
}
