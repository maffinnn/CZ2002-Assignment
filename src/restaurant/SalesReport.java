package restaurant;

import java.time.LocalDate;
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
    private HashMap<MenuItem, Integer> itemSoldTable;

    private SalesReport() {
    }

    /**
     * @param start The starting date of the sales report.
     * @param end   The end date of the sales report.
     */
    public SalesReport(LocalDate start, LocalDate end) {
        from = start;
        to = end;
        totalRevenue = 0;
        itemSoldTable = new HashMap<MenuItem, Integer>();
    }

    /**
     * Add an order the the revenue report.
     *
     * @param thisOrder The order to add.
     */
    public void processOrder(Order thisOrder) {
        for (int i = 0; i < thisOrder.getChildrenCount(); i++) {
            MenuItem item = thisOrder.getChild(i);
            if (itemSoldTable.containsKey(item))
                itemSoldTable.put(item, itemSoldTable.get(item) + 1);
            else
                itemSoldTable.put(item, 1);
        }
        totalRevenue += thisOrder.getTotalPrice();
    }

    public void print() {
        System.out.printf("Item%26s\n", "Qty");
        for (MenuItem item : itemSoldTable.keySet()) {
            System.out.printf("%-24s\t%d\n", item.getName(), itemSoldTable.get(item));
        }
        System.out.printf("%24s\t%.2f\n", "TOTAL REVENUE:", totalRevenue);
    }
}
