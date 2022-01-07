package com.sownt.awesomeclass.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.sownt.awesomeclass.R;
import com.sownt.awesomeclass.model.Lecture;
import com.sownt.awesomeclass.ui.viewholder.DateTimeViewHolder;
import com.sownt.awesomeclass.ui.viewholder.LectureViewHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public abstract class HeaderAdapter extends RecyclerView.Adapter<LectureViewHolder>
        implements StickyRecyclerHeadersAdapter<DateTimeViewHolder> {

    protected List<Lecture> lectures;

    @Override
    public long getHeaderId(int position) {
        return lectures.get(position).getStart().get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public DateTimeViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new DateTimeViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_datetime, parent, false)
        );
    }

    @Override
    public void onBindHeaderViewHolder(DateTimeViewHolder dateTimeViewHolder, int i) {
        if (lectures == null) return;
        int dow = lectures.get(i).getStart().get(Calendar.DAY_OF_WEEK);
        String weekday = Calendar.getInstance().getDisplayName(
                dow,
                Calendar.SHORT,
                Locale.US
        );
        dateTimeViewHolder.setWeekday(weekday);
        dateTimeViewHolder.setDate(lectures.get(i).getStart().get(Calendar.DAY_OF_MONTH));
    }
}
