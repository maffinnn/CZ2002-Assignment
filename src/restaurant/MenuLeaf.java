package restaurant;

public class MenuLeaf extends AbstractMenuItem{

    public MenuLeaf(int code, String name, String description) {
        super(code, name, description);
    }

    public MenuLeaf(int code, String name, String description, double price) {
        super(code, name, description);
        setPrice(price);
    }

    public MenuLeaf(MenuLeaf ml){
        this(ml.getCode(), ml.getName(), ml.getDescription(), ml.getPrice());
    }

    public void print(){
        System.out.printf("%d\t%s\t%.2f\n", getQuantity(), getName(), getTotalPrice());
    }
}
