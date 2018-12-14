package com.example.sakasho1.camera3;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;


public class SecondActivity extends Activity {

    ImageView imageView;
    RadioButton RB1; RadioButton RB2; RadioButton RB3; RadioButton RRB1;
    RadioButton KRB1; RadioButton KRB2; RadioButton KRB3;
    ToggleButton tB;  ToggleButton tB2;
    TextView tV;

    private ImageView iView;
    private float scale = 1f;
    private ScaleGestureDetector detector;


    protected void findViews() {
        imageView = (ImageView) findViewById(R.id.imageView);

        tV = (TextView)findViewById(R.id.text);

        RB1 = (RadioButton) findViewById(R.id.soutyou);
        RB2 = (RadioButton) findViewById(R.id.hiru);
        RB3 = (RadioButton) findViewById(R.id.yugata);
        RRB1 = (RadioButton) findViewById(R.id.riseto);

        tB = (ToggleButton)findViewById(R.id.toggleButton);
        tB2 = (ToggleButton)findViewById(R.id.toggleButton2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_second);
        findViews();

        ImageView img = (ImageView)findViewById(R.id.imageView);
        String path = Environment.getExternalStorageDirectory().getPath();
        System.out.println("パス="+path);
        File dir = new File(path+"/kodeneto/");
        File file = new File(dir.getAbsolutePath()+"/img1_1.jpg");
        if (file.exists()){
            Bitmap bitmap2 = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(file.getPath()) ,2448,3264,false);
            img.setImageBitmap(bitmap2);
        }else{
            System.out.print("失敗、ファイルのパス="+ file );
        }

        detector = new ScaleGestureDetector(this,new ScaleListener());

        RB1.setVisibility(View.INVISIBLE);
        RB2.setVisibility(View.INVISIBLE);
        RB3.setVisibility(View.INVISIBLE);
        RRB1.setVisibility(View.INVISIBLE);


        ((ToggleButton) findViewById(R.id.toggleButton)).setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                RB1.setVisibility(View.VISIBLE);
                                RB2.setVisibility(View.VISIBLE);
                                RB3.setVisibility(View.VISIBLE);
                                RRB1.setVisibility(View.VISIBLE);
                                tB2.setChecked(false);
                            }else {
                                RB1.setVisibility(View.INVISIBLE);
                                RB2.setVisibility(View.INVISIBLE);
                                RB3.setVisibility(View.INVISIBLE);
                                RRB1.setVisibility(View.INVISIBLE);
                                imageView.setColorFilter(Color.argb(0,0,0,0));
                            }
                        }
                    }
        );



        ((ToggleButton) findViewById(R.id.toggleButton2)).setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                RB1.setVisibility(View.INVISIBLE);
                                RB2.setVisibility(View.INVISIBLE);
                                RB3.setVisibility(View.INVISIBLE);
                                RRB1.setVisibility(View.INVISIBLE);
                                tB.setChecked(false);
                                imageView.setColorFilter(Color.argb(100,51,51,51));


                            } else {
                               imageView.setColorFilter(Color.argb(0,0,0,0));
                            }
                        }
                    }

        );
    }

    public void onRadioButtonClicked(View view){
        RadioButton radioButton = (RadioButton) view;
        boolean checked = radioButton.isChecked();
        switch (radioButton.getId()) {
            case R.id.soutyou:
                if (checked) {
                    imageView.setColorFilter(Color.argb(50,0,0,20));
                }
                break;
            case R.id.hiru:
                if (checked) {
                    imageView.setColorFilter(Color.argb(50, 20, 0, 0));
                }
                break;
            case R.id.yugata:
                if (checked) {
                    imageView.setColorFilter(Color.argb(50, 120, 40, 0));
                }
                break;
            case R.id.riseto:
                if (checked){
                    imageView.setColorFilter(Color.argb(0,0,0,0));
                }
            default:
                break;
        }

    }

    public boolean onTouchEvent(MotionEvent event){
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            scale *= detector.getScaleFactor();
            imageView.setScaleX(scale);
            imageView.setScaleY(scale);
            return true;
        }
    }


    public void onModoru(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}



