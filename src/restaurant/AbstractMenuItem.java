package restaurant;

/**
 * The base class for all menu related classes ({@link MenuItem},
 * {@link MenuBundle}, {@link MenuLeaf}). These classes constitutes a composite
 * pattern with {@link AbstractMenuItems} serving as the uniformed interface.
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
     * @param code        The code of the bundle.
     * @param name        The name of the bundle.
     * @param description The description of the bundle.
     */
    public AbstractMenuItem(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }
}
