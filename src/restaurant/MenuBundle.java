package restaurant;

import java.util.ArrayList;

public class MenuBundle extends AbstractMenuItem implements MenuList{

    private ArrayList<MenuItem> children;

    public MenuBundle(int code, String name, String description) {
        super(code, name, description);
        this.children = new ArrayList<>();
    }

    public MenuBundle(MenuBundle mb) {
        this(mb.getCode(), mb.getName(), mb.getDescription());
        children = new ArrayList<>(mb.children);
        this.setPrice(mb.getPrice());
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
    public MenuItem contains(MenuItem mi){
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getCode() == mi.getCode())
                return children.get(i);
        }
        return null;
    }

    @Override
    public void print() {
        System.out.printf("%d\t%s\t%.2f\n", getQuantity(), getName(), getTotalPrice());
        for (MenuItem mi : children) {
            System.out.printf("\t%s\n", mi.getName());
        }
    }

    public void print(boolean isInvoice) {
        if (isInvoice) {
            print();
            return;
        }
        for (MenuItem mi : children) {
            System.out.printf("%04d\t%s\n", mi.getCode(), mi.getName());
        }
    }

    public boolean equals(Object o) {
        if (this == o) // reference
            return true;
        if (o instanceof MenuBundle mb) {
            return this.getName().compareTo(mb.getName()) == 0 && this.getPrice() == mb.getPrice();
        } else
            return false;
    }
}
