package restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Sales report in a given time period.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class SalesReport {
    /**
     * Starting date.
     */
    private LocalDate from;

    /**
     * End date.
     */
    private LocalDate to;

    /**
     * Total revenue in the given time period.
     */
    private double totalRevenue;

    /**
     * Quantity of menu items sold.
     */
    private HashMap<MenuComponent, Integer> itemSoldTable;

    private SalesReport() {
    }

    public SalesReport(LocalDate start, LocalDate end) {
        from = start;
        to = end;
        totalRevenue = 0;
        itemSoldTable = new HashMap<MenuComponent, Integer>();
    }
    // I'm too sleepy to write the interface now. Forgive me.
    // Basic idea: processOrder(order) function that automatically process an order
    // (add to total revenue, update sold quantity of individual item).
    // And a print() function.

    public void processOrder(Order thisOrder) {
        for (int i = 0; i < thisOrder.orderedItems.getChildrenCount(); i++) {
            MenuComponent item = thisOrder.orderedItems.getChild(i);
            if (itemSoldTable.containsKey(item))
                itemSoldTable.put(item, itemSoldTable.get(item) + 1);
            else
                itemSoldTable.put(item, 1);
        }
        totalRevenue += thisOrder.getTotalPrice();
    }

    public void print() {
        System.out.println("Item\t\t\tQty");
        for (MenuComponent item : itemSoldTable.keySet()) {
            System.out.printf("%s\t\t%d\n", item.name, itemSoldTable.get(item));
        }
        System.out.printf("\t\tTOTAL REVENUE:\t%.2f\n", totalRevenue);
    }

}
