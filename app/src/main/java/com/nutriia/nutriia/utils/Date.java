package com.nutriia.nutriia.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date {
    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
