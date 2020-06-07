package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class menu02Activity_Img2Text extends AppCompatActivity {

    TessBaseAPI tess;
    String dataPath="";
    String PicFileName;

    DBHelper dbHelper;
    SQLiteDatabase db = null;

    String PassportNumber_data, LastName_data, FirstName_data, Nationality_data, Birth_data, IssuerCountry_data, PassportStart_data, PassportEnd_data, Gender_data, PassportType_data;;
    EditText PassportNumber_edit, LastName_edit, FirstName_edit, Nationality_edit, IssuerCountry_edit;
    TextView Birth_edit, PassportStart_edit, PassportEnd_edit;
    RadioGroup Gender_edit, PassportType_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu02__img2text);

        Intent intent=getIntent();
        PicFileName=intent.getExtras().getString("PicFileName");

        OpenCVLoader.initDebug();   //안해주면 에러

        // 각 변수 초기화
        initEditView();
        initTess();

        setData("M1234567", "SSAM", "JANG", "KOR","11 NOV 2012","F","KOREA","05 DEC 2018","05 DEC 2028","PM");
        setDataInView();

        dbHelper = new DBHelper(this, MainActivity.dbVersion);
        db = dbHelper.getWritableDatabase();

        //ImageView testview = (ImageView)findViewById(R.id.img2text_text03);
        //testview.setImageBitmap(ImagePreprocesing(Path2Bitmap(PicFileName)));
        //testview.setImageBitmap(ImagePreprocesing(bitmap));

