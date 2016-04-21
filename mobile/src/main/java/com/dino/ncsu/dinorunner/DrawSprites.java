package com.dino.ncsu.dinorunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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

    float dinoX = 0;
    float dinoY = 0;
    float dinoDirX = 0;
    float dinoDirY = 0;

    int dinoTileId = 0;
    List<Tile> trackTiles = null;
    float distancePerPixel = 0;

    long lastTime = 0;
    long deltaTime = 0;

    public DrawSprites(Canvas canvas, Resources resources) {

        this.canvas = canvas;
        this.resources = resources;

        trackTiles = Track.getInstance().getTileList();

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

        float dinoTileNextX = trackTiles.get(nextDinoTileId).getX();
        float dinoTileNextY = trackTiles.get(nextDinoTileId).getY();
        float dist = (dinoTileNextX - dinoX) * (dinoTileNextX - dinoX) +
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
        b = Bitmap.createScaledBitmap(b, 100, 100, false);
        canvas.drawBitmap(b, playerX - 50, playerY - 100, null);

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
                break;
            }
        }

        //System.out.println("======= Player Position ============ " + playerX + ", " + playerY);
//        Log.d("Player Distance ===", "" + playerDistance);
    }

    private void drawDinosaur() {
        Bitmap b= BitmapFactory.decodeResource(resources, Dinosaur.getInstance().getImageId());
        b = Bitmap.createScaledBitmap(b, 100, 100, false);
        canvas.drawBitmap(b, dinoX - 50, dinoY - 100, null);

        if(Dinosaur.getInstance().getStunned() == false) {
        //if(Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart()) {


            if (Player.getInstance().getDistance() >= Dinosaur.getInstance().getHeadStart()) {
                dinoX += dinoDirX * Dinosaur.getInstance().getSpeed() / distancePerPixel * deltaTime / 1000.0;
                dinoY += dinoDirY * Dinosaur.getInstance().getSpeed()  / distancePerPixel * deltaTime / 1000.0;
                Dinosaur.getInstance().setDistance(Dinosaur.getInstance().getDistance() +
                        Dinosaur.getInstance().getSpeed() * deltaTime / 1000);
            }

        }
        System.out.println("==== Status Bar height ==== " + getStatusBarHeight());
        //System.out.println("==== Dinosaur Stunned ==== " + Dinosaur.getInstance().getStunned());
        //System.out.println("==== Distance ==== " + Dinosaur.getInstance().getDistance() + ", " + Player.getInstance().getDistance());
        //System.out.println("======= Dinosaur Position ============ " + dinoX + ", " + dinoY + ", " + distancePerPixel + ", " + deltaTime);
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

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}

