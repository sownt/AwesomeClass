package com.sownt.awesomeclass.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.sownt.awesomeclass.R;
import com.sownt.awesomeclass.model.Lecture;
import com.sownt.awesomeclass.ui.viewholder.LectureViewHolder;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LectureAdapter extends HeaderAdapter {
    public LectureAdapter(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LectureViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LectureViewHolder holder, int position) {
        holder.setColor(lectures.get(position).getColor());
        holder.setTitle(lectures.get(position).getName());
        holder.setTime(String.format(Locale.ENGLISH, "%d - %d",
                lectures.get(position).getStart().get(Calendar.HOUR_OF_DAY),
                lectures.get(position).getEnd().get(Calendar.HOUR_OF_DAY)));
    }

    @Override
    public int getItemCount() {
        if (lectures != null) return lectures.size();
        return 0;
    }
}