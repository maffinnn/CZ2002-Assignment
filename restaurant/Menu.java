package restaurant;
import java.util.ArrayList;

/**
 * The menu objects can be used as restaurant menu, menu category, and order
 * list. The {@link Menu} serves as the composite class in the composite pattern
 * design of menu related classes.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Menu extends MenuComponent {
    /**
     * The content (children) of the menu.
     */
    private ArrayList<MenuComponent> children;

    /**
     * @param name        The name of the component.
     * @param description The description of the component.
     */
    public Menu(String name, String description) {
        super(name, description);
        children = new ArrayList<>();
    }

    /**
     * Returns a child component.
     *
     * @param index The index of the component to be returned.
     * @return The indexed component.
     * @throws IllegalArgumentException If the index is out of range.
     */
    public MenuComponent getChild(int index) {
        // TODO
        return children.get(index);
    }

    /**
     * Returns the number of children in the menu.
     *
     * @return The number of children in the menu.
     */
    public int getChildrenCount() {
        // TODO
        return children.size();
    }

    /**
     * Adds a component as child.
     *
     * @param child The component to be added as a child.
     */
    public void addChild(MenuComponent child) {
        // TODO
        children.add(child);
    }

    /**
     * Removes a child component by index. Does nothing if the index is out of
     * range.
     *
     * @param index The index of the component to be removed.
     * @throws IllegalArgumentException If the index is out of range.
     */
    public void removeChild(int index) {
        // TODO
        if(index < 0 || index > children.size()){
            throw new IllegalArgumentException();
        } else children.remove(index);
    }

    /**
     * Prints the content of the menu to standard output in a user-friendly
     * way.
     */
    public void print() {
        // TODO
    }
}
