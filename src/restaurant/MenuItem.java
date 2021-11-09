package restaurant;

/**
 * The MenuItem interface defines a set of methods to accesss the all types of
 * menu items, i.e. {@link MenuBundle} or {@link MenuLeaf}.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */

public interface MenuItem {
    
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    double getPrice();

    void setPrice(double price);

    int getQuantity();

    void setQuantity(int quantity);

    double getTotalPrice();

    int getCode();

    void setCode(int code);

    void print();
}
