package com.sownt.awesomeclass.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.sownt.awesomeclass.R;
import com.sownt.awesomeclass.interfaces.Callback;
import com.sownt.awesomeclass.model.Lecture;
import com.sownt.awesomeclass.ui.viewholder.LectureViewHolder;
import com.sownt.awesomeclass.utils.Formatter;
import com.sownt.awesomeclass.utils.LectureDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class LectureAdapter extends HeaderAdapter {
    protected LectureDatabase database;
    private Callback<String> target;
    private Callback<Boolean> onEmpty;
    private ChildEventListener eventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if (lectures == null) lectures = new ArrayList<>();
            String json = snapshot.getValue(String.class);
            Lecture item = Lecture.fromJson(json);
            lectures.add(item);
            notifyLecturesChanged();
            int i = lectures.indexOf(item);
            notifyItemInserted(i);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            String json = snapshot.getValue(String.class);
            int i = lectures.indexOf(Lecture.fromJson(json));
            lectures.remove(i);
            notifyLecturesChanged();
            notifyItemRemoved(i);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w("AwesomeClass", "postComments:onCancelled", error.toException());
        }
    };

    public LectureAdapter() {
        try {
            database = LectureDatabase.getInstance();
            database.addChildEventListener(eventListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Formatter formatter = Formatter.getInstance();
        holder.setColor(lectures.get(position).getColor());
        holder.setTitle(lectures.get(position).getName());
        holder.setTime(String.format(
                Locale.getDefault(), "%s - %s",
                formatter.getTime(lectures.get(position).getStart().getTime()),
                formatter.getTime(lectures.get(position).getEnd().getTime())));
        holder.itemView.setOnClickListener(v -> {
            if (target != null) target.callback(Lecture.toJson(lectures.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        if (lectures != null) return lectures.size();
        return 0;
    }

    public void addLecture(Lecture item) {
        database.addLecture(item);
    }

    public void addOnClickListener(Callback<String> callback) {
        target = callback;
    }

    public void addOnEmptyListener(Callback<Boolean> callback) {
        onEmpty = callback;
    }

    private void notifyLecturesChanged() {
        if (lectures != null) lectures.sort(Collections.reverseOrder());
        onEmpty.callback(getItemCount() == 0);
    }
}