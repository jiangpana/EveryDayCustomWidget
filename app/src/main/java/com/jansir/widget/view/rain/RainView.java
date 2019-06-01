package com.jansir.widget.view.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * author: jansir.
 * package: com.jansir.widget.view.rain.
 * date: 2019/6/1.
 */
public class RainView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG ="RainView" ;

    //雨滴---线
    private static class Line{
        private float startX;
        private float stopX;
        private float startY;
        private float stopY;
    }

    private SurfaceHolder surfaceHolder;
    //画笔
    private Paint paint;
    //雨滴集合
    private List<Line> lineList=new LinkedList<>();
    private Random random;
    //雨的下落速度
    private static final int DEFAULT_SPEED =30 ;
    //雨的密集程度
    private static final int DEFAULT_DEGREE =30 ;

    private  volatile int degree =DEFAULT_DEGREE;
    private volatile int speed=DEFAULT_SPEED;

    //周期性执行任务
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture<?>  scheduledFuture;



    public RainView(Context context) {
        this(context, null);
    }

    public RainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initPaint();
    }
    private void init() {
        surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        random=new Random();
        scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();

    }

    private void initPaint() {
        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2f);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                int tempDegree = degree;
                int size = lineList.size();
                if (size < tempDegree) {
                    //这里需要逐渐添加Line，才能使得Line的高度参差不齐
                    lineList.add(getRandomLine());
                } else if (size > tempDegree) {
                    Line tempLine = null;
                    for (Line line : lineList) {
                        if (line.startY >= getHeight()) {
                            tempLine = line;
                            break;
                        }
                    }
                    if (tempLine != null) {
                        lineList.remove(tempLine);
                    }
                }
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                for (Line line : lineList) {
                    //重置超出屏幕的 Line 的坐标
                    if (line.startY >= getHeight()) {
                        resetLine(line);
                        continue;
                    }
                    canvas.drawLine(line.startX, line.startY, line.stopX, line.stopY, paint);
                    line.startY = line.startY + speed;
                    line.stopY = line.stopY + speed;
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    };

    private Line getRandomLine() {
        Line line=new Line();
        resetLine(line);
        return line;
    }

    private void resetLine(Line line) {
        line.startX=nextFloat(0,getWidth()- 3.0f);
        line.startY=0;
        //有一点倾斜
        line.stopX=line.startX+nextFloat(3.0f,6.0f);
        line.stopY=line.startY+nextFloat(30.0f,50.0f);

    }

    //返回min 到 max 的随机数值
    private float nextFloat(float min, float max) {
        return min + random.nextFloat()*(max - min);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated: " );
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
            scheduledFuture = null;
        }
        scheduledFuture=scheduledExecutorService.scheduleWithFixedDelay(runnable,300,1000,TimeUnit.MILLISECONDS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged: " );

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed: " );
        if (scheduledFuture != null && !scheduledFuture.isCancelled()){
            scheduledFuture.cancel(false);
            scheduledFuture=null;
        }
        lineList.clear();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }
}
