package com.jansir.widget.view.taji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.view.View.MeasureSpec.getSize;

/**
 * author: jansir.
 * package: com.jansir.widget.view.taji.
 * date: 2019/5/28.
 */
public class TaiJiView  extends View {

    private Paint paint;
    private float radius;
    private float centerX;
    private float centerY;
    private float degrees;
    private RectF rectF = new RectF();

    private final String TAG=TaiJiView.class.getSimpleName();

    public TaiJiView(Context context) {
        this(context,null);
    }

    public TaiJiView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TaiJiView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        w = w - getPaddingLeft() - getPaddingRight();
        h = h - getPaddingTop() - getPaddingBottom();
        w = Math.min(w, h);
        radius = w / 2f;
        centerX = getPaddingLeft() + radius;
        centerY = getPaddingTop() + radius;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=getSize(widthMeasureSpec,getResources().getDisplayMetrics().widthPixels);
        int heightSize=getSize(widthMeasureSpec,getResources().getDisplayMetrics().heightPixels);
        widthSize=heightSize=Math.min(widthSize,heightSize);
        setMeasuredDimension(widthSize,heightSize);
    }

    private int getSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = 0;
        Log.e(TAG, "getSize: "+MeasureSpec.getSize(measureSpec));
        switch (mode) {
            case MeasureSpec.AT_MOST: {
                size = Math.min(MeasureSpec.getSize(measureSpec), defaultSize);
                break;
            }
            case MeasureSpec.EXACTLY: {
                size = MeasureSpec.getSize(measureSpec);
                break;
            }
            case MeasureSpec.UNSPECIFIED: {
                size = defaultSize;
                break;
            }
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //稍稍留一点间距
        float realRadius = radius - 8;
        float temp1 = realRadius / 2f;
        float temp2 = temp1 / 8f;

        canvas.translate(centerX, centerY);
        canvas.rotate(degrees);

        //绘制边框
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.4f);
        canvas.drawCircle(0, 0, realRadius, paint);

        //绘制左右半圆
        rectF.set(-realRadius, -realRadius, realRadius, realRadius);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);

        paint.setColor(Color.BLACK);
        canvas.drawArc(rectF, 90, 180, true, paint);
        paint.setColor(Color.WHITE);
        canvas.drawArc(rectF, -90, 180, true, paint);

        //绘制上边的白色圆
        canvas.save();
        canvas.translate(0, -temp1);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        canvas.drawCircle(0, 0, temp1, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, temp2, paint);
        canvas.restore();

        //绘制上边的黑色圆
        canvas.save();
        canvas.translate(0, temp1);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, temp1, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, temp2, paint);
        canvas.restore();
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
        postInvalidate();
    }


}
