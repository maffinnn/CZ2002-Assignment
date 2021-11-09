package restaurant;

import java.util.ArrayList;

/**
 * The MenuBundle class is a subclass of {@link AbstractMenuItem} class which
 * represents a promotional items sold in set.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class MenuBundle extends AbstractMenuItem implements MenuList {

    /**
     * A arrya of the menu items.
     */
    private ArrayList<MenuItem> children;

    /**
     * MenuBundle constructor
     *
     * @param code        The code of the menu bundle
     * @param name        The name of menu bundle
     * @param description The description of menu bundle
     */
    public MenuBundle(int code, String name, String description) {
        super(code, name, description);
        this.children = new ArrayList<>();
    }

    /**
     * MenuBundle copy constructor
     *
     * @param mb the menu bundle for copy
     */
    public MenuBundle(MenuBundle mb) {
        this(mb.getCode(), mb.getName(), mb.getDescription());
        children = new ArrayList<>(mb.children);
        this.setPrice(mb.getPrice());
    }

    /**
     * Returns a child component.
     *
     * @param index The index of the component to be returned.
     * @return The indexed component.
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

    /**
     * Print the menu bundle item in the user-friendly format
     */
    @Override
    public void print() {
        System.out.printf("%d\t%s\t%.2f\n", getQuantity(), getName(), getTotalPrice());
        for (MenuItem mi : children) {
            System.out.printf("\t%s\n", mi.getName());
        }
    }

    /**
     * Print the menu bundle item in the format presented in the sales revenue
     * report
     */
    public void print(boolean isInvoice) {
        if (isInvoice) {
            print();
            return;
        }
        for (MenuItem mi : children) {
            System.out.printf("%04d\t%s\n", mi.getCode(), mi.getName());
        }
    }

    /**
     * Self-defined rule for checking the equivalence of two menubundle
     */
    public boolean equals(Object o) {
        if (this == o) // reference
            return true;
        if (o instanceof MenuBundle mb) {
            return this.getName().compareTo(mb.getName()) == 0 && this.getPrice() == mb.getPrice();
        } else
            return false;
    }
}
