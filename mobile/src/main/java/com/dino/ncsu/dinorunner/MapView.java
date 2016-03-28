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

    MapViewActivity map = new MapViewActivity(); //New Activity constructor

    public MapView(Context context) {
        super(context);
    }

    //All Equipment Logic here

    EquippedItems equipment = EquippedItems.getInstance();

    float EquipmentPos_X = 700;
    float EquipmentPos_Y = 100;


    Bitmap default_head = BitmapFactory.decodeResource(getResources(), R.mipmap.default_head);
    Bitmap default_torso = BitmapFactory.decodeResource(getResources(), R.mipmap.default_torso);
    Bitmap default_pants = BitmapFactory.decodeResource(getResources(), R.mipmap.default_pants);
    Bitmap default_shoes = BitmapFactory.decodeResource(getResources(), R.mipmap.default_shoes);

    float default_head_POS_Y = default_head.getHeight();
    float default_torso_POS_Y = default_head.getHeight();
    float default_pants_POS_Y = default_torso.getHeight() + default_head_POS_Y;
    float default_shoes_POS_Y = default_head.getHeight() + default_pants_POS_Y + default_torso_POS_Y;

    Bitmap equipped_head = BitmapFactory.decodeResource(getResources(), equipment.getHelmet().getImageId());
    Bitmap equipped_chest = BitmapFactory.decodeResource(getResources(), equipment.getChest().getImageId());
    Bitmap equipped_pants = BitmapFactory.decodeResource(getResources(), equipment.getPants().getImageId() );
    Bitmap equipped_shoes = BitmapFactory.decodeResource(getResources(), equipment.getShoes().getImageId() );

    float equipped_head_POS_Y = equipped_head.getHeight();
    float equipped_chest_POS_Y = equipped_head.getHeight();
    float equipped_pants_POS_Y = equipped_chest.getHeight() + equipped_head.getHeight();
    float equipped_shoes_POS_Y = equipped_pants.getHeight() + equipped_chest.getHeight() + equipped_head.getHeight();;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(2.0f);
        paint.setTextSize(60);
        canvas.drawLine(0, 0, 500, 500, paint);
        canvas.drawLine(500, 0, 0, 500, paint);

        //Draws equipment
        canvas.drawBitmap(equipped_head, EquipmentPos_X, EquipmentPos_Y, paint);
        canvas.drawBitmap(equipped_chest, EquipmentPos_X, EquipmentPos_Y + equipped_chest_POS_Y, paint);
        canvas.drawBitmap(equipped_pants, EquipmentPos_X, EquipmentPos_Y + equipped_pants_POS_Y, paint);
        canvas.drawBitmap(equipped_shoes, EquipmentPos_X, EquipmentPos_Y + equipped_shoes_POS_Y, paint);

        canvas.drawText("Player Speed: " + map.getSpeed(), 0, 600, paint);
    }
}
 