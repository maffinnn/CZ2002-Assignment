package restaurant;

/**
 * The base class for all menu related classes {@link MenuBundle},
 * {@link MenuLeaf}). These classes constitutes a composite pattern with
 * {@link AbstractMenuItems} serving as the uniformed interface.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */

public abstract class AbstractMenuItem implements MenuItem {
    /**
     * The name of the item.
     */
    private String name;
    /**
     * The description of the component.
     */
    private String description;
    /**
     * The price of the bundle.
     */
    private double price;
    /**
     * The quantity of the bundle. Mainly used in orders.
     */
    private int quantity;
    /**
     * The code of the component.
     */
    private int code;

    /**
     * AbstractMenuItem constructor
     * 
     * @param code        The code of the bundle.
     * @param name        The name of the bundle.
     * @param description The description of the bundle.
     */
    public AbstractMenuItem(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * @return returns the name of a menu item
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of a menu item
     * 
     * @param name The name of the menu item
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description of a menu item
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of a menu item
     * 
     * @param description the description of a menu item
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price of a menu item
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of a menu item
     * 
     * @param price the price of a menu item
     */
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the quantity of a menu item
     */
    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of a menu
     * 
     * @param quantity the quantity of a menu item
     */
    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the total price of a menu item
     */
    @Override
    public double getTotalPrice() {
        return price * quantity;
    }

    /**
     * @return the total price of a menu item
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * Set the code of a menu item
     * 
     * @param code the code of a menu item
     */
    @Override
    public void setCode(int code) {
        this.code = code;
    }
}
