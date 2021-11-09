package restaurant;

import java.time.LocalDateTime;

/**
 * Reservation for a specific table and time.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Reservation implements Comparable<Reservation> {

    /**
     * The table reserved.
     */
    private int tableId;

    /**
     * The number of people.
     */
    private int pax;

    /**
     * The reserved starting time.
     */
    private LocalDateTime time;

    /**
     * Contact number of the customer who made the reservation.
     */
    private long contact;

    /**
     * Reservation constructor
     * 
     * @param time    the requested time
     * @param tableId the requested table
     * @param pax     the number of customers
     * @param contact the contact number of the customer
     */
    public Reservation(LocalDateTime time, int tableId, int pax, long contact) {
        this.tableId = tableId;
        this.pax = pax;
        this.time = time;
        this.contact = contact;
    }

    /**
     * Prints the information of the reservation to standard output in a
     * user-friendly way.
     */
    public void print() {
        String t = time.toString();
        System.out.printf("time:%s\ttableId:%d\tpax:%d\tcontact:%d\n", t, tableId, pax, contact);
    }

    /**
     * Self-defined rule for comparing reservation.(for sorting in the Treeset)
     */
    public int compareTo(Reservation r2) {
        return this.time.compareTo(r2.time);
    }

    /**
     * Self-defined rule for checking equivalence of two reservations.
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Reservation r2) {
            return r2.time.equals(this.time) && r2.tableId == this.tableId && r2.contact == this.contact;
        } else
            return false;
    }

    /**
     * @return the reserved table's Id
     */
    public int getTableId() {
        return tableId;
    }

    /**
     * Set the reserved table
     * 
     * @param tableid the reserved table
     */
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    /**
     * @return the number of the customers in this reservations
     */
    public int getPax() {
        return pax;
    }

    /**
     * Set the number of customers for a reservation
     * 
     * @param pax number of customers
     */
    public void setPax(int pax) {
        this.pax = pax;
    }

    /**
     * @return the reservation timing
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Set the reservation timing
     * 
     * @param time the requested time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * @return the contact number of a reservation
     */
    public long getContact() {
        return contact;
    }

    /**
     * Set the contact of a reservation
     * 
     * @param contact the customer's contact number
     */
    public void setContact(long contact) {
        this.contact = contact;
    }
}
