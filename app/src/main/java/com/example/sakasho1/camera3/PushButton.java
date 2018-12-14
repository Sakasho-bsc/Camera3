package com.example.sakasho1.camera3;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


@SuppressLint("AppCompatCustomView")
public class PushButton extends Button {

    SePlayer sePlayer = new SePlayer(getContext());


    public PushButton(Context context){
        super(context);
    }

    public PushButton(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public void setPressed(boolean pressed){
        if (pressed){
            this.setScaleX(1.12f);
            this.setScaleY(1.12f);
            sePlayer.playSe();
        }else{
            this.setScaleY(1.0f);
            this.setScaleX(1.0f);
        }
        super.setPressed(pressed);
    }
}
