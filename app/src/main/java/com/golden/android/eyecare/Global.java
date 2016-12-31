package com.golden.android.eyecare;

import android.app.Application;

/**
 * Created by Machine on 15-08-2016.
 */
public class Global extends Application
{
    boolean popupDiplayed
           ;//
     //= false;
   // int Score=0;

    public Boolean getFlag(){
        return popupDiplayed;
    }
//    public int getScore(){
//        return Score;
//    }

    public void setFlag(Boolean flag)
    {
        popupDiplayed=flag;
    }

//    public void setScore(int sc)
//    {
//        Score=sc;
//    }


}

