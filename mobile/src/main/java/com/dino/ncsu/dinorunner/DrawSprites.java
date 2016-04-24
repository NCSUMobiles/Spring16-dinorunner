package com.dino.ncsu.dinorunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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

    float width;
    float height;
    float scale_width;
    float scale_height;
    float scale_total;
    DisplayMetrics display;

    int dinoTileId = 0;
    List<Tile> trackTiles = null;
    float distancePerPixel = 0;

    long lastTime = 0;
    long deltaTime = 0;

    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    long fps;
    private int frameLengthInMilliseconds = 100;
    long timeThisFrame;
    private int frameWidth = 100;
    private int frameHeight = 100;
    private Rect frameToDraw = new Rect(
            0,
            0,
            frameWidth,
            frameHeight);
    RectF whereToDraw = new RectF(
            frameWidth,
            0,
            dinoX + frameWidth,
            frameHeight);
    Bitmap monster;
    boolean backtrack; //Is monster going back

    public DrawSprites(Canvas canvas, Resources resources) {

        this.canvas = canvas;
        this.resources = resources;

        trackTiles = Track.getInstance().getTileList();

        //Scaling
        display = resources.getDisplayMetrics();
        width = display.widthPixels;
        height = display.heightPixels;
        scale_width = width / 1080;
        scale_height = height / 1776;
        scale_total = (scale_width < scale_height) ? scale_width : scale_height;
        frameWidth *= scale_width;
        frameHeight *= scale_height;
        monster = BitmapFactory.decodeResource(resources, Dinosaur.getInstance().getSpriteSheetId());
        monster = monster.createScaledBitmap(monster, frameWidth * Dinosaur.getInstance().getFrameCount(), frameHeight, false);

        //Re-adjust tiles' (X,Y) by subtracting getStatusBarHeight() from each Y
        for(int i = 0; i < trackTiles.size(); i++) {
            trackTiles.get(i).setY(trackTiles.get(i).getY() - getStatusBarHeight());
        }

        Tile startTile = trackTiles.get(0);
        Player.getInstance().setCurrentTile(startTile);
        Dinosaur.getInstance().setCurrentTile(startTile);

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
        // Capture the current time in milliseconds in startFrameTime
        long startFrameTime = System.currentTimeMillis();

        long currTime = System.currentTimeMillis();
        deltaTime = currTime - lastTime;
        lastTime = currTime;

        checkTilesDino();

        whereToDraw.set((int) dinoX -frameWidth/2*scale_width, (int) dinoY - frameHeight*3/4*scale_height, (int) dinoX + frameWidth/2*scale_height, (int) dinoY + frameHeight/4*scale_height );
        getCurrentFrame();

        drawDinosaur();

        drawPlayer();

        //test
        drawTiles();

        timeThisFrame = System.currentTimeMillis() - startFrameTime;
        if (timeThisFrame >= 1) {
            fps = 1000 / timeThisFrame;
        }
    }

    public void getCurrentFrame(){

        long time  = System.currentTimeMillis();
        if(Dinosaur.getInstance().getStunned() == false) {
            if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = time;
                currentFrame++;
                if (currentFrame >= Dinosaur.getInstance().getFrameCount()) {
                    currentFrame = 0;
                }
            }
        }
        //update the left and right values of the source of
        //the next frame on the spritesheet
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;

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
            Dinosaur.getInstance().setCurrentTile(trackTiles.get(dinoTileId));

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
        Bitmap bm_run_vertical = BitmapFactory.decodeResource(resources, R.drawable.runman_vertical);
        Bitmap bm_run_horizontal = BitmapFactory.decodeResource(resources, R.drawable.runman);
        if (playerDirX == 1) {
            bm_run_horizontal = Bitmap.createScaledBitmap(bm_run_horizontal, 100, 100, false);
            //Log.d("Test Direction", "X Direction: Right: " + playerDirX);
            canvas.drawBitmap(bm_run_horizontal, playerX - 50*scale_width, playerY - 80*scale_height, null);
        }
        else if (playerDirX == 0) {
            bm_run_vertical = Bitmap.createScaledBitmap(bm_run_vertical, 100, 100, false);
            //Log.d("Test Direction", "X Direction: Right: " + playerDirX);
            canvas.drawBitmap(bm_run_vertical, playerX - 50*scale_width, playerY - 80*scale_height, null);
        }
        else {
            //Log.d("Test Direction", "X Direction: Right: " + playerDirX);
            Matrix m = new Matrix();
            m.reset();
            m.postScale(-scale_total, scale_total);
            bm_run_horizontal = Bitmap.createScaledBitmap(bm_run_horizontal, 100, 100, true);
            bm_run_horizontal = Bitmap.createBitmap(bm_run_horizontal, 0, 0, bm_run_horizontal.getWidth(), bm_run_horizontal.getHeight(), m, true );
            canvas.drawBitmap(bm_run_horizontal, playerX - 50*scale_width, playerY - 80*scale_height, null);
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

                Player.getInstance().setCurrentTile(tile); //sets current Tile to I
                Log.d("Current Test", "Current Player Tile Type :" + Player.getInstance().getCurrentTile().getTerrain());
                break;
            }
        }

        //System.out.println("======= Player Position ============ " + playerX + ", " + playerY);
//        Log.d("Player Distance ===", "" + playerDistance);
    }

    private void drawDinosaur() {
        if( Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart()) {
            if (dinoDirX == 1) {
                canvas.drawBitmap(monster, frameToDraw, whereToDraw, null);
            }
            else if (dinoDirX == -1) {
                backtrack = true;
                Matrix m = new Matrix();
                m.reset();
                m.postScale(-scale_total, scale_total);
                Bitmap monster_horizontal = Bitmap.createScaledBitmap(monster, monster.getWidth(), monster.getHeight(), true);
                monster_horizontal = Bitmap.createBitmap(monster_horizontal, 0, 0, monster_horizontal.getWidth(), monster_horizontal.getHeight(), m, true );
                canvas.drawBitmap(monster_horizontal, frameToDraw, whereToDraw, null);
            }
            else if (dinoDirX == 0) {
                if (backtrack == false) {
                    canvas.drawBitmap(monster, frameToDraw, whereToDraw, null);
                }
                else if (backtrack == true) {
                    Matrix m = new Matrix();
                    m.reset();
                    m.postScale(-scale_total, scale_total);
                    Bitmap monster_horizontal = Bitmap.createScaledBitmap(monster, monster.getWidth(), monster.getHeight(), true);
                    monster_horizontal = Bitmap.createBitmap(monster_horizontal, 0, 0, monster_horizontal.getWidth(), monster_horizontal.getHeight(), m, true );
                    canvas.drawBitmap(monster_horizontal, frameToDraw, whereToDraw, null);
                }
            }

        }
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

