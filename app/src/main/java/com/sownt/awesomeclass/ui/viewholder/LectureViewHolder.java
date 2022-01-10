package com.sownt.awesomeclass.ui.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sownt.awesomeclass.R;

public class LectureViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout container;
    private TextView title;
    private TextView time;

    public LectureViewHolder(@NonNull View itemView) {
        super(itemView);
        container = itemView.findViewById(R.id.item_lecture_container);
        title = itemView.findViewById(R.id.item_lecture_title);
        time = itemView.findViewById(R.id.item_lecture_time);
    }

    public void setColor(@ColorInt int color) {
        this.container.setBackgroundColor(color);
    }

    public void setTitle(String title) {
        if (title != null) {
            this.title.setText(title);
        }
    }

    public void setTime(String time) {
        if (time != null) {
            this.time.setText(time);
        }
    }
}
