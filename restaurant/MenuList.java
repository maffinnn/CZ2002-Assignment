package restaurant;

/**
 * The MenuList interface defines a set of methods to accesss the classes which
 * contain multiple menu items. i.e. {@link Order} and {@link Category}.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public interface MenuList {

    /**
     * Returns a child component.
     *
     * @param index The index of the component to be returned.
     * @return The indexed component.
     */
    MenuItem getChild(int index);

    /**
     * Returns the number of children in the menu.
     *
     * @return The number of children in the menu.
     */
    int getChildrenCount();

    /**
     * Adds a component as child.
     *
     * @param child The component to be added as a child.
     */
    void addChild(MenuItem child);

    /**
     * Removes a child component by index. Does nothing if the index is out of
     * range.
     *
     * @param index The index of the component to be removed.
     */
    void removeChild(int index);

    /**
     * Search child components given an item code. Returns the index in the menu if
     * exists and -1 otherwise.
     *
     * @param itemCode The item code to search for.
     * @return The index in the menu if exists and -1 otherwise.
     */
    int contains(int itemCode);

    /**
     * Search child components given a component. Returns the identical component in
     * the menu if exists and {@code null} otherwise.
     *
     * @param mc The component to search for.
     * @return The identical component in the menu if exists and {@code null}
     *         otherwise.
     */
    MenuItem contains(MenuItem mi);
}
