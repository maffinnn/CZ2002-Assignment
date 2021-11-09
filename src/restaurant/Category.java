package restaurant;

import java.util.ArrayList;

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
        children = new ArrayList<>();
    }

    /**
     * @param name The name of the category.
     */
    public Category(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public MenuItem getChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        return children.get(index);
    }

    @Override
    public int getChildrenCount() {
        return children.size();
    }

    @Override
    public void addChild(MenuItem child) {
        children.add(child);
    }

    @Override
    public void removeChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IllegalArgumentException();
        }
        children.remove(index);
    }

    @Override
    public int contains(int itemCode) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == itemCode)
                return i;
        }
        return -1;
    }

    @Override
    public MenuItem contains(MenuItem mi) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == mi.getCode())
                return children.get(i);
        }
        return null;
    }

}
