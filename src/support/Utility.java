package support;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    public static String toUTC(String time) {
        LocalDateTime ldt = LocalDateTime.parse(time);
        ZoneId userTimeZone = ZoneId.of(ZoneId.systemDefault().toString());
        ZonedDateTime ldtZoned = ldt.atZone(userTimeZone);

        return ldtZoned.withZoneSameInstant(ZoneId.of("UTC")).toString();
    }

    public static String toLocal(String utcTime) {
        Instant instant = Instant.parse(utcTime);

        ZoneId systemZone = ZoneId.systemDefault();

        return instant.atZone(systemZone).toLocalDateTime().toString();
    }

    public static LocalDateTime toEST(String utcTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localUtcTime = LocalDateTime.parse(utcTime, formatter);
        ZoneId estZone = ZoneId.of("America/New_York");
        ZonedDateTime zonedUtcTime = localUtcTime.atZone(ZoneId.of("UTC"));

        return zonedUtcTime.withZoneSameInstant(estZone).toLocalDateTime();
    }

    public static String utcForDatabase(LocalDateTime time) {
         time = time.atZone(ZoneId.of("UTC")).toLocalDateTime();

        return time.toString().replace("T", " ");
    }

    public static String utcForDatabase(String startDate, String startTime) {
        String startDateUtc = Utility.toUTC(startDate + "T" + startTime);

        startDateUtc = startDateUtc.replace("Z[UTC]", "");
        startDateUtc = startDateUtc.replace("T", " ");

        return startDateUtc;
    }


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

    public static boolean isBetween(LocalDateTime start, LocalDateTime end, LocalDateTime time) {
        return (time.isAfter(start) || time.isEqual(start)) && (time.isBefore(end) || time.isEqual(end));
    }


    public static LocalDateTime buildFromString(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localTime = LocalDateTime.parse(datetime, formatter);

        return localTime;
    }
}
