package com.example.xrecyclerview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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

public class LoadingMoreFooter extends LinearLayout {
    public final static int STATE_LOADING = 0; //正在加载
    public final static int STATE_COMPLETE = 1; //加载完成
    public final static int STATE_NOMORE = 2;

    private TextView mText;
    private AnimationDrawable mAnimationDrawable;
    private ImageView mIvProgress;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    public LoadingMoreFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingMoreFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.yun_refresh_footer, this);
        mText = findViewById(R.id.msg);
        mIvProgress = findViewById(R.id.iv_progress);
        mAnimationDrawable = (AnimationDrawable) mIvProgress.getDrawable();
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                if (!mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.start();
                }
                mIvProgress.setVisibility(GONE);
                mText.setText("努力加载中...");
                setVisibility(VISIBLE);
                break;
            case STATE_COMPLETE:
                if (mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                mText.setText("加载完成...");
                setVisibility(GONE);
                break;
            case STATE_NOMORE:
                if (mAnimationDrawable.isRunning()) {
                    mAnimationDrawable.stop();
                }
                mText.setText("没有更多内容了");
                mIvProgress.setVisibility(GONE);
                setVisibility(VISIBLE);
                break;
        }
    }

    public void reSet() {
        setVisibility(GONE);
    }
}
