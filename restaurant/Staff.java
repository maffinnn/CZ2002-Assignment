package restaurant;

/**
 * Restaurant staff information class. {@link Staff} objects are immutable.
 *
 * @author Zhou Yiqi
 * @author Bian Siyuan
 * @author Chan Chor Cheng
 * @author Shrivastava Samruddhi
 * @author Wang Yujing
 * @version 0.0
 */
public class Staff {
    /**
     * The name of the staff.
     */
    private String name;

    /**
     * The id of the staff.
     */
    private int id;

    /**
     * The title of the staff.
     */
    private String title;

    /**
     * The gender of the staff.
     */
    // Gender inclusive practices.
    private String gender;

    /**
     * Staff constructor
     * 
     * @param name   Name of the staff
     * @param id     Id of the staff
     * @param title  Title of the staff
     * @param gender gender of the staff
     */
    public Staff(String name, int id, String title, String gender) {
        this.name = name;
        this.id = id;
        this.title = title;
        this.gender = gender;
    }

    /**
     * @return returns the name of the staff
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the Id of the staff
     */
    public int getId() {
        return id;
    }

    /**
     * @return returns the title of the staff
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return returns the gender of the staff
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the name of a staff
     * 
     * @param name the name of the staff
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the id of a staff
     * 
     * @param id the id of the staff
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the title of a staff
     * 
     * @param title the title of the staff
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the gender of a staff
     * 
     * @param gender gender of the staff
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Prints the information of the staff to standard output in a user-friendly
     * way.
     */
    public void print() {
        System.out.printf("%d\t%s\t%s\t%s\n", getId(), getName(), getTitle(), getGender());
    }

}
