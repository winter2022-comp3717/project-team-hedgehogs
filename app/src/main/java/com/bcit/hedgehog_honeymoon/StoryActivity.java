package com.bcit.hedgehog_honeymoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    TextView scrollable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_story);

        scrollable = (TextView)findViewById(R.id.textView_story);
        ImageView imgview = findViewById(R.id.Imageview_Event);
        //Enabling scrolling on TextView.
        scrollable.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        int storyID = intent.getExtras().getInt("TEXT");
        String story = getString(storyID);
        int imgID = intent.getExtras().getInt("IMG");

        scrollable.setText(story);
        imgview.setImageDrawable(getDrawable(imgID));

    }

    public void returnToGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}