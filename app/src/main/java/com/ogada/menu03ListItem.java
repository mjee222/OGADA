package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class menu03ListItem extends AppCompatActivity {

    private String CountryNameStr ;
    private String PassportNameStr;

    public void setCountryNameStr(String title) {
        CountryNameStr = title ;
    }
    public void setPassportNameStr(String titleeng){
        PassportNameStr=titleeng;
    }

    public String getCountryNameStr() { return this.CountryNameStr ;}
    public String getPassportNameStr(){return this.PassportNameStr;}

}
