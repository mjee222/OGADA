package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class menu02Activity_3 extends AppCompatActivity {

    DatePickerDialog Date_dialog;
    DBHelper dbHelper;
    SQLiteDatabase db = null;

    String PassportNumber_data, LastName_data, FirstName_data, Nationality_data, Birth_data, Gender_data, IssuerCountry_data, PassportStart_data, PassportEnd_data, PassportType_data;

    long now;
    Date TodayDate;
    SimpleDateFormat weekdayFormat, dayFormat, monthFormat, yearFormat;
    int TweekDay, Tyear, Tmonth, Tday;
    String passport_start_date;
    int[] dateint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu02_3);


        TodayDate = Calendar.getInstance().getTime();
        dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        Tyear = Integer.parseInt(yearFormat.format(TodayDate));
        Tmonth = Integer.parseInt(monthFormat.format(TodayDate));
        Tday = Integer.parseInt(dayFormat.format(TodayDate));

        final TextView PassportStart = (TextView) findViewById(R.id.menu02_3_edittext10);
        final TextView PassportEnd = (TextView) findViewById(R.id.menu02_3_edittext11);

        final TextView Birth = (TextView) findViewById(R.id.menu02_3_edittext07);
        Birth.setText((Tyear-20) + "년 " + Tmonth + "월 " + Tday +"일");
        PassportStart.setText(Tyear + "년 " + Tmonth + "월 " + Tday +"일");
        passport_start_date=PassportStart.getText().toString();
        dateint=date2int(passport_start_date);
        PassportEnd.setText(dateint[0]+10 + "년 " + dateint[1] + "월 " + dateint[2] +"일");


        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getWritableDatabase();

        final EditText PassportNumber = (EditText) findViewById(R.id.menu02_3_edittext03);

        //성
        final EditText LastName = (EditText) findViewById(R.id.menu02_3_edittext04);

        // 이름
        final EditText FirstName = (EditText) findViewById(R.id.menu02_3_edittext05);

        //국적
        final EditText Nationality = (EditText) findViewById(R.id.menu02_3_edittext06);

        //생년월일

        ImageButton BirthBtn = (ImageButton) findViewById(R.id.menu02_3_birthbtn);
        Birth_data = Birth.getText().toString();
        BirthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date_dialog = new DatePickerDialog(menu02Activity_3.this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Birth.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth +"일");
                    }}, 1998, 6, 30);
                Date_dialog.show();
            }
        });

        //성별
        final RadioGroup Gender = (RadioGroup) findViewById(R.id.menu02_3_radio08);
        RadioButton GenderM = (RadioButton) findViewById(R.id.menu02_3_radioM);
        RadioButton GenderF = (RadioButton) findViewById(R.id.menu02_3_radioF);


        //발행국
        final EditText IssuerCountry = (EditText) findViewById(R.id.menu02_3_edittext09);

        //여권 발급일


        passport_start_date=PassportStart.getText().toString();
        dateint=date2int(passport_start_date);
        ImageButton PassportStartBtn = (ImageButton) findViewById(R.id.menu02_3_startdatehbtn);
        PassportStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date_dialog = new DatePickerDialog(menu02Activity_3.this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        PassportStart.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth +"일");
                        passport_start_date=PassportStart.getText().toString();
                        dateint=date2int(passport_start_date);
                        PassportEnd.setText(dateint[0]+10 + "년 " + dateint[1] + "월 " + dateint[2] +"일");
                    }}, Tyear, Tmonth, Tday);
                Date_dialog.show();
            }
        });

        //여권 만료일

        ImageButton PassportEndBtn = (ImageButton) findViewById(R.id.menu02_3_enddatehbtn);
        PassportEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date_dialog = new DatePickerDialog(menu02Activity_3.this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        PassportEnd.setText(year + "년 " + (monthOfYear+1) + "월 " + dayOfMonth +"일");
                    }}, dateint[0]+10, dateint[1], dateint[2]);
                Date_dialog.show();
            }
        });

        //여권 종류
        final RadioGroup PassportType = (RadioGroup) findViewById(R.id.menu02_3_radio12);
        RadioButton PassportTypePM = (RadioButton) findViewById(R.id.menu02_3_radioPM);
        RadioButton PassportTypePS = (RadioButton) findViewById(R.id.menu02_3_radioPS);
        RadioButton PassportTypePR = (RadioButton) findViewById(R.id.menu02_3_radioPR);
        RadioButton PassportTypePO = (RadioButton) findViewById(R.id.menu02_3_radioPO);

        Button preBtn = (Button) findViewById(R.id.menu02_3_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button goBtn = (Button) findViewById(R.id.menu02_3_gobtn);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gender_data = null;
                switch (Gender.getCheckedRadioButtonId()) {
                    case R.id.menu02_3_radioM:
                        Gender_data = "M";
                        break;
                    case R.id.menu02_3_radioF:
                        Gender_data = "F";
                        break;
                }

                PassportType_data = null;
                switch (PassportType.getCheckedRadioButtonId()) {
                    case R.id.menu02_3_radioPM:
                        PassportType_data = "PM";
                        break;
                    case R.id.menu02_3_radioPS:
                        PassportType_data = "PS";
                        break;
                    case R.id.menu02_3_radioPR:
                        PassportType_data = "PR";
                        break;
                    case R.id.menu02_3_radioPO:
                        PassportType_data = "PO";
                        break;

                }


                PassportNumber_data = PassportNumber.getText().toString();
                LastName_data = LastName.getText().toString();
                FirstName_data = FirstName.getText().toString();
                Nationality_data = Nationality.getText().toString();
                IssuerCountry_data = IssuerCountry.getText().toString();
                PassportStart_data = PassportStart.getText().toString();
                PassportEnd_data = PassportEnd.getText().toString();

                String[] dataarr={PassportNumber_data, LastName_data, FirstName_data, Nationality_data, Birth_data, Gender_data, IssuerCountry_data, PassportStart_data, PassportEnd_data, PassportType_data};
                DBHelper.Insert2Table(db,"Passport_Info", dataarr);
                Intent intent = new Intent(getApplicationContext(), menu02Activity_4.class);
                intent.putExtra("PassportNumber", PassportNumber_data);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }

    public int[] date2int(String date){
        date = date.replaceAll(" ","");
        String[] yearArr = date.split("년");
        String[] monthArr = yearArr[1].split("월");
        String[] dayArr = monthArr[1].split("일");

        int year = Integer.parseInt(yearArr[0]);
        int month = Integer.parseInt(monthArr[0]);
        int day = Integer.parseInt(dayArr[0]);

        int[] intdate = {year, month, day};

        return intdate;
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
