package com.jansir.widget.viewgroup.loadView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.jansir.widget.R;

/**
 * author: jansir.
 * package: com.jansir.widget.viewgroup.loadView.
 * date: 2019/6/5.
 */
public class ShapeChangeView extends View {

    //初始形状
    private Shape mCusShape = Shape.CIRCLE;
    //画笔
    private Paint mPaint;
    private Path mPath;

    //定义形状的枚举类型：圆形，长方形，三角形
    public enum Shape{
        CIRCLE,
        RECTANGLE,
        TRIANGLE
    }


    public ShapeChangeView(Context context) {
        this(context,null);
    }

    public ShapeChangeView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeChangeView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint=new Paint();
        mPath=new Path();
        mPaint.setAntiAlias(true);
        //填充
        mPaint.setStyle(Paint.Style.FILL);
    }

    //获取当前View绘制的形状
    public Shape getCusShape() {
        return mCusShape;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        //取宽高值小的
        setMeasuredDimension(width>height? height:width,width>height? height:width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center=getWidth() /2 ;
        switch (mCusShape){
            case CIRCLE:
                //画圆
                mPaint.setColor(ContextCompat.getColor(getContext(), R.color.green_a700));
                canvas.drawCircle(center,center,center,mPaint);
                break;
            case RECTANGLE:
                mPaint.setColor(ContextCompat.getColor(getContext(),R.color.blue700));
                canvas.drawRect(0,0,getRight(),getBottom(),mPaint);
                break;
            case TRIANGLE:
                //用Path  画三角形
                mPaint.setColor(ContextCompat.getColor(getContext(),R.color.red_700));
                //指定path 的起点
                mPath.moveTo(getWidth()/2,0);
                mPath.lineTo(0, (float) (getWidth()/2 * Math.sqrt(3)));
                mPath.lineTo(getWidth(), (float) (getWidth() /2 * Math.sqrt(3)));
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    //轮询改变当前View绘制的形状
    public void changeShape(){
        switch(mCusShape){
            case CIRCLE:
                mCusShape = Shape.TRIANGLE;
                break;
            case TRIANGLE:
                mCusShape = Shape.RECTANGLE;
                break;
            case RECTANGLE:
                mCusShape = Shape.CIRCLE;
                break;
        }
        invalidate();
    }
}
