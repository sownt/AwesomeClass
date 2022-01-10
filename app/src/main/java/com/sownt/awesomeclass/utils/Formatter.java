package com.sownt.awesomeclass.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Formatter {
    private final SimpleDateFormat df = new SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault());
    private final SimpleDateFormat tf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    private Formatter() {}

    public String getDate(Date date) {
        return df.format(date);
    }

    public Calendar getDate(String date) throws ParseException {
        Date d = df.parse(date);
        Calendar c = Calendar.getInstance();
        if (d != null) c.setTime(d);
        c.set(Calendar.SECOND, 0);
        return c;
    }

    public String getTime(Date time) {
        return tf.format(time);
    }

    public Calendar getTime(String time) throws ParseException {
        Date t = tf.parse(time);
        Calendar c = Calendar.getInstance();
        if (t != null) c.setTime(t);
        c.set(Calendar.SECOND, 0);
        return c;
    }

    public Calendar getDateTime(String date, String time) {
        Calendar c = Calendar.getInstance();
        try {
            c = getDate(date);
            Calendar t = getTime(time);
            c.set(Calendar.HOUR_OF_DAY, t.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, t.get(Calendar.MINUTE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Formatter getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        private static final Formatter INSTANCE = new Formatter();
    }
}
