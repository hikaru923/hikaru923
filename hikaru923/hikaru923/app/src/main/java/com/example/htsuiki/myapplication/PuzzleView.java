package com.example.htsuiki.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

//主に色々な初期化や表示のためのクラス
//Puzzleクラスの次に呼び出される
public class PuzzleView extends View {
    private int btn_x = 47; //ボタン横座標
    private int btn_y = 770; //ボタン縦座標
    private int btn_w = 395; //ボタンの縦幅
    private int btn_h = 50; //ボタンの横幅
    private int board_x = 40; //ボード横座標
    private int board_y = 126; //ボード縦座標
    private float board_w = 400;//ボード横幅
    private float board_h = 600;//ボード縦幅
    private int score_x = 60; //スコア表示場所横
    private int score_y = 73; //スコア表示場所縦
    private Puzzle puzzle;
    private PuzzleBoard board;
    private Drawable back, btn1, btn2;
    private boolean btn_down, isPlaying;//ボタン状態,プレイ状態
    private int pressX, pressY;//タッチ場所
    private String message = "please touch button.";
    private Timer timer;
    public  Bitmap bmp;
    public Bitmap img;
    private int count = 0;
    private SoundPool soundPool;//
    private int[] SoundId = new int[2];//


    //コンストラクタ１
    public PuzzleView(Context context) {
        super(context);
        init(context);//初期化呼び出し
    }

    //コンストラクタ２
    public PuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);//初期化呼び出し
    }

    //初期化呼び出し
    private void init(Context context) {
        puzzle = (Puzzle) context;
        btn_down = false;//ボタン状態上がってる
        isPlaying = false;//プレイ中ではない
        setPuzzleSize();//変数設定呼び出し
    }

    //変数設定処理
    private void setPuzzleSize() {
        float w = puzzle.disp_w;
        float h = puzzle.disp_h;
        Log.d("PuzzleView.puzzle.a", "" + puzzle.a);
        Log.d("PuzzleView.disp_w", "" + w);
        Log.d("PuzzleView.disp_h", "" + h);
        float dw = w / 480f;
        float dh = h / 854f;

        btn_x *= dw;
        btn_y *= dh;
        btn_w *= dw;
        btn_h *= dh;
        board_x *= dw;
        board_y *= dh;
        board_w *= dw;
        board_h *= dh;
        score_x *= dw;
        score_y *= dh;
//ここまでで端末ごとの画面の大きさの違いを直す

        Resources resources = puzzle.getResources();

        back = resources.getDrawable(R.drawable.back1);
        back.setBounds(0, 0, (int) w, (int) h);
        btn1 = resources.getDrawable(R.drawable.start1);
        btn1.setBounds(btn_x, btn_y, btn_x + btn_w, btn_y + btn_h);
        btn2 = resources.getDrawable(R.drawable.start2);
        btn2.setBounds(btn_x, btn_y, btn_x + btn_w, btn_y + btn_h);

        //Bitmap img = BitmapFactory.decodeResource(resources, R.drawable.image1);//取り合えず以前の仕様（データ内からドロイド画像取得
        // img = puzzle.bmp;

        board = new PuzzleBoard(board_x, board_y, board_w, board_h, puzzle.bmp);
//ここまでで画像設定
    }

   /*@Override
    protected void onResume() {
        super.onResume();

       //  効果音を使えるように読み込み
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        SoundId[1] = soundPool.load(getApplicationContext(), R.raw.tsukamu, 1);
        SoundId[0] = soundPool.load(getApplicationContext(), R.raw.pieceget, 1);

    }
*/
    //タイマー処理
