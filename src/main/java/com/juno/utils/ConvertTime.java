package com.juno.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConvertTime {
    public static String convertDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static String convertLocalDatetime(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDateTime.format(dateTimeFormatter);
    }

    public static Date stringToDate(String s) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(s);
            System.out.println("gia tri ok: " +date);
            return date;
        } catch (ParseException e) {
//            e.printStackTrace();
            System.out.println("co loi parse" + e.getMessage());
            return null;
        }
    }

    // cho phu hop khi chuyen sang dang date trong js (MM/dd/yyyy)
    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }
}
