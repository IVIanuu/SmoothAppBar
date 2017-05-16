package com.ivianuu.smoothappbar;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Author IVIanuu.
 */

public class SmoothAppBar {

    private final RecyclerView mRecyclerView;
    private final LinearLayoutManager mLayoutManager;
    private final AppBarLayout mAppBar;

    private boolean mScrolledUp = false;
    private int mLastOffset;

    public SmoothAppBar(RecyclerView recyclerView, LinearLayoutManager layoutManager, AppBarLayout appBar) {
        if (recyclerView == null) {
            throw new IllegalArgumentException("recyclerview cannot be null");
        } else if (layoutManager == null) {
            throw new IllegalArgumentException("layout manager cannot be null");
        } else if (appBar == null) {
            throw new IllegalArgumentException("app bar cannot be null");
        }

        mRecyclerView = recyclerView;
        mLayoutManager = layoutManager;
        mAppBar = appBar;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrolledUp = dy < 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 && mScrolledUp)
                        mAppBar.setExpanded(true, true);
            }
        });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mScrolledUp = mLastOffset < verticalOffset;
                mLastOffset = verticalOffset;
            }
        });

    }

}

