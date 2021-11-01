package restaurant;

/**
 * The base class for all menu related classes ({@link Menu},
 * {@link BundleItem}, {@link MenuLeaf}). These classes constitutes a composite
 * pattern with {@link MenuComponent} serving as the uniformed interface.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public abstract class MenuComponent {
    /**
     * The code of the component.
     */
    public int code;
    /**
     * The name of the component.
     */
    public String name;

    /**
     * The description of the component.
     */
    public String description;

    /**
     * @param name        The name of the component.
     * @param description The description of the component.
     */
    public MenuComponent(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the price of the component.
     *
     * @return The price of the component in SGD (should always be rounded to two
     *         decimal places).
     * @throws UnsupportedOperationException If the component doesn't have a price
     *                                       (e.g. {@link Menu} ).
     */
    public double getPrice() {
        throw new UnsupportedOperationException();
    }

    public double getTotalPrice() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the price of the component.
     *
     * @param price The price of the component in SGD.
     * @throws UnsupportedOperationException If the component doesn't have a price
     *                                       (e.g. {@link Menu} ).
     */
    public void setPrice(double price) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the quantity of the component. Mainly used in context of orders.
     *
     * @return The quantity of the component.
     * @throws UnsupportedOperationException If the component doesn't have a
     *                                       quantity (e.g. {@link Menu} ).
     */
    public int getQuantity() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the quantity of the component. Mainly used in context of orders.
     *
     * @param quantity The quantity of the component.
     * @throws UnsupportedOperationException If the component doesn't have a
     *                                       quantity (e.g. {@link Menu} ).
     */
    public void setQuantity(int quantity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a child component.
     *
     * @param index The index of the component to be returned.
     * @return The indexed component.
     * @throws UnsupportedOperationException If the component is not a composite
     *                                       (e.g. {@link MenuLeaf}).
     * @throws IllegalArgumentException      If the index is out of range.
     */
    public MenuComponent getChild(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the number of children in the composite.
     *
     * @return The number of children in the composite.
     * @throws UnsupportedOperationException If the component is not a composite
     *                                       (e.g. {@link MenuLeaf}).
     */
    public int getChildrenCount() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a component as child.
     *
     * @param child The component to be added as a child.
     * @throws UnsupportedOperationException If the component is not a composite
     *                                       (e.g. {@link MenuLeaf}).
     */
    public void addChild(MenuComponent child) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes a child component by index. Does nothing if the index is out of
     * range.
     *
     * @param index The index of the component to be removed.
     * @throws UnsupportedOperationException If the component is not a composite
     *                                       (e.g. {@link MenuLeaf}).
     * @throws IllegalArgumentException      If the index is out of range.
     */
    public void removeChild(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Prints the content of the component to standard output in a user-friendly
     * way. This is the minimal default implementation for all menu components.
     */
    public void print() {
        System.out.println(this.name + ": " + this.description);
    }

}
