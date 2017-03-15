package com.golden.android.eyecare;

import android.support.v7.app.AppCompatActivity;pac
import android.support.v7.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.flipkart.chatheads.ChatHeadContainer;
import static android.R.attr.key;

public class Head extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
    }

    final ChatHeadContainer chatContainer = (ChatHeadContainer) findViewById(R.id.chat_container);
chatContainer.setViewAdapter(new ChatHeadViewAdapter() {
        @Override
        public FragmentManager getFragmentManager() {
            return getSupportFragmentManager();
        }

        @Override
        public Fragment attachView(Object key, ChatHead chatHead) {
            // return the fragment which should be shown when the arrangment switches to maximized (on clicking a chat head)
            // you can use the key parameter to get back the object you passed in the addChatHead method.
            // this key should be used to decide which fragment to show.
            return new Fragment();
        }

        @Override
        public Drawable getChatHeadDrawable(Object key) {
            // this is where you return a drawable for the chat head itself based on the key. Typically you return a circular shape
            // you may want to checkout circular image library https://github.com/flipkart-incubator/circular-image
            return getResources().getDrawable(R.drawable.circular_view);
        }
    });
}
