package com.ogada;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class menu02Activity_2 extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_CAMERA=1001;
    private TextureView mCameraTextureView;
    private Preview mPreview;
    private Button mCameraCaptureButton;
    private File file;

    Activity mainActivity = this;

    private static final String TAG = "MAINACTIVITY";

    static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu02_2);

        grantCameraPermission();
        mCameraCaptureButton = (Button) findViewById(R.id.menu02_2_picbtn);
        mCameraTextureView = (TextureView) findViewById(R.id.menu02_2_cam);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        file = new File(Environment.getExternalStorageDirectory() + "/DCIM", "OGADA_" + dateFormat.format(date) + ".jpg");

        mPreview = new Preview(this, mCameraTextureView, mCameraCaptureButton, file);

//        mCameraCaptureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPreview.takePicture();
//                Intent intent=new Intent(getApplicationContext(), menu02Activity_4.class);
//                intent.putExtra("PicFileName", file.getAbsolutePath());
//                startActivity(intent);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//            }
//        });

        Button preBtn = (Button) findViewById(R.id.menu02_2_backbtn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public void NextPage(){
        Intent intent=new Intent(getApplicationContext(), menu02Activity_4.class);
        intent.putExtra("PicFileName", file.getAbsolutePath());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            mCameraTextureView = (TextureView) findViewById(R.id.menu02_2_cam);
                            mPreview = new Preview(mainActivity, mCameraTextureView, mCameraCaptureButton, file);
                            mPreview.openCamera();
                            Log.d(TAG, "mPreview set");
                        } else {
                            Toast.makeText(this, "Should have camera permission to run", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }



    private void grantCameraPermission() {
        int permssionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permssionCheck != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "권한 승인이 필요합니다", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_LONG).show();

            }
        }
    }


}
