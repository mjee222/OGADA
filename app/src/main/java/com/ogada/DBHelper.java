package com.ogada;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "OGADA.db";

    // Country_Info
    private static final String CountryInfoName="Country_Info";
    private static final String CountryInfoColumn="CREATE TABLE " + CountryInfoName + "("
            + "CountryID VARCHAR(3) PRIMARY KEY, " + "CountryNameKor VARCHAR(70), "
            + "CountryNameEng VARCHAR(70), " + "CountryFlagImg VARCHAR(40), "
            + "LandingCardImg VARCHAR(40), " +"CardType VARCHAR(3)" + ");";

    // Landing_Card
    private static final String LandingCardName="Landing_Card";
    private static final String LandingCardColumn="CREATE TABLE "+ LandingCardName + "("
            + "CountryID VARCHAR(3) PRIMARY KEY, " + "LastName VARCHAR(20), " + "FirstName VARCHAR(20), "
            + "Nationality VARCHAR(20), " + "Birth VARCHAR(20), " + "Gender VARCHAR(20), "
            + "Job VARCHAR(20), " + "Address VARCHAR(20), " + "Hometown VARCHAR(20), "
            + "PhoneNumber VARCHAR(20), " + "Email VARCHAR(20), " + "PassportNumber VARCHAR(20), "
            + "PassportStart VARCHAR(20), " + "PassportEnd VARCHAR(20), " + "VisaNumber VARCHAR(20), "
            + "VisaStart VARCHAR(20), " + "VisaEnd VARCHAR(20), " + "VisaIssuer VARCHAR(20), "
            + "AirplaneNumber VARCHAR(20), " + "BoardingCity VARCHAR(20), " + "StayDay VARCHAR(20), "
            + "StayAddress VARCHAR(20), " + "Sign VARCHAR(20), " + "TodayDate VARCHAR(20), " + "Others VARCHAR(20)"+");";
    public static final String[] LandingCardColNames={"CountryID", "LastName", "FirstName", "Nationality", "Birth", "Gender", "Job",
                                                         "Address", "Hometown", "PhoneNumber", "Email", "PassportNumber", "PassportStart", "PassportEnd",
                                                        "VisaNumber", "VisaStart", "VisaEnd", "VisaIssuer", "AirplaneNumber", "BoardingCity", "StayDay",
                                                        "StayAddress", "Sign", "TodayDate", "Others"};

    // Passport_Info
    private static final String PassportInfoName="Passport_Info";
    private static final String PassportInfoColumn="CREATE TABLE " + PassportInfoName +"("
            + "PassportNumber VARCHAR(10) PRIMARY KEY, "  + "LastName VARCHAR(30), "
            + "FirstName VARCHAR(30), " + "Nationality VARCHAR(70), " + "Birth VARCHAR(20), "
            + "Gender VARCHAR(3), " + "IssuerCountry VARCHAR(10), " + "PassportStart VARCHAR(20), "
            + "PassportEnd VARCHAR(20), " + "PassportType VARCHAR(5)" + ");";

    // User_Info
    private static final String UserInfoName="User_Info";
    private static final String UserInfoColumn="CREATE TABLE " + UserInfoName + "("
            + "PassportNumber VARCHAR(10) PRIMARY KEY, " + "Nickname VARCHAR(30), " + "PhoneNumber VARCHAR(15), "
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

        initDatabase(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CountryInfoName);
        db.execSQL("DROP TABLE IF EXISTS "+LandingCardName);
        db.execSQL("DROP TABLE IF EXISTS "+PassportInfoName);
        db.execSQL("DROP TABLE IF EXISTS "+UserInfoName);
        onCreate(db);

        initDatabase(db);

    }

    static public boolean Insert2Table(SQLiteDatabase db, String TableName, String[] InfoArray){

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

    static public boolean Delete2Value(SQLiteDatabase db, String TableName, String KeyValue){

        String Country_key = "CountryID";
        String User_key = "PassportNumber";
        String key="";

        try {
            switch (TableName) {
                case CountryInfoName:
                case LandingCardName:
                    key = Country_key;
                    break;
                case PassportInfoName:
                case UserInfoName:
                    key = User_key;
                    break;
            }

            System.out.println("DELETE FROM " + TableName + " WHERE " + key + " = '" + KeyValue + "';");
            db.execSQL("DELETE FROM " + TableName + " WHERE " + key + " = '" + KeyValue + "';");
        }catch (Exception e){return false;}

        return true;
    }

    public String Select2BlankLandingCardImg(SQLiteDatabase db,  String CountryID){
        String imgpath="";
        Cursor cursor=null;

        String query="SELECT LandingCardImg FROM " + CountryInfoName +" WHERE CountryID='"+CountryID+"';";
        cursor=db.rawQuery(query, null);

        while(cursor.moveToNext()){
            imgpath = cursor.getString(0);
        }
        return imgpath;
    }



    private String getURLForResource(int resId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resId).toString();
    }

    public void initDatabase(SQLiteDatabase db){
        // 초기 데이터
        String [][]CountryArr={
                {"001", "대한민국", "Korea", "@drawable/flag_korea", "@drawable/card_blank_korea", "wid"},
                {"002", "일본", "Japan", "@drawable/flag_japan", "@drawable/card_blank_japan", "wid"},
                {"003", "중국", "china", "@drawable/flag_china", "@drawable/card_blank_china", "wid"},
                {"004", "홍콩", "hongkong", "@drawable/flag_hongkong", "@drawable/card_blank_hongkong", "hei"},
                {"005", "대만", "taiwan", "@drawable/flag_taiwan", "@drawable/card_blank_taiwan", "hei"}
        };

        for(int i=0; i<CountryArr.length; i++){
            Insert2Table(db, CountryInfoName, CountryArr[i]);
        }


        String []Korea_xy={"001","45,85,187,107","223,87,455,106",
                "44,131,186,158","225,128,417,157","491,68,533,106",
                "453,182,565,203","47,180,409,205"," ",
                " "," ","455,131,569,157",
                " ", " ", " ",
                " "," "," ",
                "422,279,551,298", "421,320,561,343", " ",
                "41,226,563,254", "47,364,223,386", " ", "227,324,359,342"};
        Insert2Table(db, LandingCardName, Korea_xy);

        String []Japan_xy={"002","122,89,253,106","269,87,411,103",
                "125,115,249,133","329,115,469,132","492,112,585,135",
                "480,145,586,161","129,146,411,161"," ",
                " "," ","124,170,257,190",
                " ", " ", " ",
                " "," "," ",
                "363,170,425,187", "507,171,595,191", "423,220,594,238",
                "133,248,371,266", " ", "", "177,222,393,239"};
        Insert2Table(db, LandingCardName, Japan_xy);

        String []China_xy={"003","149,70,298,91","401,73,605,91",
                "150,105,298,123","149,170,303,191","508,134,606,154",
                " "," "," ",
                " "," ","402,101,609,123",
                " ", " ", "151,195,303,215",
                " "," ","149,225,299,246",
                "152,255,300,277", " ", " ",
                "151,132,468,153", "488,303,601,331", " ", "477,193,523,215"};
        Insert2Table(db, LandingCardName, China_xy);

        String []Hongkong_xy={"004","46,128,328,145","46,88,293,106",
                "47,207,175,227","192,205,320,227","319,89,337,110",
                " ","48,285,323,304","47,246,320,227",
                " "," ","43,168,173,185",
                " ", " ", " ",
                " "," "," ",
                "46,326,164,343", "210,325,326,344", "209,168,330,186",
                "210,246,325,264", "157,359,325,381", " ", " "};
        Insert2Table(db, LandingCardName, Hongkong_xy);

        String []Taiwan_xy={"005","37,120,147,135","37,142,143,154",
                "211,167,324,185","31,169,184,186","35,198,109,215",
                "240,195,327,215","37,308,265,324"," ",
                " "," ","190,122,321,147",
                " ", " ", "37,278,267,298",
                " "," "," ",
                "130,197,218,216", " ", " ",
                "97,338,299,351", "36,443,201,466", " ", "34,366,186,428"};
        Insert2Table(db, LandingCardName, Taiwan_xy);

    }


}
