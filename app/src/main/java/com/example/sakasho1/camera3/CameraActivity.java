package com.example.sakasho1.camera3;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CameraActivity extends Activity {

    private SurfaceView mySurfaceView;
    private Camera myCamera;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mySurfaceView = (SurfaceView)findViewById(R.id.mySurfaceView);
        SurfaceHolder holder = mySurfaceView.getHolder();
        holder.addCallback(callback);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new TakePictureClickListener());
        mySurfaceView.setOnTouchListener(new AutoFocus());

        img = (ImageView)findViewById(R.id.img);
        img.setVisibility(View.INVISIBLE);

    }


    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback(){
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder){
            int cameraId = 0;
            myCamera = Camera.open();
            setCameraDisplayOrientation(cameraId);
            try{
                myCamera.setPreviewDisplay(surfaceHolder);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder,int i,int i2,int i3){
            myCamera.stopPreview();
            Camera.Parameters params = myCamera.getParameters();
            List<Size> previewSizes = params.getSupportedPreviewSizes();
            Size size = previewSizes.get(0);
            params.setPreviewSize(size.width,size.height);
            myCamera.setParameters(params);
            myCamera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder){
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }


        public void setCameraDisplayOrientation(int cameraId){
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(cameraId,cameraInfo);
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation){
                case Surface.ROTATION_0:
                    degrees = 0;break;
                case Surface.ROTATION_90:
                    degrees = 90;break;
                case Surface.ROTATION_180:
                    degrees = 180;break;
                case Surface.ROTATION_270:
                    degrees = 270;break;
            }
            int result;
            if (cameraInfo.facing == cameraInfo.CAMERA_FACING_FRONT){
                result = (cameraInfo.orientation + degrees) % 360;
                result = (360 - result) % 360;
            }else {
                result = (cameraInfo.orientation - degrees + 360) % 360;
            }
            myCamera.setDisplayOrientation(result);
        }
    };



    class TakePictureClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            myCamera.takePicture(null,null, new TakePictureCallback());
        }
    }

    class TakePictureCallback implements Camera.PictureCallback{
        @Override
        public void onPictureTaken(byte[] data, Camera camera){
            try {
                File dir = new File(Environment.getExternalStorageDirectory(),"kodeneto");
                if (!dir.exists()){
                    dir.mkdir();
                }
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap = Bitmap.createBitmap(bmp,0,0,width,height,matrix,true);
                File f = new File(dir,"img1_1.jpg");
                FileOutputStream fos = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                Toast.makeText(getApplicationContext(),"写真を保存しました",Toast.LENGTH_SHORT).show();
                fos.close();
                camera.startPreview();

            }catch (Exception e){}
        }
    }

    class AutoFocus implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                myCamera.autoFocus(AutoFocusListener);
            }
            return true;
        }

        private Camera.AutoFocusCallback AutoFocusListener = new Camera.AutoFocusCallback(){
            public void onAutoFocus(boolean success, Camera camera){}
        };
    }

    public void onHensyu(View view){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
