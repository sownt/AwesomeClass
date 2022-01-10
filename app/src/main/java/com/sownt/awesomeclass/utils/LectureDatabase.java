package com.sownt.awesomeclass.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sownt.awesomeclass.model.Lecture;

public class LectureDatabase {
    private static DatabaseReference reference;
    private LectureDatabase() {}

    public void addLecture(Lecture item) {
        if (reference == null) return;
        reference.push().setValue(Lecture.toJson(item));
    }

    public void addChildEventListener(ChildEventListener listener) {
        if (reference == null) return;
        reference.addChildEventListener(listener);
    }

    public static LectureDatabase getInstance() throws Exception {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) throw new Exception("Login is required with Realtime Database");
        reference = database.getReference(user.getUid());
        reference.keepSynced(true);
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        private static final LectureDatabase INSTANCE = new LectureDatabase();
    }
}
