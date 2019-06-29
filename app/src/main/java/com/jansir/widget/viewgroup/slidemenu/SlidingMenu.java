package com.jansir.widget.viewgroup.slidemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.jansir.widget.R;

/**
 * author: jansir.
 * package: com.jansir.widget.viewgroup.slidemenu.
 * date: 2019/6/29.
 */
public class SlidingMenu extends HorizontalScrollView {

    private static final String TAG = "SlidingMenu";
    private ViewGroup  mContentView;
    private View mMenuView;
    private int mMenuWidth;
    //手势检测
    private GestureDetector mGestureDetector;
    //菜单是否打开
    private boolean isMenuOpen = false;

    private Context mContext;

    // 给内容添加阴影效果 - 阴影的ImageView
    private ImageView mShadowIv;


    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        float rightPadding = (int) a.getDimension(R.styleable.SlidingMenu_z_sm_rightPadding, dip2px(50));
        a.recycle();
        mMenuWidth = (int) (getScreenWidth() - rightPadding);
        mGestureDetector = new GestureDetector(context, new GestureListener());
        mContext = context;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup container = (ViewGroup) getChildAt(0);
        int containerChildCount = container.getChildCount();
        if (containerChildCount > 2) {
            throw new IllegalStateException("SlidingMenu 根布局LinearLayout下面只允许两个布局,菜单布局和主页内容布局");
        }
        // 给内容添加阴影效果
        // 先new一个主内容布局用来放  阴影和LinearLayout原来的内容布局
        mMenuView = container.getChildAt( 0 );
        mContentView = new FrameLayout(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1,-1);

        View oldContentView = container.getChildAt(1);
        container.removeView(oldContentView);
        //把原来的内容View 和 阴影加到我们新创建的内容布局中
        mContentView.setLayoutParams(layoutParams);
        mContentView.addView(oldContentView);

        // 7.3.1 创建阴影ImageView
        mShadowIv = new ImageView(mContext);
        mShadowIv.setBackgroundColor(Color.parseColor("#99000000"));
        mContentView.addView(mShadowIv);

        // 7.4 把包含阴影的新的内容View 添加到 LinearLayout中
        container.addView(mContentView);

        mMenuView.getLayoutParams().width = mMenuWidth;
        //内容的宽度 = 屏幕的宽度
        mContentView.getLayoutParams().width = getScreenWidth();

        mShadowIv.setOnClickListener(v -> {
            if (isMenuOpen){
                closeMenu();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //处理手指快速滑动
        if (mGestureDetector.onTouchEvent(ev)){
            return mGestureDetector.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int currentScrollX =getScrollX();
                if(currentScrollX > mMenuWidth /2 ){
                    closeMenu();
                }else {
                    openMenu();
                }
            return false;
        }
        return super.onTouchEvent(ev);
    }

    private void openMenu() {
        smoothScrollTo(0, 0);
        isMenuOpen = true;
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        isMenuOpen = false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 布局指定后会从新摆放子布局，当其摆放完毕后，让菜单滚动到不可见状态
        if (changed) {
            scrollTo(mMenuWidth, 0);
        }
    }

    /**
     * 切换菜单的状态
     */
    private void toggleMenu() {
        if(isMenuOpen){
            closeMenu();
        }else{
            openMenu();
        }
    }

    //处理手指快速滑动
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e(TAG, "onFling: " + velocityX);
            //如果菜单打开 并且是向左快速滑动 切换菜单的状态
            // 当手指快速滑动时候回调的方法
            Log.e(TAG,velocityX+"");
            // 5.3.1 如果菜单打开 并且是向左快速滑动 切换菜单的状态
            if(isMenuOpen){
                if(velocityX<0){
                    toggleMenu();
                    return true;
                }
            }else{
                // 5.3.2 如果菜单关闭 并且是向右快速滑动 切换菜单的状态
                if(velocityX>0){
                    toggleMenu();
                    return true;
                }
            }
            return false;
        }
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
