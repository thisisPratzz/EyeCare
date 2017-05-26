package com.golden.android.eyecare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Vibrator;
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
        setContentView(R.layout.reward);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = sharedPreferences.getString("example_text", null);
        TextView nameview = (TextView) findViewById(R.id.RewardTextView);
      //  name = nameview.getText()+" "+ name;
        nameview.setText(nameview.getText()+" "+ name);
        // TODO increasing score
    increaseScore();
        Vibrator v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(1000);



        final Button shareButton = (Button) findViewById(R.id.share);
        final String finalName = name;
        shareButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {


                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                shared = getSharedPreferences("score", Context.MODE_PRIVATE);
                Integer myScore = shared.getInt("MyScore",0);



                Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                        + "/drawable/" + "ic_eye");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello!! checkout my EyeCare score\n"+ finalName +"'s Score :"+myScore+"\nTo Protect your Eyes from pain use  ");
                //  shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("text/plain");
//                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "send"));

            }
        });

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
        TextView Sc = (TextView) findViewById(R.id.ScoreValue);
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

