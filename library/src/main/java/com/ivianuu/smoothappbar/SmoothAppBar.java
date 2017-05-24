package com.ivianuu.smoothappbar;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @author Manuel Wrage (IVIanuu)
 */
public class SmoothAppBar {

    private final RecyclerView mRecyclerView;
    private final LinearLayoutManager mLayoutManager;
    private final AppBarLayout mAppBar;

    private boolean mScrolledUp = false;
    private int mLastOffset;

    public SmoothAppBar(@NonNull RecyclerView recyclerView, @NonNull AppBarLayout appBar) {
        if (!LinearLayoutManager.class.isInstance(recyclerView.getLayoutManager())) {
            throw new IllegalArgumentException("layout manager has to be a linearlayoutmanager");
        }

        mRecyclerView = recyclerView;
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        mAppBar = appBar;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // check if we scrolled up
                mScrolledUp = dy < 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 && mScrolledUp) {
                    // time to expand the nav bar
                    mAppBar.setExpanded(true, true);
                }
            }
        });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // check if we scrolled up
                mScrolledUp = mLastOffset < verticalOffset;
                mLastOffset = verticalOffset;
            }
        });

    }

}

