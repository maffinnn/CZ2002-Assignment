package restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * The input wrapper class for input exceptions.
 *
 * @author Zhou Yiqi
 * @author Bian Siyuan
 * @author Chan Chor Cheng
 * @author Shrivastava Samruddhi
 * @author Wang Yujing
 * @version 0.0
 */

public class InputHandler {

    /**
     * Date formatter used across all subroutines.
     */
    private final DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Scanner for reading console input
     */
    private Scanner sc = new Scanner(System.in);

    /**
     * Integer.parseInt() wrapper
     * 
     * @return Interger parsed
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
     * 
     * @return Double parsed
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
     * 
     * @return next line of String from console
     */
    public String nextLine() {
        return sc.nextLine();
    }

    /**
     * LocalDate.parse() wrapper
     * 
     * @return the date parsed
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
     * 
     * @return Localdate parsed
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
     * 
     * @return the LocalTime parsed
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
     * 
     * @return the LocalTime parsed
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
     * 
     * @return the Long parsed
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
     * 
     * @return the Long parsed
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
