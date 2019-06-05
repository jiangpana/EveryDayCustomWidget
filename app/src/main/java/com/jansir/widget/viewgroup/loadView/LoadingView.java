package com.jansir.widget.viewgroup.loadView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.jansir.widget.R;

/**
 * author: jansir.
 * package: com.jansir.widget.viewgroup.loadView.
 * date: 2019/6/5.
 */
public class LoadingView extends LinearLayout {

    private ShapeChangeView mShapeChangeView;
    private View mShadowView;
    //动画的距离
    private int translateDistance = 300;
    //动画的时间
    private long animateTime = 400;
    //动画是否停止
    private boolean isStopAnimation = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();

    }

    private void initLayout() {
        inflate(getContext(), R.layout.layout_loading_view, this);
        mShapeChangeView = findViewById(R.id.shapeChangeView);
        mShadowView = findViewById(R.id.shadowView);
        //开启动画
        post(this::startFallAnimator);
    }

    //下落的动画
    private void startFallAnimator() {
        if (isStopAnimation) {
            return;
        }
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mShapeChangeView,
                "translationY", 0, translateDistance);
        translateAnimator.setInterpolator(new AccelerateInterpolator());
        //阴影缩小的动画
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView,
                "scaleX", 1f, 0.4f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimator, scaleAnimator);
        animatorSet.setDuration(animateTime);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShapeChangeView.changeShape();
                startUpAnimation();
            }
        });
        animatorSet.start();
    }

    //上升动画
    private void startUpAnimation() {
        if (isStopAnimation) {
            return;
        }
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mShapeChangeView,
                "translationY", translateDistance, 0);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.4f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimator, scaleAnimator);
        animatorSet.setDuration(animateTime);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //弹起时旋转
                startRotateAnimation();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startFallAnimator();
            }
        });
        animatorSet.start();
    }

    //旋转动画
    private void startRotateAnimation() {
        if (isStopAnimation) {
            return;
        }
        ObjectAnimator objectAnimator = null;
        switch (mShapeChangeView.getCusShape()) {
            case CIRCLE:
            case RECTANGLE:
                objectAnimator = ObjectAnimator.ofFloat(mShapeChangeView,
                        "rotation", 0, 180);
                break;
            case TRIANGLE:
                objectAnimator = ObjectAnimator.ofFloat(mShapeChangeView,
                        "rotation", 0, -120);
                break;


        }
        objectAnimator.setDuration(animateTime);
        objectAnimator.start();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);
        //清楚View的动画
        mShapeChangeView.clearAnimation();
        mShadowView.clearAnimation();

        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            //将view从父布局移除
            parent.removeView(this);
            //移除自身所有的view
            removeAllViews();
        }
        isStopAnimation = true;
    }
}
