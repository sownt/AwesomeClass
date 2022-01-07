package com.sownt.awesomeclass.ui.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

public class StickyDecoration extends StickyRecyclerHeadersDecoration {
    public StickyDecoration(StickyRecyclerHeadersAdapter adapter) {
        super(adapter);
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    }
}
