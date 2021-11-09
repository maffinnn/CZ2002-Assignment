package restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputHandler {

    /**
     * Date formatter used across all subroutines.
     */
    DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    Scanner sc = new Scanner(System.in);

    /**
     * Integer.parseInt() wrapper
     */
    public int getInt() {
        String s = sc.nextLine();
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return getIntAgain();
        }
    }

    /**
     * Integer.parseInt() wrapper
     */
    private int getIntAgain() {
        System.out.println("Please enter a valid integer: ");
        String s = sc.nextLine();
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return getIntAgain();
        }
    }

    /**
     * Double.parseDouble() wrapper
     */
    public double getDouble() {
        String s = sc.nextLine();
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return getDoubleAgain();
        }
    }

    /**
     * Double.parseDouble() wrapper
     */
    private double getDoubleAgain() {
        System.out.println("Please enter a valid double: ");
        String s = sc.nextLine();
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return getDoubleAgain();
        }
    }

    /**
     * Scanner.nextLine() wrapper
     */
    public String nextLine() {
        return sc.nextLine();
    }

    /**
     * LocalDate.parse() wrapper
     */
    public LocalDate getDate() {
        String s = sc.nextLine();
        try {
            return LocalDate.parse(s, dt_formatter);
        } catch (Exception e) {
            return getDateAgain();
        }
    }

    /**
     * LocalDate.parse() wrapper
     */
    private LocalDate getDateAgain() {
        System.out.println("Please enter a valid date (format:dd/mm/yyyy): ");
        String s = sc.nextLine();
        try {
            return LocalDate.parse(s, dt_formatter);
        } catch (Exception e) {
            return getDateAgain();
        }
    }

    /**
     * LocalTime.parse() wrapper
     */
    public LocalTime getTime() {
        String s = sc.nextLine();
        try {
            return LocalTime.parse(s);
        } catch (Exception e) {
            return getTimeAgain();
        }
    }

    /**
     * LocalTime.parse() wrapper
     */
    private LocalTime getTimeAgain() {
        System.out.println("Please enter a valid time (format: 13:00): ");
        String s = sc.nextLine();
        try {
            return LocalTime.parse(s);
        } catch (Exception e) {
            return getTimeAgain();
        }
    }

    /**
     * Long.parseLong() wrapper
     */
    public long getLong() {
        String s = sc.nextLine();
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return getLongAgain();
        }
    }

    /**
     * Long.parseLong() wrapper
     */
    private long getLongAgain() {
        System.out.println("Please enter a valid long: ");
        String s = sc.nextLine();
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return getLongAgain();
        }
    }

}
