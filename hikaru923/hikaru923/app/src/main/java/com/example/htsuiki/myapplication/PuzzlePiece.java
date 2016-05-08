package com.example.htsuiki.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

//パネル１枚のクラス
public class PuzzlePiece {
    private BitmapDrawable image;//分割された画像
    private float width,height,x,y,x1,y1,x2,y2;
    private int placeX,placeY;//パネルのマス目の番号
    public boolean placed;//正位置かどうか

    //コンストラクタ
//初期化
    public PuzzlePiece(Bitmap image,int x, int y, float w, float h) {
        super();
        this.image = new BitmapDrawable(image);
        placeX = x;
        placeY = y;
        width = w;
        height = h;
        placed = false;
    }

    //パネルを正位置に設置させる処理
    public void setLoc(int x, int y,boolean center){
        if (center){
            this.x = x;
            this.y = y;
            x1 = this.x - (width / 2);
            y1 = this.y - (height / 2);
        } else {
            this.x = x + (width / 2);
            this.y = y + (height / 2);
            x1 = x;
            y1 = y;
        }
        x2 = x1 + width;
        y2 = y1 + height;
    }

    //plaseXを他のクラスから取り出すための処理
    public int getPlaceX(){
        return placeX;
    }
    //plaseYを他のクラスから取り出すための処理
    public int getPlaceY(){
        return placeY;
    }
    public float getW(){
        return width;
    }
    public float getH(){
        return height;
    }
    public BitmapDrawable getB(){
        return image;
    }
    //短形情報を他のクラスから取り出すための処理
    public Rect getRect(){
        return new Rect((int)x1, (int)y1, (int)x2, (int)y2);
    }
    //渡された座標と１枚のパネル座標と合ってるかどうか
    public boolean isIn(int x, int y){
        return x > x1 && x < x2 && y > y1 && y < y2;
    }
    //パネル表示
    public void draw(Canvas c){
        image.setBounds((int)x1, (int)y1, (int)x2, (int)y2);
        image.draw(c);
    }

}