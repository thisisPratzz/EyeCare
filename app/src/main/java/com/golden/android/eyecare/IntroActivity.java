package com.golden.android.eyecare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.golden.android.eyecare.appintro.EnterName;

import java.util.ArrayList;

/**
 * Created by Machine on 07-01-2017.
 */

public class IntroActivity extends AppIntro2{







    @Override
    protected void onPostResume() {



        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








        addSlide(SampleSlide.newInstance(R.layout.slide1));
        addSlide(SampleSlide.newInstance(R.layout.pain));
        addSlide(SampleSlide.newInstance(R.layout.solution));
        addSlide(SampleSlide.newInstance(R.layout.pause));
        addSlide(SampleSlide.newInstance(R.layout.twentyfeet));
        setImmersiveMode(true,true);


        addSlide(new EnterName());

       // setImmersiveMode(true,false);
        showSkipButton(false);





        UiChangeListener();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
//        Intent returnIntent = new Intent();
//        setResult(123, returnIntent);

        finish();
    }

    public void UiChangeListener()
    {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
    }



}


