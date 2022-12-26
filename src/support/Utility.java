package support;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utility {

    public static String toUTC(String time) {
        LocalDateTime ldt = LocalDateTime.parse(time);
        ZoneId userTimeZone = ZoneId.of(ZoneId.systemDefault().toString());
        ZonedDateTime ldtZoned = ldt.atZone(userTimeZone);

        return ldtZoned.withZoneSameInstant(ZoneId.of("UTC")).toString();
    }


    public static String toLocal(String time) {
        time = time.replace(" ", "T");
        Instant timestamp = Instant.parse(time);
        ZonedDateTime ldt = timestamp.atZone(ZoneId.of(ZoneId.systemDefault().toString()));

        return ldt.toString();
    }

    public static String utcForDatabase(String startDate, String startTime) {
        String startDateUtc = Utility.toUTC(startDate + "T" + startTime);

        startDateUtc = startDateUtc.replace("Z[UTC]", "");
        startDateUtc = startDateUtc.replace("T", " ");

        return startDateUtc;
    }
}
