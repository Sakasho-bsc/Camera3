package com.example.sakasho1.camera3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    FrameLayout FL1;
    Button B1;

    protected void findViews(){
        B1=(Button)findViewById(R.id.button1);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dialogBtn = (Button)findViewById(R.id.dialogButton);
        dialogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CustomDialogFlagment dialog = new CustomDialogFlagment();
                dialog.show(getFragmentManager(),"sample");
            }
        });

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }



}
