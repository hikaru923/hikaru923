package com.example.htsuiki.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingPage extends AppCompatActivity implements View.OnClickListener {


    private Button button9;
    private SeekBar seekBar;
    private TextView textView11;
    private ImageView imageView;


   Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settei);

        Intent intent = getIntent();
        bmp = intent.getParcelableExtra("DATA");

        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView11 = (TextView) findViewById(R.id.textView11);
        imageView = (ImageView) findViewById(R.id.imageView11) ;
        imageView.setImageBitmap(bmp);
    }

    public void onClick(View v) {
        if (v == button9) {
            Log.v("SettingPage","SettingPage");
            Intent intent = new Intent(this, Puzzle.class);
            intent.putExtra("DATA", bmp);
            startActivityForResult(intent,0);
        }
    }




}

