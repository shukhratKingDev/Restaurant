package uz.uzkassa.smartposrestaurant.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 10:27
 */
public final class DateUtils {

    public static final DateTimeFormatter yearsLastTwoDigitFormat = DateTimeFormatter.ofPattern("yy");

    public static String formatYearLastTwoDigit(LocalDate date) {
        return yearsLastTwoDigitFormat.format(date);
    }
}
