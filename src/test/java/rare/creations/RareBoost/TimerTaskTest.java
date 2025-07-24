package rare.creations.RareBoost;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerTaskTest {

    public static void main(String[] args) {
        LocalTime of = LocalTime.of(10, 0);
        ZoneId of1 = ZoneId.of("America/New_York");
        boolean b = endTime(of, of1);
        System.out.println(b);
    }

    private static boolean endTime(LocalTime endTime, ZoneId zoneId){
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        if (now.toLocalTime().isAfter(endTime)){
            return true;
        }
        return false;
    }

    private void checkerFirst(LocalTime targetTime, LocalTime endTime){
        ZoneId zoned = ZoneId.of("America/New_York");
        ZonedDateTime now = ZonedDateTime.now(zoned);
        if (isWithinRange(now.toLocalTime(), targetTime, endTime)) {
            System.out.println("The current time is within the specified range.");
        } else {
            System.out.println("The current time is outside the specified range.");
        }
        ZonedDateTime tDT = ZonedDateTime.of(now.toLocalDate(), targetTime, zoned);
        if (now.toLocalTime().isAfter(targetTime)){
            tDT = tDT.plusDays(1);
        }
        Duration between = Duration.between(now, tDT);
    }

    private static boolean isWithinRange(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return !time.isBefore(startTime) && !time.isAfter(endTime);
    }

    public static void something(String[] args) {
        Timer timer =  new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task is Completed");
            }
        };

        ZoneId jakarta = ZoneId.of("America/New_York");
        LocalTime target = LocalTime.of(6, 3);

        System.out.println(calculateExecutionTime(target, jakarta));
        timer.schedule(task, calculateExecutionTime(target, jakarta));
    }

    private static long calculateInitialDelay(LocalTime targetTime, ZoneId zone) {
        LocalTime now = LocalTime.now(zone);
        long initialDelay = targetTime.toSecondOfDay() - now.toSecondOfDay();


        if (initialDelay < 0) {
            initialDelay += TimeUnit.DAYS.toSeconds(1);
        }

        return initialDelay;
    }


    private static Date calculateExecutionTime(LocalTime targetTime, ZoneId zone) {
        // Get the current date in the specified time zone
        ZonedDateTime now = ZonedDateTime.now(zone);

        // Combine the current date and the target time
        ZonedDateTime targetDateTime = ZonedDateTime.of(now.toLocalDate(), targetTime, zone);

        // If the target time has already passed for today, schedule for the next day
        if (now.isAfter(targetDateTime)) {
            targetDateTime = targetDateTime.plusDays(1);
        }

        // Convert ZonedDateTime to Date
        return Date.from(targetDateTime.toInstant());
    }

}
