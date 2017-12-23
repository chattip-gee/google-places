package com.fireoneone.android.placesapp.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Chattip Soontaku.
 */

public class RecyclerViewCustom extends RecyclerView {
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    private boolean isLoading = false;
    private boolean enableLoadMore = true;
    private OnLoadMoreListener onLoadMoreListener;

    public RecyclerViewCustom(Context context) {
        super(context);
    }

    public RecyclerViewCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewCustom(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void onLoadMore(final OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (dy > 0 && enableLoadMore) {
            if (getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount && onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            } else if (getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager layoutManager = (GridLayoutManager) getLayoutManager();
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount && onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
