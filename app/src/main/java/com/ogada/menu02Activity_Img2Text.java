package com.ogada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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

    String PassportNumber_data, LastName_data, FirstName_data, Nationality_data, Birth_data, Gender_data, IssuerCountry_data, PassportStart_data, PassportEnd_data, PassportType_data;
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


        ImageView testview = (ImageView)findViewById(R.id.img2text_text03);
        testview.setImageBitmap(Path2Bitmap(PicFileName));
//        processImage(BitmapFactory.decodeResource(getResources(), R.drawable.passporttest2));
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


    public Bitmap Path2Bitmap(String imgPath){
        File file = new File(imgPath); // 파일 불러오기
        Bitmap image1=null;
        Mat img1 = new Mat();
        if(file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath()); // 비트맵 생성
            OpenCVLoader.initDebug(); // 이 코드를 선언해주지않으면 컴파일 에러 발생
            Utils.bitmapToMat(myBitmap, img1);
            image1 = Bitmap.createBitmap(img1.cols(), img1.rows(), Bitmap.Config.ARGB_8888); // 비트맵 생성
            Utils.matToBitmap(img1, image1); // Mat을 비트맵으로 변환
        }
        return image1;
    }



    public Bitmap Img2GrayEdge(String imgPath){
        File file = new File(imgPath); // 파일 불러오기
        Bitmap image1=null;
        Mat img1 = new Mat();
        if(file.exists()) { // 파일이 존재한다면
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath()); // 비트맵 생성
            OpenCVLoader.initDebug(); // 이 코드를 선언해주지않으면 컴파일 에러 발생
            Utils.bitmapToMat(myBitmap, img1);
            Mat imageGray1 = new Mat();
            Mat imageCny1 = new Mat();
            Imgproc.cvtColor(img1, imageGray1, Imgproc.COLOR_BGR2GRAY); // GrayScale
            //Imgproc.Canny(imageGray1, imageCny1, 10, 100, 3, true); // Canny Edge 검출
            Imgproc.threshold(imageGray1, imageCny1, 150, 255, Imgproc.THRESH_BINARY); //Binary
            image1 = Bitmap.createBitmap(imageCny1.cols(), imageCny1.rows(), Bitmap.Config.ARGB_8888); // 비트맵 생성
            Utils.matToBitmap(imageCny1, image1); // Mat을 비트맵으로 변환
        }
        return image1;
    }

    public Bitmap Img2GrayEdge(int imgPath){
        Drawable drawable = getResources().getDrawable(imgPath);// drawable 타입을 bitmap으로 변경
        Bitmap myBitmap = ((BitmapDrawable)drawable).getBitmap();
        Bitmap image1=null;
        Mat img1 = new Mat();
        OpenCVLoader.initDebug(); // 이 코드를 선언해주지않으면 컴파일 에러 발생
        Utils.bitmapToMat(myBitmap, img1);
        Mat imageGray1 = new Mat();
        Mat imageCny1 = new Mat();
        Imgproc.cvtColor(img1, imageGray1, Imgproc.COLOR_BGR2GRAY); // GrayScale
        //Imgproc.Canny(imageGray1, imageCny1, 10, 100, 3, true); // Canny Edge 검출
        Imgproc.threshold(imageGray1, imageCny1, 150, 255, Imgproc.THRESH_BINARY); //Binary
        image1 = Bitmap.createBitmap(imageCny1.cols(), imageCny1.rows(), Bitmap.Config.ARGB_8888); // 비트맵 생성
        Utils.matToBitmap(imageCny1, image1); // Mat을 비트맵으로 변환
        return image1;
    }

    public String processImage(Bitmap bitmap){
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
