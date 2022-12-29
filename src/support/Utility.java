package support;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Utility.
 */
public class Utility {

    /**
     * To utc string.
     *
     * @param time the time
     * @return the string
     */
    public static String toUTC(String time) {
        LocalDateTime ldt = LocalDateTime.parse(time);
        ZoneId userTimeZone = ZoneId.of(ZoneId.systemDefault().toString());
        ZonedDateTime ldtZoned = ldt.atZone(userTimeZone);

        return ldtZoned.withZoneSameInstant(ZoneId.of("UTC")).toString();
    }

    /**
     * To local string.
     *
     * @param utcTime the utc time as a string
     * @return the string
     *
     * This method is used to convert a UTC time to a local time
     */
    public static String toLocal(String utcTime) {
        Instant instant = Instant.parse(utcTime);

        ZoneId systemZone = ZoneId.systemDefault();

        return instant.atZone(systemZone).toLocalDateTime().toString();
    }

    /**
     * To est local date time.
     *
     * @param utcTime the utc time
     * @return the local date time
     * This converts a UTC time to a New York time
     */
    public static LocalDateTime toEST(String utcTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localUtcTime = LocalDateTime.parse(utcTime, formatter);
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime zonedUtcTime = localUtcTime.atZone(ZoneId.of("UTC"));

        return zonedUtcTime.withZoneSameInstant(estZone).toLocalDateTime();
    }

    /**
     * Utc for database string.
     *
     * @param startDate the start date
     * @param startTime the start time
     * @return the string
     */
    public static String utcForDatabase(String startDate, String startTime) {
        String startDateUtc = Utility.toUTC(startDate + "T" + startTime);

        startDateUtc = startDateUtc.replace("Z[UTC]", "");
        startDateUtc = startDateUtc.replace("T", " ");

        return startDateUtc;
    }


    /**
     * In business hours boolean.
     *
     * @param time1 the time 1
     * @param time2 the time 2
     * @return the boolean
     */
    public static boolean inBusinessHours(LocalDateTime time1, LocalDateTime time2) {
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime zonedTime1 = time1.atZone(estZone);
        ZonedDateTime zonedTime2 = time2.atZone(estZone);
        LocalDateTime startOfBusinessHours = LocalDateTime.of(zonedTime1.getYear(), zonedTime1.getMonth(), zonedTime1.getDayOfMonth(), 7, 59);
        LocalDateTime endOfBusinessHours = LocalDateTime.of(zonedTime1.getYear(), zonedTime1.getMonth(), zonedTime1.getDayOfMonth(), 22, 01);
        ZonedDateTime zonedStartOfBusinessHours = startOfBusinessHours.atZone(estZone);
        ZonedDateTime zonedEndOfBusinessHours = endOfBusinessHours.atZone(estZone);

        return zonedTime1.isAfter(zonedStartOfBusinessHours) && zonedTime1.isBefore(zonedEndOfBusinessHours) && zonedTime2.isAfter(zonedStartOfBusinessHours) && zonedTime2.isBefore(zonedEndOfBusinessHours);
    }

    /**
     * Is between returns a boolean if a time is equal to or in between two other times
     *
     * @param start the start
     * @param end   the end
     * @param time  the time
     * @return the boolean
     */
    public static boolean isBetween(LocalDateTime start, LocalDateTime end, LocalDateTime time) {
        return (time.isAfter(start) || time.isEqual(start)) && (time.isBefore(end) || time.isEqual(end));
    }


    /**
     * Build from string local date time.
     *
     * @param datetime the datetime
     * @return the local date time
     */
    public static LocalDateTime buildFromString(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localTime = LocalDateTime.parse(datetime, formatter);

        return localTime;
    }
}
