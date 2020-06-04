package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class menu01ListItem extends AppCompatActivity {

    private int iconDrawable ;
    private String titleStr ;
    private String titleEndStr;

    public void setCountryTitle(String title) {
        titleStr = title ;
    }
    public void setCountryTitleEng(String titleeng){
        titleEndStr=titleeng;
    }
    public void setIcon(int icon) {
        iconDrawable = icon ;
    }

    public int getIcon() {
        return this.iconDrawable ;
    }

    public String getCountryTitleEng(){
        return this.titleEndStr;
    }

    public String getCountryTitle() {
        return this.titleStr ;
    }
}
