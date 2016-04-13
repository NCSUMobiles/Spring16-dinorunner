package com.dino.ncsu.dinorunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by luyao on 4/12/2016.
 */
public class MapView {

    Canvas canvas = null;
    Resources resources = null;

    int canvasWidth = 0;
    int canvasHeight = 0;

    int startX = 0;
    int startY = 0;
    int endX = 0;
    int endY = 0;

    int playerX = 0;
    int playerY = 0;
    int playerVeloX = 6;
    int playerVeloY = 6;

    int dinoX = 0;
    int dinoY = 0;
    int dinoVeloX = 3;
    int dinoVeloY = 3;

    int[] testPath = {};

    public MapView(Canvas canvas, Resources resources) {

        this.canvas = canvas;
        this.resources = resources;

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        System.out.println("======= Canvas Size ============ " + canvasWidth + ", " + canvasHeight);

        startX = canvasWidth * 5/100;
        startY = canvasHeight * 10/100;
        endX = canvasWidth * 95/100;
        endY = startY;

        playerX = startX;
        playerY = startY;

        dinoX = startX;
        dinoY = startY;

        //debug
        testPath = new int[]{startX, startY, endX, endY};
    }

    public void draw() {

        drawTerrain();

        drawDinosaur();

        drawPlayer();

    }


    private void drawTerrain() {

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(10.0f);

        for(int i = 0; i < testPath.length - 3; i += 2) {
            canvas.drawLine(testPath[i], testPath[i+1], testPath[i+2], testPath[i+3], paint);
        }

        //draw finish flag
        Bitmap b= BitmapFactory.decodeResource(resources, R.mipmap.finishflag);
        Rect rect = new Rect(endX - 80, endY - 100, endX, endY);
        canvas.drawBitmap(b, null, rect, null);
    }

    private void drawPlayer() {


//        Paint paint = new Paint();
//        paint.setColor(Color.parseColor("#ff0000"));
//        paint.setStyle(Paint.Style.FILL);
//
//        Path path = new Path();
//        path.moveTo(playerX, playerY);
//        path.lineTo(playerX - 30, playerY - 80);
//        path.lineTo(playerX + 30, playerY - 80);
//        path.close();
//
//        canvas.drawPath(path, paint);

        Bitmap b= BitmapFactory.decodeResource(resources, R.mipmap.runman);
        Rect rect = new Rect(playerX - 40, playerY - 80, playerX + 40, playerY);
        canvas.drawBitmap(b, null, rect, null);

        playerX += playerVeloX;
        playerY += 0;

        //System.out.println("======= Player Position ============ " + playerX + ", " + playerY);
    }

    private void drawDinosaur() {
//        Paint paint = new Paint();
//        paint.setColor(Color.parseColor("#0000ff"));
//        paint.setStyle(Paint.Style.FILL);
//
//        Path path = new Path();
//        path.moveTo(dinoX, dinoY);
//        path.lineTo(dinoX - 30, dinoY - 80);
//        path.lineTo(dinoX + 30, dinoY - 80);
//        path.close();
//
//        canvas.drawPath(path, paint);

        Bitmap b= BitmapFactory.decodeResource(resources, R.mipmap.forest_giant);
        Rect rect = new Rect(dinoX - 50, dinoY - 100, dinoX + 50, dinoY);
        canvas.drawBitmap(b, null, rect, null);

        dinoX += dinoVeloX;
        dinoY += 0;
    }

}
