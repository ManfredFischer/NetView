package de.netview.function.impl;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat lastLogin = new SimpleDateFormat();

    public DateUtil() {
        lastLogin.applyPattern("EEEE', 'dd. MMMM yyyy");
    }

    public static String getFormatedDateNow() {
        return lastLogin.format(new Date());
    }

    public static Long getDateNow(){
        return new Date().getTime();
    }


    public static String formatDate(long date) {
        return lastLogin.format(new Date(date));
    }

    public static Boolean isDateBefor(long date, long days) {
        return new Date(date).toInstant().isBefore(ZonedDateTime.now().plusDays(days).toInstant());
    }
}
