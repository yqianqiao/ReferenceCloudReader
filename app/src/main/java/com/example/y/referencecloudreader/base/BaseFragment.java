package com.example.y.referencecloudreader.base;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.y.referencecloudreader.R;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by y on 2018/5/30.
 */

public abstract class BaseFragment<SV extends ViewDataBinding> extends Fragment {
    //布局view
    protected SV bindingView;
    //内容布局
    protected RelativeLayout mConrainer;
    //fragment是否显示
    protected boolean mIsVisible = false;
    //加载中
    private LinearLayout mLlProgressBar;
    //动画
    private AnimationDrawable mAnimationDrawable;
    //加载失败
    private LinearLayout mRefresh;

    private CompositeSubscription mCompositeSubscription;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ll = inflater.inflate(R.layout.fragment_base, container, false);
        bindingView = DataBindingUtil.inflate(inflater, setContent(), container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        mConrainer = ll.findViewById(R.id.container);
        mConrainer.addView(bindingView.getRoot());
        return ll;
    }

    protected abstract int setContent();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlProgressBar = getView(R.id.ll_progress_bar);
        ImageView img = getView(R.id.img_progress);
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        mRefresh = getView(R.id.ll_error_refresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onRefresh();
            }
        });

        bindingView.getRoot().setVisibility(View.GONE);
    }

    protected <T extends View> T getView(int Id) {
        return (T) getView().findViewById(Id);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {

    }

    /**
     * 加载失败后的操作
     */
    protected void onRefresh() {

    }

    /**
     * 显示时加载数据
     */
    protected void loadData() {

    }

    protected void onVisible() {
        loadData();
    }

    /**
     * 显示加载中状态
     */
    protected void showLoading() {
        if (mLlProgressBar.getVisibility() != View.VISIBLE) {
            mLlProgressBar.setVisibility(View.VISIBLE);
        }
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
        if (mRefresh.getVisibility() != View.GONE) {
            mRefresh.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mRefresh.getVisibility() != View.GONE) {
            mRefresh.setVisibility(View.GONE);
        }
        if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
            bindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLlProgressBar.getVisibility() != View.GONE) {
            mLlProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mRefresh.getVisibility() != View.VISIBLE) {
            mRefresh.setVisibility(View.VISIBLE);
        }
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeSubscription();
    }

    public void addSubscription(Subscription s) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(s);
    }

    public void removeSubscription() {
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

}
