package com.kxtx.dubbo.monitor.core;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/4/25
 */
public class DateUtil {
    public static Date parseDate(LocalDate localDate) {
        Preconditions.checkNotNull(localDate);
        return parseDate(localDate, ZoneId.systemDefault());
    }

    /**
     * @param date supports:<ul><li>{@link java.util.Date}
     *             <li>{@link java.sql.Date}
     *             <li>{@link java.sql.Timestamp}
     *             <li>{@link java.time.LocalDate}
     *             <li>{@link java.time.LocalDateTime}
     *             <li>{@link java.time.ZonedDateTime}
     *             <li>{@link java.time.Instant}
     * @param zone used only if the input object is LocalDate or LocalDateTime.
     * @return
     */
    private static Date parseDate(Object date, ZoneId zone) {
        if (date == null)
            return null;
        if (date instanceof java.sql.Date || date instanceof java.sql.Timestamp)
            return new Date(((Date) date).getTime());
        if (date instanceof Date)
            return (Date) date;
        if (date instanceof LocalDate)
            return Date.from(((LocalDate) date).atStartOfDay(zone).toInstant());
        if (date instanceof LocalDateTime)
            return Date.from(((LocalDateTime) date).atZone(zone).toInstant());
        if (date instanceof ZonedDateTime)
            return Date.from(((ZonedDateTime) date).toInstant());
        if (date instanceof Instant)
            return Date.from((Instant) date);
        throw new UnsupportedOperationException("Don't know hot to convert " + date.getClass().getName() + " to java.util.Date");
    }

    private static LocalDateTime parseDateTime(Date date, ZoneId zone) {
        if (date == null)
            return null;
        if (date instanceof java.sql.Timestamp)
            return ((java.sql.Timestamp) date).toLocalDateTime();
        else
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
    }

    public static String dateToString(Date input) {
        if (input == null) return null;
        LocalDateTime localDateTime = parseDateTime(input, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
    }

    public static Date stringToDate(String input) throws ParseException {
        if (Strings.isNullOrEmpty(input)) {
            return new Date();
        }
        if (input.length() == 14) {
            LocalDateTime localDateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            return parseDate(localDateTime, ZoneId.systemDefault());
        } else {
            return new Date(Long.parseLong(input));
        }
    }
}
