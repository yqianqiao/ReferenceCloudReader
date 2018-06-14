package com.example.xrecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by y on 2018/6/8.
 */

public class WrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_REFRESH_HEADER = -5;
    private static final int TYPE_HEADER = -4;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFootViews;
    private RecyclerView.Adapter adapter;

    private int headerPosition = 1;

    public WrapAdapter(SparseArray<View> mHeaderViews, SparseArray<View> mFootViews, RecyclerView.Adapter adapter) {
        this.mHeaderViews = mHeaderViews;
        this.mFootViews = mFootViews;
        this.adapter = adapter;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeader(position) || isFooter(position)
                            ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams
                && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) params;
            lp.setFullSpan(true);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_REFRESH_HEADER) {
            return new SimpleViewHolder(mHeaderViews.get(0));
        } else if (viewType == TYPE_HEADER) {
            return new SimpleViewHolder(mHeaderViews.get(headerPosition++));
        } else if (viewType == TYPE_FOOTER) {
            return new SimpleViewHolder(mFootViews.get(0));
        }
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)) {
            return;
        }
        int adjPosition = position - getHeadersCount();
        int adapterCount;
        if (adapter != null) {
            adapterCount = adapter.getItemCount();
            if (adjPosition < adapterCount) {
                adapter.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    @Override
    public int getItemCount() {

        return adapter != null ? getHeadersCount() + getFootersCount() + adapter.getItemCount() :
                getHeadersCount() + getFootersCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isRefreshHeader(position)) {
            return TYPE_REFRESH_HEADER;
        }
        if (isHeader(position)) {
            return TYPE_HEADER;
        }
        if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int adjPosition = position - getHeadersCount();
        int adapterCount;
        if (adapter != null) {
            adapterCount = adapter.getItemCount();
            if (adjPosition < adapterCount) {
                return adapter.getItemViewType(adjPosition);
            }
        }
        return TYPE_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        if (adapter != null && position >= getHeadersCount()) {
            int adjPosition = position - getHeadersCount();
            int adapterCount = adapter.getItemCount();
            if (adjPosition < adapterCount) {
                return adapter.getItemId(adjPosition);
            }
        }
        return -1;
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (adapter != null) {
            super.unregisterAdapterDataObserver(observer);
        }
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (adapter != null) {
            super.registerAdapterDataObserver(observer);
        }
    }

    public boolean isRefreshHeader(int position) {
        return position == 0;
    }

    public boolean isHeader(int position) {
        return position >= 0 && position < mHeaderViews.size();
    }

    public boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - mFootViews.size();
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }


    private class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
