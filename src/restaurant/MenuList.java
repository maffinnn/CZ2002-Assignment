package restaurant;

public interface MenuList {
    
    MenuItem getChild(int index);

    int getChildrenCount();

    void addChild(MenuItem child);

    void removeChild(int index);

    int contains(int itemCode);

    MenuItem contains(MenuItem mi);
}
