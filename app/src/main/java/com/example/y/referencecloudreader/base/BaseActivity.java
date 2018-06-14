package com.example.y.referencecloudreader.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.y.referencecloudreader.R;
import com.example.y.referencecloudreader.databinding.ActivityBaseBinding;
import com.example.y.referencecloudreader.utils.CommonUtils;
import com.example.y.referencecloudreader.view.statusbar.StatusBarUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity<SV extends ViewDataBinding> extends AppCompatActivity {
    protected ActivityBaseBinding mBinding;
    //布局view
    protected SV bindingView;
    private LinearLayout llProgressBar;
    private LinearLayout llErrorRefresh;
    private AnimationDrawable mAnimationDrawable;

    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        bindingView = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        mBinding.container.addView(bindingView.getRoot());
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorTheme), 0);

        llProgressBar = mBinding.llProgressBar;
        llErrorRefresh = mBinding.llErrorRefresh;
        ImageView img = mBinding.imgProgress;
        //加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        //默认进入页面就加载动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        serToolBar();

        llErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onRefresh();
            }
        });

        bindingView.getRoot().setVisibility(View.GONE);

        initView();

    }

    /**
     * 子类初始化
     */
    protected abstract void initView();

    /**
     * 子类布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 设置toolBar
     */
    protected void serToolBar() {
        setSupportActionBar(mBinding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_back);
        }
        mBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa", "onClick: ");
                onBackPressed();
            }
        });

    }


    /**
     * 设置标题文字
     *
     * @param text 文本
     */
    public void setToolBar(CharSequence text) {
        mBinding.toolBar.setTitle(text);
    }

    protected void showLoading() {
        if (llProgressBar.getVisibility() != View.VISIBLE) {
            llProgressBar.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
        if (llErrorRefresh.getVisibility() != View.GONE) {
            llErrorRefresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (llErrorRefresh.getVisibility() != View.GONE) {
            llErrorRefresh.setVisibility(View.GONE);
        }
        if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
            bindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (llErrorRefresh.getVisibility() != View.VISIBLE) {
            llErrorRefresh.setVisibility(View.VISIBLE);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeSubscription();
    }

    public void removeSubscription() {
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
