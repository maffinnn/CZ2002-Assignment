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
     * @return Interger parsed from standard input
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
     * 
     * @return Interger parsed from standard input
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
     * @return Double parsed from standard input
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
     * 
     * @return Double parsed from standard input
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
     * @return next line of String from standard input
     */
    public String nextLine() {
        return sc.nextLine();
    }

    /**
     * LocalDate.parse() wrapper
     * 
     * @return Date parsed from standard input
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
     * @return Localdate parsed from standard input
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
     * @return LocalTime parsed from standard input
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
     * @return LocalTime parsed from standard input
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
     * @return Long parsed from standard input
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
     * @return Long parsed from standard input
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
