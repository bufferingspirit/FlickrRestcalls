package com.example.admin.flickrrestcalls;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PictureActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent intent = getIntent();
        String URL = intent.getStringExtra("URL");

        imageView = (ImageView) findViewById(R.id.imageView);

        Glide.with(this).load(URL).into(imageView);
    }
}
