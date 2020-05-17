package com.ogada;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Switch;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OGADA.db";


    // Country_Info
    private static final String CountryInfoName="Country_Info";
    private static final String CountryInfoColumn="CREATE TABLE " + CountryInfoName + "("
            + "CountryID NUMBER(3), " + "CountryNameKor VARCHAR(70), "
            + "CountryNameEng VARCHAR(70), " + "CountryFlagImg VARCHAR(40), "
            + "LandingCardImg VARCHAR(40), " +"CardType NUMBER(3)" + ");";

    // Landing_Card
    private static final String LandingCardName="Landing_Card";
    private static final String LandingCardColumn="CREATE TABLE "+ LandingCardName + "("
            + "CountryID NUMBER(3), " + "LastName VARCHAR(20), " + "FirstName VARCHAR(20), "
            + "Nationality VARCHAR(20), " + "Birth VARCHAR(20), " + "Gender VARCHAR(20), "
            + "Job VARCHAR(20), " + "Address VARCHAR(20), " + "Hometown VARCHAR(20), "
            + "PhoneNumber VARCHAR(20), " + "Email VARCHAR(20), " + "PassportNumber VARCHAR(20), "
            + "PassportStart VARCHAR(20), " + "PassportEnd VARCHAR(20), " + "VisaNumber VARCHAR(20), "
            + "VisaStart VARCHAR(20), " + "VisaEnd VARCHAR(20), " + "VisaIssuer VARCHAR(20), "
            + "AirplaneNumber VARCHAR(20), " + "BoardingCity VARCHAR(20), " + "StayDay VARCHAR(20), "
            + "StayAddress VARCHAR(20), " + "Sign VARCHAR(20), " + "TodayDate VARCHAR(20), " + "Others VARCHAR(20)"+");";

    // Passport_Info
    private static final String PassportInfoName="Passport_Info";
    private static final String PassportInfoColumn="CREATE TABLE " + PassportInfoName +"("
            + "PassportNumber VARCHAR(10), " + "LastName VARCHAR(30), " + "FirstName VARCHAR(30), "
            + "Nationality VARCHAR(70), " + "Birth VARCHAR(20), " + "Gender VARCHAR(3), "
            + "IssuerCountry VARCHAR(10), " + "PassportStart VARCHAR(20), " + "PassportEnd VARCHAR(20), "
            + "PassportType VARCHAR(5)" + ");";

    // User_Info
    private static final String UserInfoName="User_Info";
    private static final String UserInfoColumn="CREATE TABLE " + UserInfoName + "("
            + "PassportNumber VARCHAR(10), " + "PhoneNumber VARCHAR(15), "
            + "Email VARCHAR(30), " + "Address VARCHAR(100)" + ");";


    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("CREATE TABLE tableName ( name TEXT, info TEXT);");

        db.execSQL(CountryInfoColumn);
        db.execSQL(LandingCardColumn);
        db.execSQL(PassportInfoColumn);
        db.execSQL(UserInfoColumn);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CountryInfoName);
        db.execSQL("DROP TABLE IF EXISTS "+LandingCardName);
        db.execSQL("DROP TABLE IF EXISTS "+PassportInfoName);
        db.execSQL("DROP TABLE IF EXISTS "+UserInfoName);
        onCreate(db);
    }

    public boolean Insert2Table(SQLiteDatabase db, String TableName, String[] InfoArray){

        String value="";
        try {
            for (int i = 0; i < InfoArray.length; i++) {
                value = value + ("'" + InfoArray[i] + "'");
                if(i!=InfoArray.length-1){
                    value = value + ", ";
                }
            }
            db.execSQL("INSERT INTO " + TableName + " VALUES ("+value+");");
        }catch (Exception e){return false;}

        return true;
    }

    public boolean Delete2Table(SQLiteDatabase db, String TableName, String KeyValue){

        String Country_key = "CountryID";
        String User_key = "PassportNumber";
        String key="";

        switch(TableName){
            case CountryInfoName:
            case LandingCardName:
                key = Country_key;
                break;
            case PassportInfoName:
            case UserInfoName:
                key = User_key;
                break;
        }

        System.out.println("DELETE FROM "+TableName+" WHERE "+key+" = '"+KeyValue+"';");
        db.execSQL("DELETE FROM "+TableName+" WHERE "+key+" = '"+KeyValue+"';");


        return true;
    }

}
