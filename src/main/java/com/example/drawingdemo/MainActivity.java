package com.example.drawingdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Map<String, Float>> list = new ArrayList<Map<String, Float>>();
    private ArrayList<Map<String, Integer>> colList = new ArrayList<Map<String, Integer>>();
    private int cc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();
        setContentView(new DrawView2(this));
    }

    private class DrawView extends View {
        public DrawView(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas c) {
            super.onDraw(c);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            // make the entire canvas white
            paint.setColor(Color.WHITE);
            c.drawPaint(paint);

            // Draw 2 diagonal lines
            paint.setAntiAlias(true);
            paint.setColor(Color.LTGRAY);
            paint.setStrokeWidth(10);
            c.drawLine(0, 0, getWidth() - 1, getHeight() - 1, paint);
            c.drawLine(getWidth() - 1, 0, 0, getHeight() - 1, paint);

            // Draw a Blue rectangle
            paint.setColor(Color.BLUE);
            c.drawRect(20, 30, 300, 200, paint);

            //Draw a triangle in Red stroke
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            Path path = new Path();
            path.moveTo(50, getHeight() / 2);
            path.lineTo(150, getHeight() / 2);
            path.lineTo(100, getHeight() / 2 - 70);
            path.close();
            c.drawPath(path, paint);

            // Draw a transparent yellow circle
            paint.setColor(Color.argb(150, 255, 255, 0));
            paint.setStyle(Paint.Style.FILL);
            c.drawCircle(300, 200, 90, paint); // x=300, y=200 radius=90


            // Draw a Green arc
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            RectF rec = new RectF(400, 200, 900, 600);
            c.drawArc(rec, 0, 240, true, paint);

            // Draw Black text
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(60);
            paint.setTypeface(Typeface.SERIF);
            c.drawText("Text Drawing 1", 150, 600, paint);
            paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
            c.drawText("Text Drawing 2", 150, 700, paint);
        }
    }

    private class DrawView2 extends View {
        private float startX, startY, endX, endY, deviceX, drawY;
        private int color;

        public DrawView2(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas c) {
            super.onDraw(c);
            Paint paint = new Paint();
            DisplayMetrics dm = getResources().getDisplayMetrics();
            deviceX = dm.widthPixels;
            drawY = dm.heightPixels * 1 / 10;

            // make the entire canvas white
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            c.drawPaint(paint);

            // draw rectangle
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            Map<String, Integer> map = new HashMap<String, Integer>();
            if (startX >= 0 && startY >= 0 && startX <= deviceX * 1 / 7 && startY <= drawY) {
                color = Color.RED;
            } else if (startX >= deviceX * 1 / 7 && startY >= 0 && startX <= deviceX * 2 / 7 && startY <= drawY) {
                color = Color.GREEN;
            } else if (startX >= deviceX * 2 / 7 && startY >= 0 && startX <= deviceX * 3 / 7 && startY <= drawY) {
                color = Color.BLUE;
            }

            paint.setColor(color);
            c.drawRect(startX, startY, endX, endY, paint);
            endX = 0;
            endY = 0;
            map.put("color", color);
            if (cc < list.size()) {
                cc++;
                colList.add(map);
            }


            for (int i = 0; i < list.size(); i++) {
                paint.setColor(colList.get(i).get("color"));
                c.drawRect(list.get(i).get("startX"), list.get(i).get("startY"), list.get(i).get("endX"), list.get(i).get("endY"), paint);
            }

            // draw 3 rectangles
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            c.drawRect(0, 0, deviceX * 1 / 7, drawY, paint);

            paint.setColor(Color.GREEN);
            c.drawRect(deviceX * 1 / 7, 0, deviceX * 2 / 7, drawY, paint);

            paint.setColor(Color.BLUE);
            c.drawRect(deviceX * 2 / 7, 0, deviceX * 3 / 7, drawY, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLACK);
            c.drawRect(0, 0, deviceX, drawY, paint);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();   // Where is the screen touched?
            float eventY = event.getY();
            Map<String, Float> map = new HashMap<String, Float>();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = eventX;
                    startY = eventY;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    endX = eventX;
                    endY = eventY;
                    break;
                case MotionEvent.ACTION_UP:
                    map.put("startX", startX);
                    map.put("startY", startY);
                    map.put("endX", endX);
                    map.put("endY", endY);
                    list.add(map);
                    break;
                default:
                    return false;
            }
            // Schedules a repaint.
            invalidate();

            return true;
        }
    }


}