//        OCR(BitmapFactory.decodeResource(getResources(), R.drawable.passporttest2));


        Button preBtn = (Button) findViewById(R.id.img2text_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button goBtn = (Button) findViewById(R.id.img2text_gobtn);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputDB();
                Intent intent = new Intent(getApplicationContext(), menu02Activity_4.class);
                intent.putExtra("PassportNumber", PassportNumber_data);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    // View 초기화
    public void initEditView(){
        PassportNumber_edit = (EditText)findViewById(R.id.img2text_edittext03);
        LastName_edit = (EditText)findViewById(R.id.img2text_edittext04);
        FirstName_edit = (EditText)findViewById(R.id.img2text_edittext05);
        Nationality_edit = (EditText)findViewById(R.id.img2text_edittext06);
        Birth_edit = (TextView)findViewById(R.id.img2text_edittext07);
        Gender_edit = (RadioGroup)findViewById(R.id.img2text_radio08);
        IssuerCountry_edit = (EditText)findViewById(R.id.img2text_edittext09);
        PassportStart_edit = (TextView)findViewById(R.id.img2text_edittext10);
        PassportEnd_edit = (TextView)findViewById(R.id.img2text_edittext11);
        PassportType_edit = (RadioGroup)findViewById(R.id.img2text_radio12);
    }

    // TessAPI 초기화
    public void initTess(){
        //데이터 경로
        dataPath = getFilesDir()+"/tesseract/";
        System.out.println(getFilesDir());

        //한글 & 영어 데이터 체크
        checkFile(new File(dataPath+"tessdata/"),"kor");
        checkFile(new File(dataPath+"tessdata/"),"eng");

        //문자 인식을 수행할 tess 객체 생성
        String lang = "kor+eng";
        tess = new TessBaseAPI();
        tess.init(dataPath, lang);
    }

    public void setData(String passportnumber, String lastname, String firstname, String nationality, String birth, String gender, String issuercountry, String passportstart, String passportend, String passporttype){
        PassportNumber_data=passportnumber;
        LastName_data=lastname;
        FirstName_data=firstname;
        Nationality_data=nationality;
        Birth_data=birth;
        Gender_data=gender;
        IssuerCountry_data=issuercountry;
        PassportStart_data=passportstart;
        PassportEnd_data=passportend;
        PassportType_data=passporttype;
    }

    // 뷰에 데이터 집어넣음
    public void setDataInView(){
        PassportNumber_edit.setText(PassportNumber_data);
        LastName_edit.setText(LastName_data);
        FirstName_edit.setText(FirstName_data);
        Nationality_edit.setText(Nationality_data);
        Birth_edit.setText(Birth_data);
        Gender_edit.check(getRadioIndex(Gender_data));
        IssuerCountry_edit.setText(IssuerCountry_data);
        PassportStart_edit.setText(PassportStart_data);
        PassportEnd_edit.setText(PassportEnd_data);
        PassportType_edit.check(getRadioIndex(PassportType_data));
    }

    // 뷰에서 데이터 가져옴
    public void getDataInView(){
        PassportNumber_data = PassportNumber_edit.getText().toString();
        LastName_data = LastName_edit.getText().toString();
        FirstName_data = FirstName_edit.getText().toString();
        Nationality_data = Nationality_edit.getText().toString();
        Birth_data = Birth_edit.getText().toString();
        IssuerCountry_data = IssuerCountry_edit.getText().toString();
        PassportStart_data = PassportStart_edit.getText().toString();
        PassportEnd_data = PassportEnd_edit.getText().toString();

        switch (Gender_edit.getCheckedRadioButtonId()) {
            case R.id.img2text_radioM:
                Gender_data = "M";
                break;
            case R.id.img2text_radioF:
                Gender_data = "F";
                break;
        }

        switch (PassportType_edit.getCheckedRadioButtonId()) {
            case R.id.img2text_radioPM:
                PassportType_data = "PM";
                break;
            case R.id.img2text_radioPS:
                PassportType_data = "PS";
                break;
            case R.id.img2text_radioPR:
                PassportType_data = "PR";
                break;
            case R.id.img2text_radioPO:
                PassportType_data = "PO";
                break;

        }
    }

    public int getRadioIndex(String data){
        int result=-1;

        switch (data){
            case "M":
                result=R.id.img2text_radioM;
                break;
            case "F":
                result=R.id.img2text_radioF;
                break;
            case "PM":
                result=R.id.img2text_radioPM;
                break;
            case "PS":
                result=R.id.img2text_radioPS;
                break;
            case "PR":
                result=R.id.img2text_radioPR;
                break;
            case "PO":
                result=R.id.img2text_radioPO;
                break;
        }

        return result;
    }


    // DB에 값 저장
    public void inputDB(){
        getDataInView();
        String[] dataarr={PassportNumber_data, LastName_data, FirstName_data, Nationality_data, Birth_data, Gender_data, IssuerCountry_data, PassportStart_data, PassportEnd_data, PassportType_data};
        DBHelper.Insert2Table(db,"Passport_Info", dataarr);
    }


    //데이터 체크
    private void checkFile(File dir, String lang){
        if(!dir.exists()&&dir.mkdirs()){
            copyFiles(lang);
        }
        if(dir.exists()){
            String datafilePath=dataPath+"/tessdata/"+lang+".traineddata";
            File datafile=new File(datafilePath);
            if(!datafile.exists()){
                copyFiles(lang);
            }
        }
    }


    // 비트맵 영상 획득
    public Bitmap Path2Bitmap(String imgPath){
        File file = new File(imgPath); // 파일 불러오기
        Bitmap image=null;
        if(file.exists()){
            image = BitmapFactory.decodeFile(imgPath);
            image = rotateImage(image, 90);
        }
        return image;
    }

    // 영상 회전
    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public Bitmap ImagePreprocesing(Bitmap originalImg){
        Bitmap image=null;
        Mat img = new Mat();
        Mat imageGray = new Mat();
        Mat imageCny = new Mat();

        Utils.bitmapToMat(originalImg, img);
        Imgproc.cvtColor(img, imageGray, Imgproc.COLOR_BGR2GRAY); // GrayScale
        //Imgproc.Canny(imageGray1, imageCny1, 10, 100, 3, true); // Canny Edge 검출
        Imgproc.threshold(imageGray, imageCny, 150, 255, Imgproc.THRESH_BINARY); //Binary
        image = Bitmap.createBitmap(imageCny.cols(), imageCny.rows(), Bitmap.Config.ARGB_8888); // 비트맵 생성
        Utils.matToBitmap(imageCny, image); // Mat을 비트맵으로 변환

        return image;
    }


    public String OCR(Bitmap bitmap){
        String OCRresult=null;
        tess.setImage(bitmap);
        OCRresult = tess.getUTF8Text();
        return OCRresult;
    }

    private void copyFiles(String lang){
        try{
            String filepath = dataPath + "/tessdata/" +lang +".traineddata";
            AssetManager assertManager = getAssets();
            InputStream inStream = assertManager.open("tessdata/"+lang+".traineddata");
            OutputStream outStream = new FileOutputStream(filepath);
            byte[] buffer=new byte[1024];
            int read;
            while((read=inStream.read(buffer))!=-1){
                outStream.write(buffer, 0, read);
            }
            outStream.flush();
            outStream.close();
            inStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
