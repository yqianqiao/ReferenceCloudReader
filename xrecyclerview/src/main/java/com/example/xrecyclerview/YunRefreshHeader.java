package com.example.xrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by y on 2018/6/8.
 */

public class YunRefreshHeader extends LinearLayout implements BaseRefreshHeader {
    private Context mContext;
    private AnimationDrawable animationDrawable;
    private TextView msg;
    private int measuredHeight;
    private LinearLayout mContainer;
    private int mState = STATE_NORMAL;

    public YunRefreshHeader(Context context) {
        super(context);
    }

    public YunRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public YunRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.kaws_refresh_header, this, true);
        ImageView img = findViewById(R.id.img);
        animationDrawable = (AnimationDrawable) img.getDrawable();
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        msg = findViewById(R.id.msg);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        measuredHeight = getMeasuredHeight();
        mContainer = findViewById(R.id.container);
        mContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    @Override
    public void onMove(float delta) {
        if (getVisiableHeight() > 0 || delta > 0) {
            setVisiableHeight((int) (delta + getVisiableHeight()));
            if (mState <= STATE_RELEASE_TO_REFRESH) {// 未处于刷新状态，更新箭头
                if (getVisiableHeight() > measuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisiableHeight();
        if (height == 0) isOnRefresh = false;
        if (getVisiableHeight() > measuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        int destHeight = 0;
        if (mState == STATE_REFRESHING) {
            destHeight = measuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisiableHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisiableHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 500);
    }

    public void reset() {
        smoothScrollTo(0);
        setState(STATE_NORMAL);
    }

    private void setState(int state) {
        if (state == mState) return;
        switch (state) {
            case STATE_NORMAL:
                if (animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }
                msg.setText("下拉刷新...");
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (!animationDrawable.isRunning()) {
                    animationDrawable.start();
                }
                msg.setText("释放刷新...");
                break;
            case STATE_REFRESHING:
                msg.setText("正在刷新...");
                break;
            case STATE_DONE:
                msg.setText("刷新完成...");
                break;
        }
        mState = state;
    }

    private void setVisiableHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    @Override
    public int getVisiableHeight() {
        return mContainer.getHeight();
    }
}
