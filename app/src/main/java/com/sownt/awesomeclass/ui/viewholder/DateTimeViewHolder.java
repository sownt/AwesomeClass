package com.sownt.awesomeclass.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sownt.awesomeclass.R;

public class DateTimeViewHolder extends RecyclerView.ViewHolder {
    private TextView weekday;
    private TextView date;

    public DateTimeViewHolder(@NonNull View itemView) {
        super(itemView);
        weekday = itemView.findViewById(R.id.item_weekday);
        date = itemView.findViewById(R.id.item_date);
    }

    public void setWeekday(String weekday) {
        if (weekday != null) {
            this.weekday.setText(weekday);
        }
    }

    public void setDate(int date) {
        if (date > 0 && date <= 31) {
            this.date.setText(String.valueOf(date));
        }
    }
}