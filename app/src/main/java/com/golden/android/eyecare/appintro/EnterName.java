
package com.golden.android.eyecare.appintro;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;
import com.golden.android.eyecare.R;

import static java.security.AccessController.getContext;

public class EnterName extends Fragment implements ISlidePolicy, ISlideBackgroundColorHolder
{

    private LinearLayout layoutContainer;
  //  private CheckBox checkBox;
    private EditText editText;
    Context context;


    @Override
    public void onDestroyView() {

        String s =editText.getText().toString();

        if(!s.isEmpty()) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putString("example_text", s).apply();
        }
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =getContext().getApplicationContext();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.entername, container, false);
        layoutContainer = (LinearLayout) view.findViewById(R.id.slide_policy_demo_container);
//        checkBox = (CheckBox) view.findViewById(R.id.slide_policy_demo_checkbox);

        editText = (EditText) view.findViewById(R.id.edit_name);



            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String s= sharedPreferences.getString("example_text",null);
        if(s!=null)
        {
            editText.setText(s);
        }


        

        return view;
    }

    @Override
    public boolean isPolicyRespected() {
        String s =editText.getText().toString();

        if(s.isEmpty()) {

            return false;
        }

        return true;

    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        Toast.makeText(getContext(), "Please Enter the name", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#F44336");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if(layoutContainer != null) {
            layoutContainer.setBackgroundColor(backgroundColor);
        }
    }




}
