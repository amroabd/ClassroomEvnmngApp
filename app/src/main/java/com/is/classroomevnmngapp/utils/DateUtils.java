package com.is.classroomevnmngapp.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateUtils {
    // TODO: 04/04/2023 format-> %[argument_index$][flags][width][.precision]conversion = %1$4.2s
    //  Conversions ->[general:hH,bB ],[CHARACTER c],[INTEGER:dD,o,x],[float:f,gG,aA],[date:tT]
    private final String[] en_am_pm = {"AM", "PM"};
    private final String[] ar_am_pm = {"صباح", "مساء"};
    private static final String TAG = DateUtils.class.getSimpleName();
    @SuppressLint("ConstantLocale")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static final String DEF_HH_MM = "HH:mm";
    public static final String def_HH_MM_SS_24 = "HH:mm:ss";
    public static final String DEF_HH_MM_SS_12 = "hh:mm:ss";
    public static final String DEF_HH_MM_SS_M = "HH:mm:ss.SSS";
    public static final String DEF_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DEF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DEF_YYYY_MM_DD_HH_MM_SS_12 = "yyyy-MM-dd hh:mm:ss";
    public static final String DEF_YYYY_MM_DD_HH_MM_SS_24 = "yyyy-MM-dd HH:mm:ss";
    public static final String DEF_YYYY_MM_DD_HH_MM_SS_M = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_DAY_NAME = "EEEE";

    private static final long ONE_SECOND_MILLI = 1000L;
    private static final long ONE_MINUTE_MILLI = ONE_SECOND_MILLI * 60L;
    private static final long ONE_HOUR_MILLI = ONE_MINUTE_MILLI * 60L;
    private static final long ONE_DAY_MILLI = 86400000L;
    private static final long ONE_MONTH_MILLI = 2678400000L;
    private static final long ONE_YEAR_MILLI = 32140800000L;
    private static final String NAME_HOUR = "ساعة";
    private static final String NAME_DAY = "يوم";
    private static final String NAME_WEEK = "اسبوع";
    private static final String NAME_MONTH = "شهر";
    private static final String NAME_YEAR = "سنة";

    //====================================================

    @NotNull
    @Contract("_ -> new")
    private static DateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, Locale.ENGLISH);
    }

    @NotNull
    public static String getDate() {
        return (getSimpleDateFormat(DEF_YYYY_MM_DD)).format(new Date());
    }

    @NonNull
    public static String getCurrentDTime() {
        //,Locale.ENGLISH
        return ((new SimpleDateFormat(DEF_YYYY_MM_DD_HH_MM_SS_24,Locale.ENGLISH)).format(Calendar.getInstance().getTime()));
    }

    @NotNull
    public static String getNameDay() {
        return new SimpleDateFormat(FORMAT_DAY_NAME,Locale.ENGLISH).format(new Date());
    }

    @NotNull
    public static String getTime_12() {
        return (new SimpleDateFormat(DEF_HH_MM_SS_12,Locale.ENGLISH)).format(new Date());
    }
    @NotNull
    public static String getTime_24() {
        return (new SimpleDateFormat(def_HH_MM_SS_24,Locale.ENGLISH)).format(new Date());
    }

    @NotNull
    public static String getA() {
        return (getSimpleDateFormat("a")).format(Calendar.getInstance().getTime());
    }


    public static String convertLong2Date(long milli, String pattern) {
        Log.i(TAG,"milli_convert_date :"+ milli);//Long.valueOf("1680564666794")
        return getSimpleDateFormat(DEF_YYYY_MM_DD_HH_MM_SS_12).format(milli);
    }

    @NotNull
    public static String convertLong2Date(String milli, String pattern) {
        Log.i(TAG,"milli_convert_date :"+ milli);
        return getSimpleDateFormat(pattern).format(new Date(Long.parseLong(milli)));
    }

    public static long convertDate2long(String strDate, String pattern) throws ParseException {
        Log.i(TAG, "date to long :"+ strDate);
        return Objects.requireNonNull(getSimpleDateFormat(pattern).parse(strDate)).getTime() / ONE_SECOND_MILLI;
    }

    /***
     * if date_1>date_2 return true(2023-4-4>2023-4-3),other false
     */
    public static boolean compare2Date(Date date_1, Date date_2) {
        if ((date_1 == null) || (date_2 == null)) return false;
        return date_1.after(date_2);
    }

    public static boolean compare2Date(Date date_2) {
        if (date_2 == null) return false;
        return new Date().after(date_2);
    }

    private static Date getDATE(String date) {

        try {
            return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * @param date_1 date first as string
     * @param date_2 date second as string
     * @return if date_1>date_2 return true(2023-4-4>2023-4-3),other false
     */
    public static boolean compare2Date(String date_1, String date_2) {
        if (TextUtils.isEmpty(date_1) || TextUtils.isEmpty(date_2)) return false;
        return getDATE(date_1).after(getDATE(date_2));
    }

    public static boolean isToDay(long oneMillis) {
        return android.text.format.DateUtils.isToday(oneMillis);
    }


    private static double convertToMonths(double days) {
        return days / 365.0D * 12.0D;
    }

    private static double convertToWeeks(double days) {
        return days / 365.0D * 52.0D;
    }

    public static double convertToYear(double days) {
        return days / 365.0D;
    }

    public static Calendar getCalendar(String TIME) {
        String[] strings = TIME.split(":");
        int hour = (strings != null && strings.length > 0) ? Integer.parseInt(strings[0]) : 0;
        int minute = (strings != null && strings.length > 1) ? Integer.parseInt(strings[1]) : 0;

        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();

        calendar1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar1.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        calendar1.set(Calendar.HOUR, hour);
        calendar1.set(Calendar.MINUTE, minute);

        return calendar1;
    }

}
