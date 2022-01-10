package com.sownt.awesomeclass.ui.main;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sownt.awesomeclass.App;
import com.sownt.awesomeclass.MainActivity;
import com.sownt.awesomeclass.R;
import com.sownt.awesomeclass.adapter.LectureAdapter;
import com.sownt.awesomeclass.interfaces.Callback;
import com.sownt.awesomeclass.model.Lecture;
import com.sownt.awesomeclass.ui.decoration.StickyDecoration;
import com.sownt.awesomeclass.utils.Formatter;
import com.sownt.awesomeclass.utils.NotificationPublisher;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private LectureAdapter lectureAdapter;
    private Calendar calendar;
    private Formatter formatter;
    private Lecture tempLecture;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView empty = view.findViewById(R.id.no_item_here);
        RecyclerView plan = view.findViewById(R.id.home_plan);
        FloatingActionButton addLectures = view.findViewById(R.id.fab_add_lecture);
        lectureAdapter = new LectureAdapter();
        formatter = Formatter.getInstance();

        plan.setLayoutManager(new LinearLayoutManager(this.getContext()));
        plan.setAdapter(lectureAdapter);
        plan.addItemDecoration(new StickyDecoration(lectureAdapter));

        addLectures.setOnClickListener(v -> addNewLecture());
        lectureAdapter.addOnClickListener(pass -> lectureClicked(pass));
        lectureAdapter.addOnEmptyListener(isEmpty -> {
            if (isEmpty) {
                empty.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.GONE);
            }
        });
    }

    private void lectureClicked(String pass) {
        View lectureView = getLayoutInflater().inflate(R.layout.bottom_sheet_view_lecture, null);
        Lecture l = Lecture.fromJson(pass);

        TextView title = lectureView.findViewById(R.id.sheet_lecture_title);
        ImageView color = lectureView.findViewById(R.id.sheet_color);
        TextView timestamp = lectureView.findViewById(R.id.sheet_timestamp);
        TextView link = lectureView.findViewById(R.id.sheet_link);

        title.setText(l.getName());
        color.setColorFilter(l.getColor());
        timestamp.setText(String.format(Locale.getDefault(), "%s . %s",
                formatter.getDate(l.getStart().getTime()),
                formatter.getTime(l.getStart().getTime())));
        link.setText(l.getLink());

        BottomSheetDialog sheetDialog = new BottomSheetDialog(getActivity());
        sheetDialog.setContentView(lectureView);
        sheetDialog.show();

        link.setOnClickListener(v -> {
            if (l.getLink() == null) return;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(l.getLink()));
            startActivity(intent);
        });
    }

    private void addNewLecture() {
        final View lectureView = getLayoutInflater().inflate(R.layout.bottom_sheet_add_lecture, null);
        tempLecture = new Lecture();

        Button save = lectureView.findViewById(R.id.sheet_save);
        EditText title = lectureView.findViewById(R.id.sheet_lecture_title);
        SwitchCompat isAllDay = lectureView.findViewById(R.id.sheet_all_day);
        TextView fromDate = lectureView.findViewById(R.id.sheet_from_date);
        TextView fromTime = lectureView.findViewById(R.id.sheet_from_time);
        TextView toDate = lectureView.findViewById(R.id.sheet_to_date);
        TextView toTime = lectureView.findViewById(R.id.sheet_to_time);
        TextView repeat = lectureView.findViewById(R.id.sheet_repeat);
        EditText link = lectureView.findViewById(R.id.sheet_link);
        EditText location = lectureView.findViewById(R.id.sheet_location);
        TextView notification = lectureView.findViewById(R.id.sheet_notification);
        TextView color = lectureView.findViewById(R.id.sheet_color);
        EditText description = lectureView.findViewById(R.id.sheet_description);

        updateTime();
        fromDate.setText(formatter.getDate(calendar.getTime()));
        fromTime.setText(formatter.getTime(calendar.getTime()));
        toDate.setText(formatter.getDate(calendar.getTime()));
        toTime.setText(formatter.getTime(calendar.getTime()));

        BottomSheetDialog sheetDialog = new BottomSheetDialog(getActivity());
        sheetDialog.setContentView(lectureView);
        sheetDialog.setOnDismissListener(dialog -> tempLecture = null);
        sheetDialog.show();

        isAllDay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isAllDay.isChecked()) {
                lectureView.findViewById(R.id.sheet_from_time).setVisibility(View.GONE);
                lectureView.findViewById(R.id.sheet_to_time).setVisibility(View.GONE);
            } else {
                lectureView.findViewById(R.id.sheet_from_time).setVisibility(View.VISIBLE);
                lectureView.findViewById(R.id.sheet_to_time).setVisibility(View.VISIBLE);
            }
        });

        fromDate.setOnClickListener(v -> selectDate(fromDate));
        fromTime.setOnClickListener(v -> selectTime(fromTime));
        toDate.setOnClickListener(v -> selectDate(toDate));
        toTime.setOnClickListener(v -> selectTime(toTime));
        repeat.setOnClickListener(v -> selectRepeat(repeat));
        notification.setOnClickListener(v -> selectNotification(notification));
        color.setOnClickListener(v -> selectColor(color));
        save.setOnClickListener(v -> {
            saveLecture(
                    title, isAllDay, fromDate, fromTime, toDate, toTime, repeat, link,
                    location, notification, color, description
            );
            sheetDialog.dismiss();
        });
    }

    private void saveLecture(EditText title, SwitchCompat isAllDay, TextView fromDate,
                             TextView fromTime, TextView toDate, TextView toTime, TextView repeat,
                             EditText link, EditText location, TextView notification, TextView color,
                             EditText description) {

        tempLecture.setName(title.getText().toString());
        tempLecture.setStart(formatter.getDateTime(fromDate.getText().toString(), fromTime.getText().toString()));
        tempLecture.setEnd(formatter.getDateTime(toDate.getText().toString(), toTime.getText().toString()));
        tempLecture.setLink(link.getText().toString());
        tempLecture.setLocation(location.getText().toString());
        tempLecture.setDescription(description.getText().toString());
        lectureAdapter.addLecture(tempLecture);

        long[] delay = {0,5,10,15,30};
        Date notificationTime = new Date(tempLecture.getStart().getTimeInMillis() -
                Duration.ofMinutes(delay[tempLecture.getNotification()]).toMillis());
        sendNotification(
                tempLecture.getName(),
                tempLecture.getLink(),
                notificationTime.getTime());

        tempLecture = null;
    }

    private void selectColor(TextView color) {
        String[] colors = getResources().getStringArray(R.array.color_theme);
        String[] values = getResources().getStringArray(R.array.color_values);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(colors, 0, (dialog, which) -> {
            if (which != 0) {
                color.setText(colors[which]);
                tempLecture.setColor(Color.parseColor(values[which]));
            }
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void selectNotification(TextView notification) {
        String[] status = getResources().getStringArray(R.array.notification_status);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(status, 0, (dialog, which) -> {
            if (which != 0) {
                notification.setText(status[which]);
                tempLecture.setNotification(which);
            }
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void selectRepeat(TextView repeat) {
    }

    private void selectTime(TextView fromTime) {
        TimePickerDialog.OnTimeSetListener listener = (view, hourOfDay, minute) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            fromTime.setText(formatter.getTime(calendar.getTime()));
        };

        updateTime();
        TimePickerDialog dialog = new TimePickerDialog(
                getActivity(),
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );

        dialog.show();
    }

    private void selectDate(TextView fromDate) {
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            fromDate.setText(formatter.getDate(calendar.getTime()));
        };

        updateTime();
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void updateTime() {
        calendar = Calendar.getInstance();
    }

    public void sendNotification(String content, String link, long time) {
        Log.d("AwesomeClass", new SimpleDateFormat("EEE, dd-MM-yyyy  hh:mm a", Locale.getDefault()).format(new Date(time)));
        Intent intent = new Intent(getActivity(), NotificationPublisher.class);
        intent.putExtra(NotificationPublisher.NOTIFICATION_CONTENT, content);
        intent.putExtra(NotificationPublisher.NOTIFICATION_LINK, link);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
}
