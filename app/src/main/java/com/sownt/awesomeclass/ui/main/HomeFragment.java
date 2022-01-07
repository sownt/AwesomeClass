package com.sownt.awesomeclass.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sownt.awesomeclass.R;
import com.sownt.awesomeclass.adapter.LectureAdapter;
import com.sownt.awesomeclass.model.Lecture;
import com.sownt.awesomeclass.ui.decoration.StickyDecoration;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView plan;
    private LectureAdapter lectureAdapter;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plan = view.findViewById(R.id.home_plan);

        plan.setLayoutManager(new LinearLayoutManager(this.getContext()));

        lectureAdapter = new LectureAdapter(getLectures());
        plan.setAdapter(lectureAdapter);

        plan.addItemDecoration(new StickyDecoration(lectureAdapter));
    }

    private List<Lecture> getLectures() {
        List<Lecture> lectures = new ArrayList<>();

        for (int i = 1; i <= 10; ++i) {
            lectures.add(new Lecture(
                    "Lecture " + i,
                    new GregorianCalendar(2022, 0, 10, i, 10, 50),
                    new GregorianCalendar(2022, 0, 10, 1 + i, 10, 50),
                    "Hanoi",
                    false,
                    getColor(),
                    "None"));
//            Log.d("AwesomeClass", lectures.get(i - 1).toString());
//            Log.d("AwesomeClass", lectures.get(i - 1).getStart().getTimeZone().toString());
        }

        for (int i = 11; i <= 20; ++i) {
            lectures.add(new Lecture(
                    "Lecture " + i,
                    new GregorianCalendar(2022, 0, 11, i, 10, 50),
                    new GregorianCalendar(2022, 0, 11, 1 + i, 10, 50),
                    "Hanoi",
                    false,
                    getColor(),
                    "None"));
        }

        for (int i = 11; i <= 20; ++i) {
            lectures.add(new Lecture(
                    "Lecture " + i,
                    new GregorianCalendar(2022, 0, 12, i - 10, 10, 50),
                    new GregorianCalendar(2022, 0, 12, i - 10, 10, 50),
                    "Hanoi",
                    false,
                    getColor(),
                    "None"));
        }

        return lectures;
    }

    private int getColor() {
        SecureRandom random = new SecureRandom();
        return Color.HSVToColor(255, new float[]{
                random.nextInt(359), 1, 1
        });
    }
}
