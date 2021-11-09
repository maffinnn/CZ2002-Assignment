package restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Order implements MenuList, Comparable<Order> {

    private ArrayList<MenuItem> children;

    private String name;

    /**
     * The membership discount that is applied to the order.
     */
    private final static double discountRate = 0.1;

    /**
     * Whether the order is made by the customer holding the membership or not.
     */
    private boolean isMember;

    /**
     * The tax rate that is applied to the order.
     */
    private final static double taxRate = 0.07;

    /**
     * The reservation behind the order.
     */
    private Reservation reservation;

    /**
     * Whether the order is active or not.
     */
    private boolean active;

    /**
     * The staff created the order.
     */
    private Staff staff;

    /**
     * The total sales price per order
     */
    private double totalPrice;

    // Disable default constructor.
    private Order() {
    }

    /**
     * Construct an order from a reservation.
     *
     * @param reservation The reservation from which the order will be created. Can
     *                    not be {@code null}.
     * @param staff       The staff who created the order.
     */
    public Order(Reservation reservation, Staff staff) {
        name = reservation.getTime().toString();
        this.reservation = reservation;
        reservation.setTime(LocalDateTime.now());
        this.staff = staff;
        children = new ArrayList<>();
        active = true;
    }

    /**
     * Construct an order from database.
     *
     * @param reservation The reservation from which the order will be created. Can
     *                    not be {@code null}.
     * @param totalPrice  The recorded totalPrice of this bill.
     */
    public Order(Reservation reservation, double totalPrice) {
        name = reservation.getTime().toString();
        this.reservation = reservation;
        this.totalPrice = totalPrice;
        children = new ArrayList<>();
        active = false;
    }

    /**
     * Add item to order.
     *
     * @param item     The item to add.
     * @param quantity The quantity of the added item.
     */
    public void addItem(MenuItem item, int quantity) {
        MenuItem itemInOrder = contains(item);
        if (itemInOrder != null) {
            itemInOrder.setQuantity(itemInOrder.getQuantity() + quantity);
            return;
        }

        if (item instanceof MenuBundle) {
            MenuBundle mb = new MenuBundle((MenuBundle) item);
            mb.setQuantity(quantity);
            addChild(mb);
        } else {
            MenuLeaf ml = new MenuLeaf((MenuLeaf) item);
            ml.setQuantity(quantity);
            addChild(ml);
        }
    }

    /**
     * Returns the table used by the order.
     *
     * @return The table id used by the order.
     */
    public int getTableId() {
        return reservation.getTableId();
    }

    /**
     * Search ordered items given an item code. Returns the index in the order if
     * exists and -1 otherwise.
     *
     * @param itemCode The item code to search for.
     * @return The index in the order if exists and -1 otherwise.
     */
    public int getItemIndex(int itemCode) {
        int ret = -1;
        int n = getChildrenCount();
        for (int i = 0; i < n; i++) {
            if (getChild(i).getCode() == itemCode) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * Prints the information of the order to standard output in a user-friendly
     * way.
     */
    public void print() {
        System.out.println("\n==========================================");
        System.out.println("Restaurant 0.0");
        System.out.printf("Server:%s\t\tTable:%d\n", staff.getId(), reservation.getTableId());
        String t = String.join(" ", reservation.getTime().toString().split("T"));
        System.out.printf("Time:%s\n", t);
        System.out.println("--------------------------------------");
        totalPrice = 0;
        for (int i = 0; i < getChildrenCount(); i++) {
            MenuItem mc = getChild(i);
            int quantity = mc.getQuantity();
            System.out.printf("%d\t%04d-%s\t%.2f\n", quantity, mc.getCode(), mc.getName(), mc.getPrice() * quantity);
            totalPrice += mc.getTotalPrice();
        }
        System.out.println("--------------------------------------");

        System.out.printf("\t\tSub-Total:\t%.2f\n", totalPrice);
        System.out.println("==========================================\n");
    }

    /**
     * Prints an invoice of the order to standard output in a user-friendly way and
     * set the order to inactive.
     */
    public void printInvoice(boolean isMember) {
        if (!active)
            return;
        active = false;
        System.out.println("\n==========================================");
        System.out.println("Restaurant 0.0");
        System.out.printf("Server:%s\t\tTable:%d\n", staff.getId(), reservation.getTableId());
        String t = String.join(" ", reservation.getTime().toString().split("T"));
        System.out.printf("Time:%s\n", t);
        System.out.println("--------------------------------------");
        totalPrice = 0;
        for (int i = 0; i < getChildrenCount(); i++) {
            MenuItem mc = getChild(i);
            int quantity = mc.getQuantity();
            System.out.printf("%d\t%s\t%.2f\n", quantity, mc.getName(), mc.getPrice() * quantity);
            totalPrice += mc.getTotalPrice();
        }
        System.out.println("--------------------------------------");

        System.out.printf("%24s\t%.2f\n", "Sub-Total:", totalPrice);

        if (isMember) {
            double discount = totalPrice * discountRate;
            totalPrice -= discount;
            System.out.printf("%24s\t%.2f\n", "Membership discount:", discount);
        }

        double serviceCharge = totalPrice * 0.1;
        double tax = totalPrice * 1.1 * taxRate;

        System.out.printf("%24s\t%.2f\n", "Service Charge:", serviceCharge);
        System.out.printf("%24s\t%.2f\n", "Tax:", tax);
        System.out.println("--------------------------------------");
        System.out.printf("%24s\t%.2f\n", "TOTAL:", totalPrice + serviceCharge + tax);
        System.out.println("==========================================\n");
        reservation.setTime(LocalDateTime.now());
    }

    /**
     * Change the starting time of the reservation to now.
     */
    public void updateTime() {
        reservation.setTime(LocalDateTime.now());
    }

    /**
     * Returns the total price of the order (which is calculated in print()).
     *
     * @return The total price of the order.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Returns the id of the staff who created the order.
     *
     * @return The id of the staff who created the order.
     */
    public int getStaffId() {
        return staff.getId();
    }

    public String getName() {
        return name;
    }

    @Override
    public MenuItem getChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        return children.get(index);
    }

    @Override
    public int getChildrenCount() {
        return children.size();
    }

    @Override
    public void addChild(MenuItem child) {
        children.add(child);
    }

    @Override
    public void removeChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        children.remove(index);
    }

    @Override
    public int contains(int itemCode) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == itemCode)
                return i;
        }
        return -1;
    }

    @Override
    public MenuItem contains(MenuItem mi) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == mi.getCode())
                return children.get(i);
        }
        return null;
    }

    public int compareTo(Order o2) {
        return this.reservation.compareTo(o2.reservation);
    }

    public Reservation getReservation() {
        return reservation;
    }

    public LocalDateTime getTime() {
        if (active) {
            updateTime();
        }
        return reservation.getTime();
    }
}
