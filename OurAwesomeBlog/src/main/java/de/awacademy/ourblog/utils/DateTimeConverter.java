package de.awacademy.ourblog.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeConverter {

    /**
     * This method converts the Instant object to a formatted String
     *
     * @param postedAt is the Instant object to be converted
     * @return the return value is a formatted String, that displays the date and time
     */
    public static String getConvertedDateTime(Instant postedAt){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM )
                        .withLocale( Locale.GERMANY )
                        .withZone( ZoneId.systemDefault() );
        return formatter.format(postedAt);
    }
}