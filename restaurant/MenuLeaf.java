package restaurant;

/**
 * The individual items in restaurant menu.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class MenuLeaf extends MenuComponent {
    /**
     * The price of the item.
     */
    private double price;

    /**
     * The quantity of the item. Mainly used in orders.
     */
    private int quantity;

    public MenuLeaf(int code, String name, String description) {
        super(code, name, description);
    }

    public MenuLeaf(int code, String name, String description, double price) {
        super(code, name, description);
        this.price = price;
    }

    public MenuLeaf(MenuLeaf mf) {
        this(mf.code, mf.name, mf.description, mf.price);
    }

    /**
     * Returns the price of the item.
     *
     * @return The price of the item in SGD (should always be rounded to two decimal
     *         places).
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the total price of items.
     *
     * @return The price of the item in SGD (should always be rounded to two decimal
     *         places).
     */
    public double getTotalPrice() {
        return price * quantity;
    }

    /**
     * Sets the price of the item.
     *
     * @param price The price of the item in SGD.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the quantity of the item (ordered).
     *
     * @return The quantity of the item (ordered).
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity The quantity of the item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Prints the content of the item to standard output in a user-friendly way.
     */
    public void print() {
        System.out.printf("%d\t%s\t%.2f\n", quantity, name, getTotalPrice());
    }
}
