package com.gauss243j.appgeo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gauss243j.appgeo.helper.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread chrono =new Thread(){
            public void run(){
                try{
                    sleep(4000);

                }
                catch (InterruptedException e){
                    e.printStackTrace();}
                finally{

                    if (SharedPrefManager.getInstance(getApplicationContext()).getConnect()==1){
                        startActivity(new Intent(getApplicationContext(),MainA.class));
                        finish();
                    }else{startActivity(new Intent(getApplicationContext(), Signup.class));
                        finish();}

                }

            }


        };

        chrono.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        textView1.animate().alpha(1).translationX(75f).setDuration(200);
//        textView2.animate().alpha(1).translationX(75f).setDuration(600);
//        textView3.animate().alpha(1).translationX(75f).setDuration(1000);
//        textView4.animate().alpha(1).translationX(75f).setDuration(1600);
//        textView5.animate().alpha(1).translationX(75f).setDuration(2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        textView1.animate().alpha(1).translationX(-75f).setDuration(200);
//        textView2.animate().alpha(1).translationX(-75f).setDuration(600);
//        textView3.animate().alpha(1).translationX(-75f).setDuration(1000);
//        textView4.animate().alpha(1).translationX(-75f).setDuration(1600);
//        textView5.animate().alpha(1).translationX(-75f).setDuration(2000);
    }
}
