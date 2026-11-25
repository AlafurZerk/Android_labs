package com.example.lab1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Данил - Вариант 8");

        FrameLayout drawContainer = findViewById(R.id.drawContainer);
        drawContainer.addView(new DrawView(this));
    }

    class DrawView extends View {
        private Paint paint;
        private Paint strokePaint;
        private Paint textPaint;
        private Paint infoPaint;
        private Paint framePaint;
        private Paint wheelPaint;
        private Paint accentPaint;
        private Rect rect;

        public DrawView(Context context) {
            super(context);
            paint = new Paint();
            paint.setAntiAlias(true);

            strokePaint = new Paint();
            strokePaint.setAntiAlias(true);
            strokePaint.setStyle(Paint.Style.STROKE);

            textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(36);
            textPaint.setColor(Color.rgb(33, 33, 33));
            textPaint.setFakeBoldText(true);

            infoPaint = new Paint();
            infoPaint.setAntiAlias(true);
            infoPaint.setTextAlign(Paint.Align.CENTER);
            infoPaint.setTextSize(26);
            infoPaint.setColor(Color.rgb(85, 85, 85));

            framePaint = new Paint();
            framePaint.setAntiAlias(true);
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setColor(Color.rgb(211, 47, 47));
            framePaint.setStrokeWidth(8);
            framePaint.setStrokeCap(Paint.Cap.ROUND);
            framePaint.setStrokeJoin(Paint.Join.ROUND);

            wheelPaint = new Paint();
            wheelPaint.setAntiAlias(true);
            wheelPaint.setColor(Color.rgb(33, 33, 33));

            accentPaint = new Paint();
            accentPaint.setAntiAlias(true);

            rect = new Rect();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.rgb(250, 250, 250));

            int width = canvas.getWidth();
            int cx = width / 2;

            int squareSide = 240;
            int squareTop = 70;
            int squareLeft = cx - squareSide / 2;
            int squareRight = squareLeft + squareSide;
            int squareBottom = squareTop + squareSide;

            canvas.drawText("Задание 3: Зеленый квадрат (S = P)", cx, squareTop - 20, textPaint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.rgb(46, 125, 50));
            rect.set(squareLeft, squareTop, squareRight, squareBottom);
            canvas.drawRect(rect, paint);

            strokePaint.setColor(Color.rgb(27, 94, 32));
            strokePaint.setStrokeWidth(5);
            canvas.drawRect(rect, strokePaint);

            canvas.drawText("a = 4 ед. (240 px), S = 16 ед.², P = 16 ед.", cx, squareBottom + 35, infoPaint);

            int bikeTitleY = squareBottom + 95;
            canvas.drawText("Задание 4: Велосипед (ломаные линии)", cx, bikeTitleY, textPaint);

            float cy = bikeTitleY + 220;
            float xr = cx - 150;
            float yr = cy;
            float xf = cx + 150;
            float yf = cy;
            float xc = cx - 20;
            float yc = cy;
            float xs = cx - 65;
            float ys = cy - 130;
            float xh = cx + 95;
            float yh = cy - 130;

            wheelPaint.setStyle(Paint.Style.STROKE);
            wheelPaint.setStrokeWidth(12);
            canvas.drawCircle(xr, yr, 75, wheelPaint);
            canvas.drawCircle(xf, yf, 75, wheelPaint);

            accentPaint.setStyle(Paint.Style.STROKE);
            accentPaint.setColor(Color.rgb(189, 189, 189));
            accentPaint.setStrokeWidth(3);
            canvas.drawCircle(xr, yr, 67, accentPaint);
            canvas.drawCircle(xf, yf, 67, accentPaint);

            for (int i = 0; i < 8; i++) {
                double angle = i * Math.PI / 4.0;
                float dx = (float) (67 * Math.cos(angle));
                float dy = (float) (67 * Math.sin(angle));
                canvas.drawLine(xr, yr, xr + dx, yr + dy, accentPaint);
                canvas.drawLine(xf, yf, xf + dx, yf + dy, accentPaint);
            }

            accentPaint.setStyle(Paint.Style.FILL);
            accentPaint.setColor(Color.rgb(66, 66, 66));
            canvas.drawCircle(xr, yr, 8, accentPaint);
            canvas.drawCircle(xf, yf, 8, accentPaint);

            accentPaint.setColor(Color.rgb(120, 144, 156));
            accentPaint.setStyle(Paint.Style.STROKE);
            accentPaint.setStrokeWidth(2);
            canvas.drawLine(xc, yc - 18, xr, yr - 8, accentPaint);
            canvas.drawLine(xc, yc + 18, xr, yr + 8, accentPaint);

            accentPaint.setStyle(Paint.Style.FILL);
            accentPaint.setColor(Color.rgb(255, 179, 0));
            canvas.drawCircle(xc, yc, 20, accentPaint);
            accentPaint.setStyle(Paint.Style.STROKE);
            accentPaint.setColor(Color.rgb(230, 81, 0));
            accentPaint.setStrokeWidth(3);
            canvas.drawCircle(xc, yc, 20, accentPaint);

            accentPaint.setColor(Color.rgb(55, 71, 79));
            accentPaint.setStrokeWidth(5);
            canvas.drawLine(xc, yc, xc + 18, yc + 18, accentPaint);
            canvas.drawLine(xc + 12, yc + 24, xc + 24, yc + 12, accentPaint);
            canvas.drawLine(xc, yc, xc - 18, yc - 18, accentPaint);
            canvas.drawLine(xc - 24, yc - 12, xc - 12, yc - 24, accentPaint);

            Path framePath = new Path();
            framePath.moveTo(xr, yr);
            framePath.lineTo(xc, yc);
            framePath.lineTo(xs, ys);
            framePath.lineTo(xr, yr);
            framePath.moveTo(xs, ys);
            framePath.lineTo(xh, yh);
            framePath.lineTo(xc, yc);
            canvas.drawPath(framePath, framePaint);

            canvas.drawLine(xh, yh, xf, yf, framePaint);
            canvas.drawLine(xc, yc, xs - 5, ys - 20, framePaint);
            canvas.drawLine(xh, yh, xh + 5, yh - 25, framePaint);

            wheelPaint.setStyle(Paint.Style.STROKE);
            wheelPaint.setStrokeWidth(7);
            Path barPath = new Path();
            barPath.moveTo(xh - 15, yh - 20);
            barPath.lineTo(xh + 10, yh - 25);
            barPath.lineTo(xh + 35, yh - 15);
            canvas.drawPath(barPath, wheelPaint);

            wheelPaint.setStyle(Paint.Style.FILL);
            Path saddle = new Path();
            saddle.moveTo(xs - 35, ys - 20);
            saddle.lineTo(xs + 25, ys - 20);
            saddle.lineTo(xs + 30, ys - 14);
            saddle.lineTo(xs - 35, ys - 14);
            saddle.close();
            canvas.drawPath(saddle, wheelPaint);
        }
    }
}
