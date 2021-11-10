package restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The Order class is a record of a collection of menu items made by customers.
 *
 * @author Zhou Yiqi
 * @author Bian Siyuan
 * @author Chan Chor Cheng
 * @author Shrivastava Samruddhi
 * @author Wang Yujing
 * @version 0.0
 */
public class Order implements MenuList, Comparable<Order> {

    /**
     * A arrya of the ordered items.
     */
    private ArrayList<MenuItem> children;

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
     * 
     * @param isMember boolean to check if an order is paid by a member of the
     *                 restaurant and the discount applied if isMember is true
     */
    public void printInvoice(boolean isMember) {
        reservation.setTime(LocalDateTime.now());
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

    /**
     * Returns a child component.
     *
     * @param index The index of the component to be returned.
     * @return The indexed component.
     * @throws IllegalArgumentException If the index is out of range.
     */
    @Override
    public MenuItem getChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        return children.get(index);
    }

    /**
     * Returns the number of children in the menu.
     *
     * @return The number of children in the menu.
     */
    @Override
    public int getChildrenCount() {
        return children.size();
    }

    /**
     * Adds a component as child.
     *
     * @param child The component to be added as a child.
     */
    @Override
    public void addChild(MenuItem child) {
        children.add(child);
    }

    /**
     * Removes a child component by index. Does nothing if the index is out of
     * range.
     *
     * @param index The index of the component to be removed.
     * @throws IllegalArgumentException If the index is out of range.
     */
    @Override
    public void removeChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        children.remove(index);
    }

    /**
     * Search child components given an item code. Returns the index in the menu if
     * exists and -1 otherwise.
     *
     * @param itemCode The item code to search for.
     * @return The index in the menu if exists and -1 otherwise.
     */
    @Override
    public int contains(int itemCode) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == itemCode)
                return i;
        }
        return -1;
    }

    /**
     * Search child components given a component. Returns the identical component in
     * the menu if exists and {@code null} otherwise.
     *
     * @param mi The component to search for.
     * @return The identical component in the menu if exists and {@code null}
     *         otherwise.
     */
    @Override
    public MenuItem contains(MenuItem mi) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == mi.getCode())
                return children.get(i);
        }
        return null;
    }

    /**
     * Get the reservation attached to an order
     * 
     * @return the reservation assiocated with an order. Every order has to be
     *         instantiated from a reservation regardless of whether the reservation
     *         is made by a customer or not. Hence {@code null} is never returned.
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Get the time of an order
     * 
     * @return Returns the order creation time
     */
    public LocalDateTime getTime() {
        if (active) {
            updateTime();
        }
        return reservation.getTime();
    }

    /**
     * Self-defined rule for order comparison according to the reservations attached
     * to the order. Two {@link Order} instances are considered equal if they refer
     * to the same reservation(reference).
     */
    public int compareTo(Order o2) {
        return this.reservation.compareTo(o2.reservation);
    }

    /**
     * Self-defined rule for order comparison according to which table this order
     * belongs to
     * 
     * @return true if the two {@link Order} object are made on the same table,
     *         false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof Order o2) {
            return o2.getReservation().getTableId() == this.reservation.getTableId();
        } else
            return false;
    }

}
