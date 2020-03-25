package de.netview.function.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat lastLogin = new SimpleDateFormat();

    public DateUtil (){
        lastLogin.applyPattern("EEEE', 'dd. MMMM yyyy");
    }

    public static String getDateNow(){
        return lastLogin.format(new Date());
    }
}
