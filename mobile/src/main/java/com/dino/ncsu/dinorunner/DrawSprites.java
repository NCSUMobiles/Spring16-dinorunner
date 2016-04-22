package com.dino.ncsu.dinorunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;

import com.dino.ncsu.dinorunner.Objects.Dinosaur;
import com.dino.ncsu.dinorunner.Objects.Player;
import com.dino.ncsu.dinorunner.Objects.Tile;
import com.dino.ncsu.dinorunner.Objects.Track;

import java.util.List;

/**
 * Created by luyao on 4/12/2016.
 */
public class DrawSprites {

    Canvas canvas = null;
    Resources resources = null;

    float playerX = 0;
    float playerY = 0;
    float playerDirX = 0;
    float playerDirY = 0;

    float dinoX = 0;
    float dinoY = 0;
    float dinoDirX = 0;
    float dinoDirY = 0;
    float dinoTileNextX;
    float dinoTileNextY;
    float dist;

    float scale_width;
    float scale_height;
    DisplayMetrics display;

    int dinoTileId = 0;
    List<Tile> trackTiles = null;
    float distancePerPixel = 0;

    long lastTime = 0;
    long deltaTime = 0;

    public DrawSprites(Canvas canvas, Resources resources) {

        this.canvas = canvas;
        this.resources = resources;

        trackTiles = Track.getInstance().getTileList();

        //Scaling
        display = resources.getDisplayMetrics();
        float width = display.widthPixels;
        float height = display.heightPixels;
        scale_width = width / 1080;
        scale_height = height / 1776;

        //Re-adjust tiles' (X,Y) by subtracting getStatusBarHeight() from each Y
        for(int i = 0; i < trackTiles.size(); i++) {
            trackTiles.get(i).setY(trackTiles.get(i).getY() - getStatusBarHeight());
        }

        Tile startTile = trackTiles.get(0);

        playerX = startTile.getX();
        playerY = startTile.getY();


        dinoX = startTile.getX();
        dinoY = startTile.getY();
        dinoDirX = startTile.getDirX();
        dinoDirY = startTile.getDirY();

        distancePerPixel = (float) (Track.getInstance().getTotalDistance() /
                Track.getInstance().getTotalLength());

        lastTime = System.currentTimeMillis();
    }

    public void draw() {
        long curTime = System.currentTimeMillis();
        deltaTime = curTime - lastTime;
        lastTime = curTime;

        checkTilesDino();

        drawDinosaur();

        drawPlayer();

        //test
        drawTiles();
    }

    private void checkTilesDino() {
        int nextDinoTileId = (dinoTileId + 1) % trackTiles.size();

        dinoTileNextX = trackTiles.get(nextDinoTileId).getX();
        dinoTileNextY = trackTiles.get(nextDinoTileId).getY();

        dist = (dinoTileNextX - dinoX) * (dinoTileNextX - dinoX) +
                (dinoTileNextY - dinoY) * (dinoTileNextY - dinoY);
        if(dist < 25 && matchDirection(dinoDirX, dinoDirY, trackTiles.get(dinoTileId).getDirX(), trackTiles.get(dinoTileId).getDirY()))
        {
            dinoTileId = nextDinoTileId;
            if(dinoTileId == 0) {
                dinoDirX = 0;
                dinoDirY = 0;
            }else {
                dinoDirX = trackTiles.get(dinoTileId).getDirX();
                dinoDirY = trackTiles.get(dinoTileId).getDirY();
            }
        }
    }

    private void drawPlayer() {
        Bitmap b= BitmapFactory.decodeResource(resources, R.mipmap.runman);
        if (playerDirX == 1 || playerDirX == 0) {
            b = Bitmap.createScaledBitmap(b, 100, 100, false);
            Log.d("Test Direction", "X Direction: Right: " + playerDirX);
            canvas.drawBitmap(b, playerX - 50*scale_width, playerY - 80*scale_height, null);
        }
        else {
            Log.d("Test Direction", "X Direction: Right: " + playerDirX);
            Matrix m = new Matrix();
            m.postScale(scale_width, scale_height);
            m.postRotate(180);
            Bitmap rescaledBit = Bitmap.createBitmap(b, 0,0, 100, 100, m, true);
            canvas.drawBitmap(rescaledBit, playerX - 50*scale_width, playerY - 80*scale_height, null);
        }

        float playerDistance = (float) Player.getInstance().getDistance();
        for(int i = 0; i < trackTiles.size(); i++) {
            Tile tile = trackTiles.get(i);
            float tileDistance = tile.getLength() * distancePerPixel;
            if(playerDistance > tileDistance) {
                playerDistance = playerDistance - tileDistance;
            } else {
                float pixelOnTile = playerDistance / distancePerPixel;
                playerX = tile.getX() + tile.getDirX() * pixelOnTile;
                playerY = tile.getY() + tile.getDirY() * pixelOnTile;
                playerDirX = tile.getDirX();
                playerDirY = tile.getDirY();
                break;
            }
        }

        //System.out.println("======= Player Position ============ " + playerX + ", " + playerY);
//        Log.d("Player Distance ===", "" + playerDistance);
    }

    private void drawDinosaur() {
        Bitmap b= BitmapFactory.decodeResource(resources, Dinosaur.getInstance().getImageId());
        b = Bitmap.createScaledBitmap(b, 100, 100, false);
        canvas.drawBitmap(b, dinoX - 50*scale_width, dinoY - 80*scale_height, null);

        if(Dinosaur.getInstance().getStunned() == false) {

            if (Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart()) {
                dinoX += dinoDirX * Dinosaur.getInstance().getSpeed() / distancePerPixel * deltaTime / 1000.0;
                dinoY += dinoDirY * Dinosaur.getInstance().getSpeed()  / distancePerPixel * deltaTime / 1000.0;
                Dinosaur.getInstance().setDistance(Dinosaur.getInstance().getDistance() +
                        Dinosaur.getInstance().getSpeed() * deltaTime / 1000);
            }

        }
    }

    private boolean matchDirection(float aX, float aY, float bX, float bY) {
        return (Math.abs(aX - bX) < 0.0001 && Math.abs(aY - bY) < 0.0001);
    }

    private void drawTiles() {
        Paint p = new Paint();
        p.setColor(Color.RED);
        for (int i = 0; i < trackTiles.size(); i++) {
            canvas.drawCircle(trackTiles.get(i).getX(), trackTiles.get(i).getY(), 8, p);
        }
    }

    private float getStatusBarHeight() {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result*scale_height;
    }

}

