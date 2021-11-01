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
    public int tableId;

    /**
     * The number of people.
     */
    public int pax;

    /**
     * The reserved starting time.
     */
    public LocalDateTime time;

    /**
     * Contact number of the customer who made the reservation.
     */
    public long contact;

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

    public int compareTo(Reservation r2) {
        return this.time.compareTo(r2.time);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Reservation r2) {
            return r2.time.equals(this.time) && r2.tableId == this.tableId && r2.contact == this.contact;
        } else
            return false;
    }

}
