package com.dino.ncsu.dinorunner;

import android.content.Context;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(2.0f);
        canvas.drawLine(0, 0, 500, 500, paint);
        canvas.drawLine(500,0,0,500,paint);

    }
}
 