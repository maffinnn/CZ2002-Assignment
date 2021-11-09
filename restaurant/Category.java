package restaurant;

import java.util.ArrayList;

/**
 * The category class is used in restaurant menu. The {@link Category} is a
 * composite class to hold an array list of menu items, be it {@link MenuLeaf}
 * or {@link MenuBundle}.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Category implements MenuList {
    /**
     * The name of the category.
     */
    private String name;
    /**
     * The content (children) of the menu.
     */
    private ArrayList<MenuItem> children;

    public Category() {
    }

    /**
     * Category constructor
     * 
     * @param name The name of the category.
     */
    public Category(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    /**
     * @return returns the name of a category
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of a category
     * 
     * @param name The name of the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a child component.
     *
     * @param index The index of the component to be returned.
     * @return The indexed component.
     * @throws IllegalArgumentException If the index is out of range.
     */
    @Override
    public MenuItem getChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        return children.get(index);
    }

    /**
     * Returns the number of children in the menu.
     *
     * @return The number of children in the menu.
     */
    @Override
    public int getChildrenCount() {
        return children.size();
    }

    /**
     * Adds a component as child.
     *
     * @param child The component to be added as a child.
     */
    @Override
    public void addChild(MenuItem child) {
        children.add(child);
    }

    /**
     * Removes a child component by index. Does nothing if the index is out of
     * range.
     *
     * @param index The index of the component to be removed.
     * @throws IllegalArgumentException If the index is out of range.
     */
    @Override
    public void removeChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        children.remove(index);
    }

    /**
     * Search child components given an item code. Returns the index in the menu if
     * exists and -1 otherwise.
     *
     * @param itemCode The item code to search for.
     * @return The index in the menu if exists and -1 otherwise.
     */
    @Override
    public int contains(int itemCode) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == itemCode)
                return i;
        }
        return -1;
    }

    /**
     * Search child components given a component. Returns the identical component in
     * the menu if exists and {@code null} otherwise.
     *
     * @param mc The component to search for.
     * @return The identical component in the menu if exists and {@code null}
     *         otherwise.
     */
    @Override
    public MenuItem contains(MenuItem mi) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == mi.getCode())
                return children.get(i);
        }
        return null;
    }

}
