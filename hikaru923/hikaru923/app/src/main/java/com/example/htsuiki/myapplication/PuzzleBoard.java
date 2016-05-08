package com.example.htsuiki.myapplication;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;
import java.util.Random;

//ゲームの心臓部
    public class PuzzleBoard  extends AppCompatActivity {
    private static final int ROW = 2;//パネル縦の数
    private static final int COL = 2;//パネル横の数
    private float w = 240; //ボード横幅
    private float h = 360; //ボード縦幅
    private float pW; //パネル１枚横幅
    private float pH; //パネル１枚縦幅
    private float x, y; //パネル１枚座標
    private SoundPool soundPool;
    private int[] SoundId = new int[2];
    private com.example.htsuiki.myapplication.PuzzlePiece[][] data; //パネル１枚づつ設定
    public com.example.htsuiki.myapplication.PuzzlePiece dragPiece; //パネル１枚づつ座標設定
    public int count = 0; //カウント

    /*@Override
    protected void onResume() {
        super.onResume();

        // 効果音を使えるように読み込み
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        SoundId[1] = soundPool.load(getApplicationContext(), R.raw.tsukamu, 1);
        SoundId[0] = soundPool.load(getApplicationContext(), R.raw.pieceget, 1);

    }*/


    //コンストラクタ
//変数設定
    public PuzzleBoard(float x, float y, float w, float h, Bitmap image) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        pW = w / COL;
        pH = h / ROW;
        data = new com.example.htsuiki.myapplication.PuzzlePiece[COL][ROW];
        for (int i = 0; i < COL; i++) {
            for (int j = 0; j < ROW; j++) {
                Bitmap bitmap = Bitmap.createBitmap(image, (int) (pW * i),
                        (int) (pH * j), (int) pW, (int) pH);
                data[i][j] = new com.example.htsuiki.myapplication.PuzzlePiece(bitmap, i, j, pW, pH);
                data[i][j].setLoc((int) (x + pW * i), (int) (y + pH * j), false);
            }
        }
    }


    //初期設定というよりここではパネルをランダムでバラバラにする
//カウントを０に
//クリア後などにここだけ呼び出せば大丈夫
    public void init() {
        Random r = new Random(new Date().getTime());
        for (int i = 0; i < COL; i++) {
            for (int j = 0; j < ROW; j++) {
                data[i][j].setLoc(r.nextInt((int) w), r.nextInt((int) h), true);
                data[i][j].placed = false;
                //  data[i][j].getB().setLayoutParams(new LinearLayout.LayoutParams((int)(data[i][j].getW()), (int)(data[i][j].getH()), 2)); //追加２
            }
        }
        count = 0;
    }

    //パネル表示
    public void draw(Canvas c) {
        for (int i = 0; i < COL; i++) {
            for (int j = 0; j < ROW; j++) {
                if (data[i][j] != null)
                    data[i][j].draw(c);
            }
        }
    }

    //タッチ座標を受け取りその座標にパネルがあるかどうか
    public com.example.htsuiki.myapplication.PuzzlePiece getIn(int x, int y) {

        com.example.htsuiki.myapplication.PuzzlePiece result = null;
        for (int i = 0; i < COL; i++) {
            for (int j = 0; j < ROW; j++) {
                if (data[i][j].isIn(x, y))
                    result = data[i][j];
            }
        }
        return result;
    }

    //ドラッグされているパネルを座標に移動
    public void dragPiece(int x0, int y0) {
        if (dragPiece != null) {
            int px = dragPiece.getPlaceX();
            int py = dragPiece.getPlaceY();
            if (!data[px][py].placed)
                dragPiece.setLoc(x0, y0, true);
        }
    }

    //パネルの位置が正位置に近ければ正位置にセット
    public void checkPlace() {
        if (dragPiece != null) {
            int px = dragPiece.getPlaceX();
            int py = dragPiece.getPlaceY();
            Rect r = dragPiece.getRect();
            if (Math.abs(x + px * pW - r.left) < (pW/* / 4*/)
                    && Math.abs(y + py * pH - r.top) < (pH/* / 4*/)) {
                dragPiece.setLoc((int) (x + px * pW), (int) (y + py * pH),
                        false);
                data[px][py].placed = true;
               // soundPool.play(SoundId[0], 1.0F, 1.0F, 0, 0, 1.0F); // 正解音を再生
                //data[px][py].getB().setLayoutParams(new LinearLayout.LayoutParams((int)(data[px][py].getW()), (int)(data[px][py].getH()),1));//ピースはまると奥行き１に

            }
        }
    }

    //クリア判定
    public boolean checkFinish() {
        boolean flg = true;
        for (int i = 0; i < COL; i++)
            for (int j = 0; j < ROW; j++)
                if (!data[i][j].placed)
                    flg = false;
        return flg;

    }
 /*   @Override
   protected void onPause() {
        super.onPause();
        // SoundPool 解放
        soundPool.unload(SoundId[0]);
        soundPool.unload(SoundId[1]);

        soundPool.release();
    }
*/
}