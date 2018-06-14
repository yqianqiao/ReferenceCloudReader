package com.example.xrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by y on 2018/6/8.
 */

public class XRecyclerView extends RecyclerView {
    //是否下拉刷新
    private boolean pullRefreshEnablad = true;
    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mFootViews = new SparseArray<>();

    private YunRefreshHeader mRefreshHeader;
    private boolean isOther; // 是否是额外添加FooterView


    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (pullRefreshEnablad) {
            YunRefreshHeader refreshHeader = new YunRefreshHeader(context);
            mHeaderViews.put(0, refreshHeader);
            mRefreshHeader = refreshHeader;
        }

        LoadingMoreFooter footView = new LoadingMoreFooter(context);
        addFootView(footView, false);
        mFootViews.get(0).setVisibility(GONE);
    }

    /**
     * 提供外部添加view ，使用标识
     *
     * @param view    需要添加的View
     * @param isOther 是否是额外添加FooterView
     */
    public void addFootView(View view, boolean isOther) {
        mFootViews.clear();
        mFootViews.put(0, view);
        this.isOther = isOther;
    }

    /**
     * 相当于加一个空白头布局：
     * 只有一个目的：为了滚动条显示在最顶端
     * 因为默认加了刷新头布局，不处理滚动条会下移。
     * 和 setPullRefreshEnabled(false) 一块儿使用
     * 使用下拉头时，此方法不应被使用！
     */
    public void clearHeader() {
        mHeaderViews.clear();
        float scale = getContext().getResources().getDisplayMetrics().density;
        int height = (int) (1.0f * scale + 0.5f);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        View view = new View(getContext());
        view.setLayoutParams(params);
        mHeaderViews.put(0, view);
    }

    /**
     * 添加一个头布局
     * @param view 需要添加的view
     */
    public void addHeaderView(View view) {
        if (pullRefreshEnablad && !(mHeaderViews.get(0) instanceof YunRefreshHeader)) {
            YunRefreshHeader refreshHeader = new YunRefreshHeader(getContext());
            mHeaderViews.put(0, refreshHeader);
            mRefreshHeader = refreshHeader;
        }
        mHeaderViews.put(mHeaderViews.size(), view);
    }

    @Override
    public void setAdapter(Adapter adapter) {

        super.setAdapter(adapter);
    }
}
