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

    private boolean mScrolledUp = false;
    private int mLastOffset;

    public static SmoothAppBar with(@NonNull RecyclerView recyclerView, @NonNull AppBarLayout appBar) {
        return new SmoothAppBar(recyclerView, appBar);
    }

    private SmoothAppBar(RecyclerView recyclerView, final AppBarLayout appBar) {
        if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("layout manager has to be a linearlayoutmanager");
        }

        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // check if we had scrolled up
                mScrolledUp = dy < 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && layoutManager.findFirstCompletelyVisibleItemPosition() == 0 && mScrolledUp) {
                    // time to expand the nav bar
                    appBar.setExpanded(true, true);
                }
            }
        });

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // check if we had scrolled up
                mScrolledUp = mLastOffset < verticalOffset;
                mLastOffset = verticalOffset;
            }
        });

    }

}

