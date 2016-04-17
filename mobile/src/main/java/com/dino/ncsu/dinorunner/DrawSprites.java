package com.dino.ncsu.dinorunner;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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

    int dinoTileId = 0;
    List<Tile> trackTiles = null;

    public DrawSprites(Canvas canvas, Resources resources) {

        this.canvas = canvas;
        this.resources = resources;

        trackTiles = Track.getInstance().getTileList();
        Tile startTile = trackTiles.get(0);

        playerX = startTile.getX();
        playerY = startTile.getY();
        playerDirX = startTile.getDirX();
        playerDirY = startTile.getDirY();

        dinoX = startTile.getX();
        dinoY = startTile.getY();
        dinoDirX = startTile.getDirX();
        dinoDirY = startTile.getDirY();
    }

    public void draw() {
        checkTilesDino();

        drawDinosaur();

        drawPlayer();
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
        canvas.drawBitmap(b, playerX - 50, playerY - 150, null);

        float distancePerPixel = (float) (Track.getInstance().getTotalDistance() /
                Track.getInstance().getTotalLength());

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
        canvas.drawBitmap(b, dinoX - 50, dinoY - 150, null);

        dinoX += dinoDirX * Dinosaur.getInstance().getSpeed() * 50;
        dinoY += dinoDirY * Dinosaur.getInstance().getSpeed() * 50;
    }

    private boolean matchDirection(float aX, float aY, float bX, float bY) {
        return (Math.abs(aX - bX) < 0.0001 && Math.abs(aY - bY) < 0.0001);
    }

}

