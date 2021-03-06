package restaurant;

/**
 * The MenuLeaf class is a subclass of {@link AbstractMenuItem} class which
 * represents individual alacarte item.
 *
 * @author Zhou Yiqi
 * @author Bian Siyuan
 * @author Chan Chor Cheng
 * @author Shrivastava Samruddhi
 * @author Wang Yujing
 * @version 0.0
 */
public class MenuLeaf extends AbstractMenuItem {

    /**
     * MenuLeaf constructor
     *
     * @param code        The code of a menu leaf
     * @param name        The name of a menu leaf
     * @param description The description of a menu leaf
     */
    public MenuLeaf(int code, String name, String description) {
        super(code, name, description);
    }

    /**
     * MenuLeaf constructor for loading from database
     *
     * @param code        The code of a menu leaf
     * @param name        The name of a menu leaf
     * @param description The description of a menu leaf
     * @param price       The price of a menu leaf
     */
    public MenuLeaf(int code, String name, String description, double price) {
        super(code, name, description);
        setPrice(price);
    }

    /**
     * MenuLeaf copy constructor
     *
     * @param ml The {@link MenuLeaf} object to copy from
     */
    public MenuLeaf(MenuLeaf ml) {
        this(ml.getCode(), ml.getName(), ml.getDescription(), ml.getPrice());
    }

    /**
     * Print the menu leaf in a user-friendly format
     */
    public void print() {
        System.out.printf("%d\t%s\t%.2f\n", getQuantity(), getName(), getTotalPrice());
    }
}
