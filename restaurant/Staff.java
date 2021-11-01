package restaurant;

/**
 * Restaurant staff information. {@link Staff} objects are immutable.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Staff {
    private String name;

    private int id;

    private String title;

    // Gender inclusive practices.
    private String gender;

    public Staff(String name, int id, String title, String gender) {
        this.name = name;
        this.id = id;
        this.title = title;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGender() {
        return gender;
    }

    /**
     * Prints the information of the staff to standard output in a user-friendly
     * way.
     */
    public void print() {
        System.out.printf("%d\t%s\t%s\t%s\n", getId(), getName(), getTitle(), getGender());
    }
}
