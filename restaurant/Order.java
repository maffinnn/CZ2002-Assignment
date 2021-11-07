package restaurant;

import java.time.LocalDateTime;

/**
 * Order objects include a {@link Menu} object that is used for ordering items
 * and other relevant information.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Order implements Comparable<Order> {
    /**
     * A menu contains the ordered items and quantity.
     */
    public Menu orderedItems;

    /**
     * The membership discount that is applied to the order.
     */
    public double discountRate = 0.1;

    /**
     * Whether the order is made by the customer holding the membership or not.
     */
    public boolean isMember;

    /**
     * The tax rate that is applied to the order.
     */
    public double taxRate = 0.07;

    /**
     * The reservation behind the order.
     */
    public Reservation reservation;

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
        reservation.time = LocalDateTime.now();
        this.staff = staff;
        orderedItems = new Menu(-1, reservation.time.toString(), "");
        active = true;
    }

    public Order(Reservation reservation, double totalPrice) {
        this.reservation = reservation;
        orderedItems = new Menu(-1, reservation.time.toString(), "");
        this.totalPrice = totalPrice;
        active = false;
    }

    /**
     * Add item to order.
     *
     * @param item     The item to add.
     * @param quantity The quantity of the added item.
     */
    public void addItem(MenuComponent item, int quantity) {
        MenuComponent itemInOrder = orderedItems.contains(item);
        if (itemInOrder != null) {
            itemInOrder.setQuantity(itemInOrder.getQuantity() + quantity);
            return;
        }

        if (item instanceof MenuBundle) {
            MenuBundle mb = new MenuBundle((MenuBundle) item);
            mb.setQuantity(quantity);
            orderedItems.addChild(mb);
        } else {
            MenuLeaf ml = new MenuLeaf((MenuLeaf) item);
            ml.setQuantity(quantity);
            orderedItems.addChild(ml);
        }
    }

    /**
     * Returns the table used by the order.
     *
     * @return The table id used by the order.
     */
    public int getTableId() {
        return reservation.tableId;
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
        int n = orderedItems.getChildrenCount();
        for (int i = 0; i < n; i++) {
            if (orderedItems.getChild(i).code == itemCode) {
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
        System.out.printf("Server:%s\t\tTable:%d\n", staff.getId(), reservation.tableId);
        String t = String.join(" ", reservation.time.toString().split("T"));
        System.out.printf("Time:%s\n", t);
        System.out.println("--------------------------------------");
        totalPrice = 0;
        for (int i = 0; i < orderedItems.getChildrenCount(); i++) {
            MenuComponent mc = orderedItems.getChild(i);
            int quantity = mc.getQuantity();
            System.out.printf("%d\t%04d-%s\t%.2f\n", quantity, mc.code, mc.name, mc.getPrice() * quantity);
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
        System.out.printf("Server:%s\t\tTable:%d\n", staff.getId(), reservation.tableId);
        String t = String.join(" ", reservation.time.toString().split("T"));
        System.out.printf("Time:%s\n", t);
        System.out.println("--------------------------------------");
        totalPrice = 0;
        for (int i = 0; i < orderedItems.getChildrenCount(); i++) {
            MenuComponent mc = orderedItems.getChild(i);
            int quantity = mc.getQuantity();
            System.out.printf("%d\t%s\t%.2f\n", quantity, mc.name, mc.getPrice() * quantity);
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
        reservation.time = LocalDateTime.now();
    }

    /**
     * Change the starting time of the reservation to now.
     */
    public void updateTime() {
        reservation.time = LocalDateTime.now();
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

    public int compareTo(Order o2) {
        return this.reservation.compareTo(o2.reservation);
    }
}
