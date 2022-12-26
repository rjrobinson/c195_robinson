package support;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utility {

    public String toUTC(String time) {
        LocalDateTime ldt = LocalDateTime.parse(time);
        ZoneId userTimeZone = ZoneId.of(ZoneId.systemDefault().toString());
        ZonedDateTime ldtZoned = ldt.atZone(userTimeZone);

        return ldtZoned.withZoneSameInstant(ZoneId.of("UTC")).toString();
    }


    public static String toLocal(String time) {

        Instant timestamp = Instant.parse(time);
        ZonedDateTime ldt = timestamp.atZone(ZoneId.of(ZoneId.systemDefault().toString()));

        return ldt.toString();
    }
}
