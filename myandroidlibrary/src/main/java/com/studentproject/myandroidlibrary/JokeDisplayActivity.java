package com.studentproject.myandroidlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    private String mJokeString;
    private TextView mJokeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_display_activity);

        Intent jokeIntent = getIntent();

        if(jokeIntent != null){
            mJokeString = jokeIntent.getStringExtra("joke_extra");
        }

        mJokeTextView = findViewById(R.id.tv_joke_display);
        mJokeTextView.setText(mJokeString);

    }

}
