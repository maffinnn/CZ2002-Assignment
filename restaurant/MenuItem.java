package restaurant;

/**
 * The MenuItem interface defines a set of methods to accesss the menu related
 * classes i.e.{@link MenuBundle} and {@link MenuLeaf}.
 *
 * @author Zhou Yiqi
 * @author Bian Siyuan
 * @author Chan Chor Cheng
 * @author Shrivastava Samruddhi
 * @author Wang Yujing
 * @version 0.0
 */
public interface MenuItem {

    /**
     * @return returns the name of a menu item
     */
    String getName();

    /**
     * Set the name of a menu item
     * 
     * @param name The name of the menu item
     */
    void setName(String name);

    /**
     * @return the description of a menu item
     */
    String getDescription();

    /**
     * Set the description of a menu item
     * 
     * @param description the description of a menu item
     */
    void setDescription(String description);

    /**
     * @return the price of a menu item
     */
    double getPrice();

    /**
     * Set the price of a menu item
     * 
     * @param price the price of a menu item
     */
    void setPrice(double price);

    /**
     * @return the quantity of a menu item
     */
    int getQuantity();

    /**
     * Set the quantity of a menu
     * 
     * @param quantity the quantity of a menu item
     */
    void setQuantity(int quantity);

    /**
     * @return the total price of a menu item
     */
    double getTotalPrice();

    /**
     * @return the total price of a menu item
     */
    int getCode();

    /**
     * Set the code of a menu item
     * 
     * @param code the code of a menu item
     */
    void setCode(int code);

    /**
     * Print a menu item
     */
    void print();
}