//スレッド処理と呼ばれるもので、Viewを使う場合のスレッド処理方法
//ここではcountを１秒に１だけ増やす
    public void startTimer() {
        final Handler handler = new Handler();
        TimerTask task = new TimerTask() {
            public int count = 0;

            @Override
            public void run() {
                message = "        Time: " + ++count + " sec.";
                Log.d("under", "message");
                Log.d("count is", "" + count);
                handler.post(new Runnable() {

                    public void run() {
                        invalidate();
                        Log.d("under", "invalidate");
                        Log.d("count is", "" + count);
                    }
                });
            }
        };
        timer = new Timer();
        Log.d("under", "newTimer");
        Log.d("count is", "" + count);
        timer.schedule(task, 0, 1000);
        Log.d("under", "Schedule");
        Log.d("count is", "" + count);
    }


    //Viewを取り込んでいると自動で作成されるメソッド
    @Override
    protected void onDraw(Canvas c) {
        c.drawColor(Color.BLACK);//背景色表示
        back.draw(c);//背景画像表示
        board.draw(c);//ボード画像表示
//ボタンが押されているかどうかでボタン画像変化
        if (btn_down) {
            btn2.draw(c);
        } else {
            btn1.draw(c);
        }
//テキスト表示
        Paint p = new Paint();
        p.setColor(Color.WHITE);//テキストを白に
        p.setTextSize(30f);//テキストのサイズ
        c.drawText(message, score_x, score_y, p);//テキスト表示
        Log.d("under", "drawText");
        Log.d("count is", "" + count);
    }

    //タッチイベント処理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
//タッチした時の処理
            case MotionEvent.ACTION_DOWN:
                pressX = x;
                pressY = y;
//プレイ中なら
//ボードクラスのgetInにタッチ座標を渡して
//ボードクラスのdragPieceに保管
//getInはタッチした場所にパネルがあるかどうか調べるメソッドで
//この処理でdragPieceにそのパネルが保管されます。なければ保管されません
//プレイ中でなければ
//タッチ座標をisInメソッドに渡して、ボタン状態を調べます
                if (isPlaying) {
                    board.dragPiece = board.getIn(pressX, pressY);
                  //  soundPool.play(SoundId[0], 1.0F, 1.0F, 0, 0, 1.0F); // 正解音を再生
                } else {
                    if (isIn(pressX, pressY, btn1.getBounds())) {
                        btn_down = true;
                    }
                }
                break;
//タッチして離れた時の処理
//プレイ中なら
//クリアかどうか判定。か、ボタン上でプレイ中じゃなければ開始
            case MotionEvent.ACTION_UP:
                if (isPlaying) {
                    board.checkPlace();
                    if (board.checkFinish()) {
                        timer.cancel();
                        Toast toast = Toast.makeText(puzzle,
                                "クリア！", Toast.LENGTH_LONG);
                        toast.show();
                        isPlaying = false;
                        Intent intent = new Intent(puzzle, SelectPhoto.class);
                        Log.d("under", "newIntent");
                        Log.d("count is", "" + count);
                        intent.putExtra("TIMER", message);
                        intent.putExtra("DATA", bmp);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        puzzle.startActivity(intent);
                        puzzle.finish();
                    }
                } else {
                    if (isIn(pressX, pressY, btn1.getBounds())) {
                        btn_down = false;
                        isPlaying = true;
                        board.init();
                        startTimer();
                        Toast toast = Toast.makeText(puzzle,
                                "スタート！", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                btn_down = false;//何もなければボタンは常時上がっている
                board.dragPiece = null;//パネルは触っていない
                break;
//タッチしながら移動中処理
//プレイ中なら
//ボードクラスのdragPieceにタッチ座標を渡しながら移動
            case MotionEvent.ACTION_MOVE:
                if (isPlaying) {
                    board.dragPiece(x, y);
                }
                break;
        }
        invalidate();//再表示
        return true;
    }

    //渡された座標が渡された短形範囲にあるかどうか
    public boolean isIn(int x, int y, Rect rect) {
        return x > rect.left && x < rect.right && y > rect.top && y < rect.bottom;
    }

   /*protected void onPause() {
        super.onPause();
        // SoundPool 解放
        soundPool.unload(SoundId[0]);
        soundPool.unload(SoundId[1]);

        soundPool.release();

    }*/
}