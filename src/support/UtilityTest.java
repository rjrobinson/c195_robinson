package support;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

class UtilityTest {

    @org.junit.jupiter.api.Test
    void toUTC() {
        String time = "2021-04-01T08:00";
        String expected = "2021-04-01T12:00Z[UTC]";
        String actual = Utility.toUTC(time).toString();

        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void toLocal() {
        String time = "2022-12-01T00:00:00Z";
        String expected = "2022-11-30T19:00";

        String actual = Utility.toLocal(time).toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void toEST() {
        String time = "2021-04-01 12:00";
        String expected = "2021-04-01T08:00";

        String actual = Utility.toEST(time).toString();
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void inBusinessHours() {

        // Test case 1: Both times within business hours
        LocalDateTime time1 = LocalDateTime.of(2021, 4, 1, 9, 0);
        LocalDateTime time2 = LocalDateTime.of(2021, 4, 1, 18, 0);
        boolean expectedOutput = true;
        boolean actualOutput = Utility.inBusinessHours(time1, time2);

        assertEquals(expectedOutput, actualOutput);

        // Test case 2: Both times outside business hours
        time1 = LocalDateTime.of(2021, 4, 1, 7, 0);
        time2 = LocalDateTime.of(2021, 4, 1, 22, 0);
        expectedOutput = false;
        actualOutput = Utility.inBusinessHours(time1, time2);

        assertEquals(expectedOutput, actualOutput);

        // Test case 3: First time within business hours, second time outside business hours
        time1 = LocalDateTime.of(2021, 4, 1, 9, 0);
        time2 = LocalDateTime.of(2021, 4, 1, 22, 0);

        expectedOutput = true;
        actualOutput = Utility.inBusinessHours(time1, time2);

        assertEquals(expectedOutput, actualOutput);

        // Test case 4: First time outside business hours, second time within business hours
        time1 = LocalDateTime.of(2021, 4, 1, 7, 0);
        time2 = LocalDateTime.of(2021, 4, 1, 18, 0);
        expectedOutput = false;
        actualOutput = Utility.inBusinessHours(time1, time2);

        assertEquals(expectedOutput, actualOutput);


    }

    @org.junit.jupiter.api.Test
    void isBetween() {

        // Test case 1: Both times within business hours
        LocalDateTime time1 = LocalDateTime.of(2021, 4, 1, 9, 0);
        LocalDateTime time2 = LocalDateTime.of(2021, 4, 1, 18, 0);

        LocalDateTime inBetween = LocalDateTime.of(2021, 4, 1, 12, 0);

        boolean expectedOutput = true;
        boolean actualOutput = Utility.isBetween(time1, time2, inBetween);

        assertEquals(expectedOutput, actualOutput);

        inBetween = LocalDateTime.of(2021, 4, 1, 22, 0);

        expectedOutput = false;
        actualOutput = Utility.isBetween(time1, time2, inBetween);

        assertEquals(expectedOutput, actualOutput);


        expectedOutput = true;
        actualOutput = Utility.isBetween(time1, time2, time1);

        assertEquals(expectedOutput, actualOutput);
    }
}
