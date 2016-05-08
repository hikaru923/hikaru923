package com.example.htsuiki.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Puzzle extends AppCompatActivity {
    public float disp_w, disp_h;//端末の画面の大きさを取得するための変数
    public int a;

    private Button button10;
    private Button button11;
    private ImageView imageView;

    public Bitmap bmp = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//ここから下３行でフルスクリーン処理
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        // window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//端末の画面の大きさを取得するための処理
        WindowManager manager = window.getWindowManager();
        Display disp = manager.getDefaultDisplay();
        a = 1;
        disp_w = disp.getWidth();//端末の画面の横幅取得

        disp_h = disp.getHeight() - 250;//端末の画面の縦幅取得

        setContentView(R.layout.puzzle);

        Intent intent = getIntent();
        bmp = intent.getParcelableExtra("DATA");




       // Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer2);
        //chronometer.start();
    }


    }

