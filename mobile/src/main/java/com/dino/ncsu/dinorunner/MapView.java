package com.dino.ncsu.dinorunner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by luyao on 3/3/2016.
 */
public class MapView extends View {

    Paint paint = new Paint();

    public MapView(Context context) {
        super(context);
    }

    //All Equipment Logic here

    EquippedItems equipment = EquippedItems.getInstance();

    //Equipment Location on Canvas
    float EquipmentPos_X = 640;
    float EquipmentPos_Y = 70;

    //Equipment Frame Location on Canvas
    float EquipmentFramePos_X = 600;
    float EquipmentFramePos_Y = 50;


    Bitmap default_head = BitmapFactory.decodeResource(getResources(), R.mipmap.default_head);
    Bitmap default_torso = BitmapFactory.decodeResource(getResources(), R.mipmap.default_chest);
    Bitmap default_pants = BitmapFactory.decodeResource(getResources(), R.mipmap.default_pants);
    Bitmap default_shoes = BitmapFactory.decodeResource(getResources(), R.mipmap.default_shoes);

    float default_head_POS_Y = default_head.getHeight();
    float default_torso_POS_Y = default_head.getHeight();
    float default_pants_POS_Y = default_torso.getHeight() + default_head_POS_Y;
    float default_shoes_POS_Y = default_head.getHeight() + default_pants_POS_Y + default_torso_POS_Y;

    //Bitmap for frame:
    Bitmap character_frame = BitmapFactory.decodeResource(getResources(), R.mipmap.frame_character);

    Bitmap equipped_head = BitmapFactory.decodeResource(getResources(), equipment.getHelmet().getImageId());
    Bitmap equipped_chest = BitmapFactory.decodeResource(getResources(), equipment.getChest().getImageId());
    Bitmap equipped_pants = BitmapFactory.decodeResource(getResources(), equipment.getPants().getImageId() );
    Bitmap equipped_shoes = BitmapFactory.decodeResource(getResources(), equipment.getShoes().getImageId());


    float equipped_head_POS_Y = equipped_head.getHeight();
    float equipped_chest_POS_Y = equipped_head.getHeight();
    float equipped_pants_POS_Y = equipped_chest.getHeight() + equipped_head.getHeight();
    float equipped_shoes_POS_Y = equipped_pants.getHeight() + equipped_chest.getHeight() + equipped_head.getHeight();;


    //Timer variables for Frame refresh
    int framesPerSecond = 60;

    long startTime;

    @Override
    protected void onDraw(Canvas canvas) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(2.0f);
        paint.setTextSize(60);
        canvas.drawLine(0, 0, 500, 500, paint);
        canvas.drawLine(500, 0, 0, 500, paint);

        //Code for Drawing
        drawEquipment(canvas);




    }

    //This method draws all the equipment
    public void drawEquipment(Canvas canvas) {
        //Draws equipment screen
        canvas.drawBitmap(character_frame, EquipmentFramePos_X, EquipmentFramePos_Y, paint);

        //Draws equipment
        canvas.drawBitmap(equipped_head, EquipmentPos_X, EquipmentPos_Y, paint);
        canvas.drawBitmap(equipped_chest, EquipmentPos_X, EquipmentPos_Y + equipped_chest_POS_Y, paint);
        canvas.drawBitmap(equipped_pants, EquipmentPos_X, EquipmentPos_Y + equipped_pants_POS_Y, paint);
        canvas.drawBitmap(equipped_shoes, EquipmentPos_X, EquipmentPos_Y + equipped_shoes_POS_Y, paint);
    }

    public void redraw() {

    }


}
 