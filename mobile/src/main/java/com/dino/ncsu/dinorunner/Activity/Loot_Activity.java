package com.dino.ncsu.dinorunner.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dino.ncsu.dinorunner.R;

public class Loot_Activity extends Activity implements Runnable {

    private Bitmap loot_table_view;
    public static Typeface oldLondon;


    //Surfaceview information
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    //Display information
    private DisplayMetrics display;
    private int width; //HTC ONE M8 = 1080
    private int height; //HTC ONE M8 = 1776

    //Scale for image size of different screens
    private float scale_width;
    private float scale_height;

    private Paint paint = new Paint();

    //Thread information
    private Thread thread;
    private boolean locker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        display = this.getResources().getDisplayMetrics();
        width = display.widthPixels;
        height = display.heightPixels;

        loot_table_view = BitmapFactory.decodeResource(getResources(), R.mipmap.frame_table_loot);
        loot_table_view = Bitmap.createScaledBitmap(this.loot_table_view, width, height, true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loot_);

        surfaceView = (SurfaceView) findViewById(R.id.loot_table_surface);
        surfaceHolder = surfaceView.getHolder();

        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (locker) {
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                draw(canvas);

                // End of painting to canvas. system will paint with this canvas,to the surface.
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void draw(Canvas canvas) {
        if(canvas != null) {
            canvas.drawBitmap(loot_table_view, 0, 0, paint);
        }
    }
}
