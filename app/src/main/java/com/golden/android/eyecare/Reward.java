package com.golden.android.eyecare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Reward extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String s = sharedPreferences.getString("example_text", null);
        TextView name = (TextView) findViewById(R.id.RewadTextView);
        s = name.getText()+" "+ s;
        name.setText(s);
    increaseScore();
        Button button = (Button) findViewById(R.id.ContinueButton);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          finish();
                                      }
                                  }

        );

    }

    void increaseScore() {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shared = getSharedPreferences("score", Context.MODE_PRIVATE);
        Integer myScore = shared.getInt("MyScore",0);
        TextView Sc = (TextView) findViewById(R.id.textView3);
        //Integer i =Integer.parseInt(myScore);
        myScore+=20;

       // myScore=i.toString();
        Sc.setText(""+myScore);
        shared.edit().putInt("MyScore",myScore).commit();
//        new CountDownTimer(i, 1000) {
//            TextView mTextField=(TextView)findViewById(R.id.fullscreen_content);
//            public void onTick(long millisUntilFinished) {
//                mTextField.setText("" + 20-(millisUntilFinished / 1000));
//            }


//            @Override
//            public void onTick(long l) {
//                Sc.setText();
//            }
//        }.start();
    }
}

