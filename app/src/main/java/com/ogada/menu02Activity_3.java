package com.ogada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class menu02Activity_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu02_3);


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
                Intent intent = new Intent(getApplicationContext(), menu02Activity_4.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


        // 여권번호
        EditText PassportNumber = (EditText) findViewById(R.id.menu02_3_edittext03);
        String PassportNumber_data = PassportNumber.getText().toString();

        //성
        EditText LastName = (EditText) findViewById(R.id.menu02_3_edittext04);
        String LastName_data = LastName.getText().toString();

        // 이름
        EditText FirstName = (EditText) findViewById(R.id.menu02_3_edittext05);
        String FirstName_data = FirstName.getText().toString();

        //국적
        EditText Nationality = (EditText) findViewById(R.id.menu02_3_edittext06);
        String Nationality_data = Nationality.getText().toString();

        //생년월일
        TextView Birth = (TextView) findViewById(R.id.menu02_3_edittext07);
        ImageButton BirthBtn = (ImageButton) findViewById(R.id.menu02_3_birthbtn);


        String Birth_data = Birth.getText().toString();

        //성별
        RadioGroup Gender = (RadioGroup) findViewById(R.id.menu02_3_radio08);
        RadioButton GenderM = (RadioButton) findViewById(R.id.menu02_3_radioM);
        RadioButton GenderF = (RadioButton) findViewById(R.id.menu02_3_radioF);
        String Gender_data = null;
        switch (Gender.getCheckedRadioButtonId()) {
            case R.id.menu02_3_radioM:
                Gender_data = "M";
                break;
            case R.id.menu02_3_radioF:
                Gender_data = "F";
                break;
        }

        //발행국
        EditText IssuerCountry = (EditText) findViewById(R.id.menu02_3_edittext09);
        String IssuerCountry_data = IssuerCountry.getText().toString();

        //여권 발급일
        TextView PassportStart = (TextView) findViewById(R.id.menu02_3_edittext10);
        ImageButton PassportStartBtn = (ImageButton) findViewById(R.id.menu02_3_startdatehbtn);


        //여권 만료일
        TextView PassportEnd = (TextView) findViewById(R.id.menu02_3_edittext11);
        ImageButton PassportEndBtn = (ImageButton) findViewById(R.id.menu02_3_enddatehbtn);
        String PassportEnd_data = PassportEnd.getText().toString();

        //여권 종류
        RadioGroup PassportType = (RadioGroup) findViewById(R.id.menu02_3_radio12);
        RadioButton PassportTypePM = (RadioButton) findViewById(R.id.menu02_3_radioPM);
        RadioButton PassportTypePS = (RadioButton) findViewById(R.id.menu02_3_radioPS);
        RadioButton PassportTypePR = (RadioButton) findViewById(R.id.menu02_3_radioPR);
        RadioButton PassportTypePO = (RadioButton) findViewById(R.id.menu02_3_radioPO);
        String PassportType_data = null;
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
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
