package com.course.offering.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

public class ScheduleTimeConverter {

    private static int startingHour = 7;

    // Input has to be of 4 digits
    public static String time24To12(String timeOf4Digits) {
        // System.out.println(getTimeOfDay24MilTime());
        String timeOfDay12 = "EMPTY";
        try {
            // Convert the int to a string ensuring its 4 characters wide, parse as a date
            // Date date = new SimpleDateFormat("hhmm").parse(String.format("%04d",
            // getTimeOfDay24()));
            Date date = new SimpleDateFormat("HHMM").parse(timeOf4Digits);
            // Set format: print the hours and minutes of the date, with AM or PM at the end
            timeOfDay12 = new SimpleDateFormat("hh:mm a").format(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return timeOfDay12;
    }

    public static int time24ToIndex(String timeOf4Digits) {
        int index = 0;
        try {
            Date date = new SimpleDateFormat("HHMM").parse(timeOf4Digits);
            index = timeToIndex(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return index;
    }

    public static int time12ToIndex(String time12Format) {
        int index = 0;
        try {
            Date date = new SimpleDateFormat("hh:mm a").parse(time12Format);
            index = timeToIndex(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return index;
    }

    public static int timeToIndex(Date time) {
        int index = 0;
        try {
            String date = new SimpleDateFormat("HH").format(time);
            index = (Integer.parseInt(date) - startingHour + 1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return index;
    }

    public static String indexToTime12(int rowIndex) {
        String timeString = "";
        try {
            Date date = new SimpleDateFormat("HH").parse(String.format("%02d", rowIndex + startingHour));
            // Set format: print the hours and minutes of the date, with AM or PM at the end
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            timeString = format.format(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return timeString;
    }

    public static String indexToTime24(int rowIndex) {
        String timeString = "";
        try {
            Date date = new SimpleDateFormat("HH").parse(String.format("%02d", rowIndex + startingHour));
            // Set format: print the hours and minutes of the date, with AM or PM at the end
            SimpleDateFormat format = new SimpleDateFormat("HHMM");
            timeString = format.format(date);
        } catch (Exception e) {
            System.out.println(e);
        }
        return timeString;
    }

    public static DayOfWeek dayStrToDayOfWeek(String day) {
        switch (day) {
            case "Sunday":
                return DayOfWeek.SUNDAY;
            case "Monday":
                return DayOfWeek.MONDAY;
            case "Tuesday":
                return DayOfWeek.TUESDAY;
            case "Wednesday":
                return DayOfWeek.WEDNESDAY;
            case "Thursday":
                return DayOfWeek.THURSDAY;
            case "Friday":
                return DayOfWeek.FRIDAY;
            default:
                return DayOfWeek.SATURDAY;
        }
    }

    public static DayOfWeek indexToDayOfWeek(int colIndex) {
        switch (colIndex) {
            case 1:
                return DayOfWeek.SUNDAY;
            case 2:
                return DayOfWeek.MONDAY;
            case 3:
                return DayOfWeek.TUESDAY;
            case 4:
                return DayOfWeek.WEDNESDAY;
            default:
                return DayOfWeek.THURSDAY;
        }
    }

    public static int dayOfWeekTOIndex(DayOfWeek day) {
        switch (day) {
            case SUNDAY:
                return 1;
            case MONDAY:
                return 2;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 4;
            default:
                return 5;
        }
    }

    public static int durationToRowSpan(int durationInMinutes) {
        switch (durationInMinutes) {
            case 50:
                return 4;
            default:
                return Math.round(durationInMinutes / 15);
        }
    }

    public static int timeIntervalToRowSpan(String startTime, String endTime) {
        return durationToRowSpan(timeIntervalToDuration(startTime, endTime));
    }

    public static int timeIntervalToDuration(String startTime, String endTime) {
        int difference = Math.abs(Integer.parseInt(startTime) - Integer.parseInt(endTime));
        int minutes = difference % 100;
        int difference_in_minutes = (difference - minutes) / 100 * 60 + minutes;
        return difference_in_minutes;
    }

    public static DayOfWeek letterToDayOfWeek(char letter) {
        switch (letter) {
            case 'U':
                return DayOfWeek.SUNDAY;
            case 'M':
                return DayOfWeek.MONDAY;
            case 'T':
                return DayOfWeek.TUESDAY;
            case 'W':
                return DayOfWeek.WEDNESDAY;
            default:
                return DayOfWeek.THURSDAY;
        }
    }
}
