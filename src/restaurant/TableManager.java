package restaurant;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Manages table and reservation in the restaurant.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class TableManager {
    /**
     * List of capacities of all tables in the restaurant. Table ID is the index of
     * that table in this array.
     */
    private ArrayList<Integer> tables;

    /**
     * The list of all active reservations.
     */
    private TreeSet<Reservation> reservations;

    public TableManager() {
        tables = new ArrayList<>();
        reservations = new TreeSet<>();
        loadReservations();
        loadTables();
    }

    /**
     * Returns the capacity of the table by id.
     *
     * @param id The id of the table.
     * @return The capacity of the table.
     * @throws IllegalArgumentException If id is out of range.
     */
    public int getTableCapacity(int id) {
        if (id < 0 || id >= tables.size()) {
            throw new IllegalArgumentException();
        } else
            return tables.get(id);
    }

    /**
     * Returns the number of tables in the restaurant.
     *
     * @return The number of tables in the restaurant.
     */
    public int getTableCount() {
        return tables.size();
    }

    /**
     * Adds a table with given capacity.
     *
     * @param capacity The capacity of the table to be added.
     */
    public void addTable(int capacity) {
        tables.add(capacity);
    }

    /**
     * Adds a reservation.
     *
     * @param reservation The reservation made.
     * @throws IllegalArgumentException If talbe indicated in the reservation is not
     *                                  available at the specified time slot.
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Returns reservation(s).
     *
     * @param contact The id of the reservation.
     * @return A list of reservations for the given contact
     */
    public ArrayList<Reservation> getReservation(long contact) {
        ArrayList<Reservation> resultList = new ArrayList<>();

        for (Reservation r : reservations) {
            if (r.getContact() == contact) {
                resultList.add(r);
            }
        }
        return resultList;
    }

    /**
     * Returns list of reservations on a given table.
     *
     * @param tableId The inquired table.
     * @return A list of reservations on the given table.
     */
    public ArrayList<Reservation> getReservations(int tableId) {
        ArrayList<Reservation> resultList = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getTableId() == tableId) {
                resultList.add(r);
                break;
            }
        }
        return resultList;
    }

    /**
     * Returns the number of active reservations.
     *
     * @return The number of active reservations.
     */
    public int getReservationCount() {
        update();
        return reservations.size();
    }

    /**
     * Removes the given reservation, if exists.
     */
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    /**
     * Returns a list of id of available tables at given time with capacity equal or
     * larger than pax. This method automatically calls {@code update()} to clear
     * expired reservations.
     *
     * @param time Query time.
     * @param pax  Number of person at table.
     * @return An array of id of available tables, sort in order from the small (but
     *         still larger than pax) to large.
     */
    public ArrayList<Integer> getAvailableTables(LocalDateTime time, int pax) {
        ArrayList<Integer> tableIndex = new ArrayList<>();
        boolean occupied[] = getAvailableTables(time);
        for (int i = 0; i < tables.size(); i++) {
            if (!occupied[i] && tables.get(i) >= pax) {
                tableIndex.add(i);
            }
        }
        return tableIndex;
    }

    /**
     * Query available tables in given time.
     *
     * @param time The inquired time.
     * @return Boolean array indicating available tables with true.
     */
    public boolean[] getAvailableTables(LocalDateTime time) {
        update();
        boolean[] occupied = new boolean[tables.size()];
        Arrays.fill(occupied, false);
        LocalDateTime before = time.minusHours(2);
        LocalDateTime after = time.plusHours(2);
        for (Reservation r : reservations) {
            if (r.getTime().compareTo(before) > 0 && r.getTime().compareTo(after) < 0) {
                occupied[r.getTableId()] = true;
            } else if (r.getTime().compareTo(after) > 0)
                break;
        }
        return occupied;
    }

    /**
     * Removes expired reservations.
     */
    public void update() {
        LocalDateTime pivot = LocalDateTime.now().minusHours(1);
        while (!reservations.isEmpty() && reservations.first().getTime().compareTo(pivot) < 0) {
            reservations.remove(reservations.first());
        }
    }

    /**
     * Prints table information to standard output in a user-friendly way.
     */
    public void printAllTables() {
        boolean[] occupied = getAvailableTables(LocalDateTime.now());
        System.out.printf("%s%16s%20s\n", "Index", "Capacity", "Availability");
        for (int i = 0; i < tables.size(); i++) {
            System.out.printf("%d%16d%20s\n", i, tables.get(i), occupied[i] ? "No" : "Yes");
        }
    }

    public void printAllTables(LocalDateTime time) {
        boolean[] occupied = getAvailableTables(time);
        System.out.printf("%s%16s%20s\n", "Index", "Capacity", "Availability");
        for (int i = 0; i < tables.size(); i++) {
            System.out.printf("%d%16d%20s\n", i, tables.get(i), occupied[i] ? "No" : "Yes");
        }
    }

    /**
     * Prints active reservations to standard output in a user-friendly way.
     */
    public void printAllReservations() {
        update();
        System.out.print("Time\t\t\tTableId\tPax\tContact\n");
        for (Reservation r : reservations) {
            System.out.printf("%s\t%d\t%d\t%d\n", r.getTime().toString(), r.getTableId(), r.getPax(), r.getContact());
        }
    }

    /**
     * Load reservations from save file.
     */
    public void loadReservations() {
        try {
            String fileName = "database\\reservations.txt";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numOfReservations = Integer.parseInt(br.readLine());
            for (int i = 0; i < numOfReservations; i++) {
                String l = br.readLine();
                String list[] = l.split(",");
                LocalDateTime time = LocalDateTime.parse(list[0]);
                int tableId = Integer.parseInt(list[1]);
                int pax = Integer.parseInt(list[2]);
                long contact = Long.parseLong(list[3]);
                Reservation r = new Reservation(time, tableId, pax, contact);
                addReservation(r);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        update();
    }

    /**
     * Save reservation information to file.
     */
    public void saveReservations() {
        update();
        try {
            String fileName = "database\\reservations.txt";
            BufferedWriter wr = new BufferedWriter(new FileWriter(fileName));
            wr.write(reservations.size() + "\n");
            for (Reservation r : reservations) {
                wr.write(
                        r.getTime().toString() + "," + r.getTableId() + "," + r.getPax() + "," + r.getContact() + "\n");
            }
            wr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load table information from file.
     */
    public void loadTables() {
        try {
            String fileName = "database\\tables.txt";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numOfTables = Integer.parseInt(br.readLine());
            for (int i = 0; i < numOfTables; i++) {
                int capacity = Integer.parseInt(br.readLine());
                tables.add(capacity);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save table information to file.
     */
    public void saveTables() {
        try {
            String fileName = "database\\tables.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            int numOfTables = tables.size();
            bw.write(numOfTables);
            bw.write("\n");
            for (int i = 0; i < numOfTables; i++) {
                bw.write(tables.get(i) + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
