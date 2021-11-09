package restaurant;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class RestaurantApp {

    /**
     * Scanner used across all subroutines.
     */
    InputHandler sc = new InputHandler();

    /**
     * Restaurant backend.
     */
    Restaurant myRestaurant;

    public static void main(String args[]) {
        System.out.println("Initiating ... ");
        RestaurantApp myApp = new RestaurantApp();
        myApp.run();
    }

    /**
     * Menu item editing subroutine.
     *
     * @param category The item to be edited.
     * @return Whether the edit is successful.
     */
    private boolean editItem(Category category) {
        System.out.print("0.\tGo back\n" + "1.\tAdd item\n" + "2.\tUpdate item\n" + "3.\tRemove item\n"
                + "4.\tRemove this category\n" + "Choose option:");
        int op = sc.getInt();
        switch (op) {
        case 1: {
            // add item
            if (category.getName().compareTo("Combo") != 0 && category.getName().compareTo("Promotions") != 0) {
                System.out.print("Item name: ");
                String name = sc.nextLine();
                System.out.print("Item description: ");
                String description = sc.nextLine();
                System.out.print("Item price: ");
                double price = sc.getDouble();
                Random rand = new Random();
                int itemCode;
                do {
                    itemCode = rand.nextInt(10000);
                } while (itemCode < 0 || myRestaurant.menuReference.containsKey(itemCode));

                MenuLeaf item = new MenuLeaf(itemCode, name, description, price);
                myRestaurant.menuReference.put(itemCode, item);
                category.addChild(item);
            } else {
                // add bundle
                System.out.print("Combo name: ");
                String name = sc.nextLine();
                System.out.print("Number of items this combo contains: ");
                int numOfItems = sc.getInt();

                Random rand = new Random();
                int ComboCode;
                do {
                    ComboCode = rand.nextInt(10000);
                } while (ComboCode < 0 || myRestaurant.menuReference.containsKey(ComboCode));
                MenuBundle Combo = new MenuBundle(ComboCode, name, "");
                displayMenu();
                for (int i = 0; i < numOfItems; i++) {

                    System.out.print("Combo item code: ");
                    int itemCode = sc.getInt();
                    while (!myRestaurant.menuReference.containsKey(itemCode)) {
                        System.out.println("Invalid item code, please try again");
                        System.out.print("Combo item code: ");
                        itemCode = sc.getInt();
                    }
                    Combo.addChild(myRestaurant.menuReference.get(itemCode));
                }

                System.out.print("Combo price: ");
                double price = sc.getDouble();
                Combo.setPrice(price);
                myRestaurant.menuReference.put(ComboCode, Combo);
                category.addChild(Combo);
            }
        }
            break;
        case 2: {
            // update item
            if (category.getName().compareTo("Combo") != 0 && category.getName().compareTo("Promotions") != 0) {
                displayItems(category);
            } else {
                displayCombos(category);
            }
            System.out.println("Enter item code: ");
            int itemCode = sc.getInt();
            while (!myRestaurant.menuReference.containsKey(itemCode)) {
                System.out.println("Invalid item code, please try again");
                System.out.print("Enter item code: ");
                itemCode = sc.getInt();
            }

            MenuItem curItem = myRestaurant.menuReference.get(itemCode);

            if (curItem instanceof MenuBundle mb) {

                System.out.print("0.\tGo back\n" + "1.\tupdate name\n" + "2.\tupdate description\n"
                        + "3.\tupdate price\n" + "4.\tadd item\n" + "5.\tremove item\n" + "Choose option: ");
                int curOp = sc.getInt();
                while (curOp >= 1 && curOp <= 5) {
                    switch (curOp) {
                    case 1: {
                        System.out.print("Enter new name: ");
                        curItem.setName(sc.nextLine());
                        break;
                    }
                    case 2: {
                        System.out.print("Enter new description: ");
                        curItem.setDescription(sc.nextLine());
                        break;
                    }
                    case 3: {
                        System.out.print("Enter new price: ");
                        double newPrice = sc.getDouble();
                        while (newPrice <= 0) {
                            System.out.println("Invalid price, please enter again.");
                            System.out.print("Enter new price: ");
                            newPrice = sc.getDouble();
                        }
                        curItem.setPrice(newPrice);
                        break;
                    }
                    case 4: {
                        displayMenu();
                        System.out.print("Enter item code: ");
                        itemCode = sc.getInt();
                        while (!myRestaurant.menuReference.containsKey(itemCode)) {
                            System.out.println("Invalid item code, please try again");
                            System.out.print("Enter item code: ");
                            itemCode = sc.getInt();
                        }
                        mb.addChild(myRestaurant.menuReference.get(itemCode));
                        break;
                    }
                    case 5: {
                        MenuBundle combo = (MenuBundle) curItem;
                        combo.print(false);
                        System.out.println("Enter item code: ");
                        itemCode = sc.getInt();
                        int index = combo.contains(itemCode);
                        if (index == -1) {
                            System.out.println("Unsuccessful removal.");
                            break;
                        }
                        combo.removeChild(index);
                        break;
                    }
                    default:
                        break;
                    }
                    System.out.print("0.\tGo back\n" + "1.\tupdate name\n" + "2.\tupdate description\n"
                            + "3.\tupdate price\n" + "4.\tadd item\n" + "5.\tremove item\n" + "Choose option: ");
                    curOp = sc.getInt();
                }

            } else {
                System.out.print("0.\tGo back\n" + "1.\tupdate name\n" + "2.\tupdate description\n"
                        + "3.\tupdate price\n" + "Choose option: ");
                int curOp = sc.getInt();
                switch (curOp) {
                case 1: {
                    System.out.print("Enter new name: ");
                    curItem.setName(sc.nextLine());
                    break;
                }
                case 2: {
                    System.out.print("Enter new description: ");
                    curItem.setDescription(sc.nextLine());
                    break;
                }
                case 3: {
                    System.out.print("Enter new price: ");
                    double newPrice = sc.getDouble();
                    while (newPrice <= 0) {
                        System.out.println("Invalid price, please enter again.");
                        System.out.print("Enter new price: ");
                        newPrice = sc.getDouble();
                    }
                    curItem.setPrice(newPrice);
                    break;
                }
                default:
                    break;
                }
            }
            break;

        }
        case 3: {
            if (category.getName().compareTo("Combo") != 0 && category.getName().compareTo("Promotions") != 0) {
                displayItems(category);
            } else {
                displayCombos(category);
            }
            System.out.println("Enter item code: ");
            int itemCode = sc.getInt();
            while (!myRestaurant.menuReference.containsKey(itemCode)) {
                System.out.println("Invalid item code, please try again");
                System.out.print("Enter item code: ");
                itemCode = sc.getInt();
            }
            int itemIndex = ((Category) category).contains(itemCode);
            if (itemIndex == -1) {
                break;
            }
            category.removeChild(itemIndex);
            break;
        }
        default:
            return false;
        }
        return true;
    }

    /**
     * Restaurant menu edit subroutine.
     */
    private void editMenu() {
        boolean flag = false;
        while (!flag) {
            System.out.print("\t0.Go back\n");
            displayCategory();
            System.out.printf("\t%d.Create a New Category\n", myRestaurant.menu.size() + 1);
            System.out.print("Choose Category:");
            int op = sc.getInt();
            Category category;
            if (op == 0)
                return;
            else if (op > myRestaurant.menu.size()) {
                System.out.print("Category Name:");
                String name = sc.nextLine();
                category = new Category(name);
                myRestaurant.menu.add(category);
            } else {
                category = myRestaurant.menu.get(op - 1);
            }
            flag = editItem(category);
        }

    }

    /**
     * Print sales report.
     */
    private void printReport(LocalDate from, LocalDate to) {
        SalesReport report = myRestaurant.generateSalesReport(from, to);
        report.print();
    }

    /**
     * Print menu category.
     */
    private void displayCategory() {
        ArrayList<Category> menu = myRestaurant.menu;
        // System.out.print("\t" + menu.name + "category");
        for (int i = 0; i < menu.size(); i++) {
            Category category = menu.get(i);
            System.out.printf("\t%d.%s\n", i + 1, category.getName());
        }
    }

    /**
     * Print all itmes in a category.
     *
     * @param category The category to be printed.
     */
    private void displayItems(Category category) {
        for (int j = 0; j < category.getChildrenCount(); j++) {
            MenuItem item = category.getChild(j);
            System.out.printf("%04d\t%s\t%.2f\n", item.getCode(), item.getName(), item.getPrice());
            System.out.printf("\t%s\n", item.getDescription());
        }
    }

    /**
     * Print combo item.
     *
     * @param combo The combo item to be printed.
     */
    private void displayCombos(Category combo) {
        for (int j = 0; j < combo.getChildrenCount(); j++) {
            MenuBundle mb = (MenuBundle) combo.getChild(j);
            System.out.printf("%04d\t%s\t%.2f\n", mb.getCode(), mb.getName(), mb.getPrice());
            for (int k = 0; k < mb.getChildrenCount(); k++) {
                MenuItem item = mb.getChild(k);
                System.out.printf("\t\t%s\n", item.getName());
            }
        }
    }

    /**
     * Print the menu of the restaurant.
     */
    private void displayMenu() {
        ArrayList<Category> menu = myRestaurant.menu;
        System.out.println("Menu");
        for (int i = 0; i < menu.size(); i++) {
            Category category = menu.get(i);
            System.out.println(category.getName());
            if (category.getName().compareTo("Combo") != 0 && category.getName().compareTo("Promotions") != 0) {
                displayItems(category);
            } else {
                displayCombos(category);
            }
        }
    }

    private void displayReservations() {
        myRestaurant.printAllReservations();
    }

    private void displayTables() {
        myRestaurant.printAllTables();
    }

    private void displayTables(LocalDateTime time) {
        myRestaurant.printAllTables(time);
    }

    /**
     * Print active orders.
     */
    private void displayOrders() {
        System.out.println("activeOrders");
        int numOfOrders = myRestaurant.activeOrders.size();
        for (int i = 0; i < numOfOrders; i++) {
            Order o = myRestaurant.activeOrders.get(i);
            o.print();
        }
    }

    /**
     * Edit an order.
     *
     * @param o Order to be edited.
     */
    private void editOrder(Order o) {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.Add item\n" + "\t2.Remove item\n");
            op = sc.getInt();
            switch (op) {
            case 1:
                displayMenu();
                System.out.print("Enter item code: ");
                int itemCode = sc.getInt();
                while (!myRestaurant.menuReference.containsKey(itemCode)) {
                    System.out.println("Invalid item code, please enter again");
                    System.out.print("Enter item code: ");
                    itemCode = sc.getInt();
                }
                System.out.print("Enter quantity: ");
                int quantity = sc.getInt();
                o.addItem(myRestaurant.menuReference.get(itemCode), quantity);
                System.out.printf("%d serving(s) of %s has been added\n", quantity,
                        myRestaurant.menuReference.get(itemCode).getName());
                break;
            case 2:
                o.print();
                if (o.getChildrenCount() == 0) {
                    System.out.println("No item in this order.");
                    break;
                }
                System.out.print("Enter item code: ");
                int code = sc.getInt();
                while (!myRestaurant.menuReference.containsKey(code) || o.getItemIndex(code) == -1) {
                    System.out.println("Invalid item code, please enter again");
                    System.out.print("Enter item code: ");
                    code = sc.getInt();
                }
                int itemIndex = o.getItemIndex(code);
                MenuItem itemInOrder = o.getChild(itemIndex);
                System.out.print("Enter item quantity: ");
                quantity = sc.getInt();
                while (quantity > itemInOrder.getQuantity() || quantity < 0) {
                    System.out.println("Invalid quantity, please enter again");
                    System.out.print("Enter item quantity: ");
                    quantity = sc.getInt();
                }
                if (quantity == itemInOrder.getQuantity()) {
                    o.removeChild(itemIndex);
                    break;
                }
                itemInOrder.setQuantity(itemInOrder.getQuantity() - quantity);
                break;
            default:
                break;
            }

        } while (op > 0 && op < 3);

    }

    /**
     * Main menu of the app.
     */
    private void run() {
        myRestaurant = new Restaurant();
        int op;
        do {
            System.out.println("=========================================");
            System.out.println("\tWelcome to Restaurant 0.0");
            System.out.println("=========================================");
            System.out.print("(1) Menu\n" + "(2) Order\n" + "(3) Reservation\n" + "(4) Check Table Availability\n"
                    + "(5) Print Sales Revenue Report\n" + "(6) Shut down the program\n");
            System.out.println("Choose an option:");
            op = sc.getInt();

            switch (op) {
            case 1:
                runMenu();
                break;
            case 2:
                runOrder();
                break;
            case 3:
                runReservation();
                break;
            case 4: {
                myRestaurant.updateTime();
                myRestaurant.printAllTables();
                break;
            }
            case 5:
                System.out.println("----------------------------------------");
                System.out.print("Enter the start date(format:dd/mm/yyyy): ");
                LocalDate from = sc.getDate();

                System.out.println(from);

                System.out.print("Enter the end date(format:dd/mm/yyyy): ");
                LocalDate to = sc.getDate().plusDays(1);

                System.out.println(to);

                while (to.isBefore(from)) {
                    System.out.println("Invalid query of period, please enter again");
                    System.out.print("Enter the start date(format:dd/mm/yyyy): ");
                    from = sc.getDate();
                    System.out.print("Enter the end date(format:dd/mm/yyyy): ");
                    to = sc.getDate();
                }
                printReport(from, to);
                break;
            case 6:
                System.out.println("Shut down the program? (Y/N)");
                String cm = sc.nextLine();
                // == tests object references, .equals() tests the string values
                if (cm.equals("Y") || cm.equals("y")) {
                    myRestaurant.saveAll();
                    System.out.println("save all ... ");
                    return;
                }
                break;
            default:
                break;
            }
        } while (op > 0 && op < 7);
    }

    /**
     * Reservation subroutine.
     */
    private void runReservation() {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.Make Reservation\n" + "\t2.Check Reservation\n"
                    + "\t3.Remove Reservation\n");
            System.out.print("Choose an option:");
            op = sc.getInt();
            switch (op) {
            case 1:
                System.out.print("Enter the date(format: dd/mm/yyyy): ");
                LocalDate date = sc.getDate();
                while (date.isBefore(LocalDate.now())) {
                    System.out.println("invalid date, please try again");
                    System.out.print("Enter the date(format: dd/mm/yyyy): ");
                    date = sc.getDate();
                }
                System.out.print("Enter the timing(format: 13:00): ");
                LocalTime time = sc.getTime();
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                displayTables(dateTime);
                System.out.print("Enter tableId: ");
                int tableId = sc.getInt();
                System.out.print("Enter pax: ");
                int pax = sc.getInt();
                System.out.print("Enter contact number: ");
                long contact = sc.getLong();
                myRestaurant.makeReservation(dateTime, tableId, pax, contact);
                break;
            case 2:
                System.out.println("Enter contact number: ");
                long c = sc.getLong();
                checkReservation(c);
                break;
            case 3:
                System.out.println("Enter contact number: ");
                long cc = sc.getLong();

                removeReservation(cc);

                break;
            default:
                break;
            }
        } while (op > 0 && op < 4);
    }

    /**
     * Restaurant menu subroutine.
     */
    private void runMenu() {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.View Menu\n" + "\t2.Edit Menu\n");
            System.out.print("Choose an option:");
            op = sc.getInt();
            switch (op) {
            case 1:
                displayMenu();
                break;
            case 2:
                editMenu();
                break;
            default:
                break;
            }
        } while (op > 0 && op < 3);
    }

    /**
     * Order subroutine.
     */
    private void runOrder() {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.Make Order\n" + "\t2.View Order\n" + "\t3.Edit Order\n"
                    + "\t4.Print Invoice\n");
            System.out.print("Choose an option:");
            op = sc.getInt();
            switch (op) {
            case 1:
                System.out.print("Enter your staffId: ");
                int staffId = sc.getInt();
                System.out.print("Enter the tableId: ");
                int tableId = sc.getInt();
                System.out.print("Enter pax: ");
                int pax = sc.getInt();
                myRestaurant.makeOrder(staffId, tableId, pax);
                System.out.print("Order is created!\n");
                break;
            case 2:
                displayTables();
                System.out.print("Enter tableId: ");
                int Id = sc.getInt();
                Order o = myRestaurant.getOrderByTableId(Id);
                if (o == null) {
                    System.out.print("No active order on this table.\n");
                    break;
                }
                o.print();
                break;
            case 3:
                displayTables();
                System.out.print("Enter tableId: ");
                int t = sc.getInt();
                Order oo = myRestaurant.getOrderByTableId(t);
                if (oo == null) {
                    System.out.print("No active order on this table.\n");
                    break;
                }
                oo.print();
                editOrder(oo);
                break;
            case 4:
                displayTables();
                System.out.print("Enter tableId: ");
                int tt = sc.getInt();
                Order ooo = myRestaurant.getOrderByTableId(tt);
                while (ooo == null) {
                    System.out.print("Invalid tableId, please try again\n");
                    System.out.print("Enter tableId: ");
                    tt = sc.getInt();
                    ooo = myRestaurant.getOrderByTableId(tt);
                }
                System.out.println("Are you a memeber of Restaurant 0.0? (Y/N): ");
                String ans = sc.nextLine();
                if (ans.equals("Y") || ans.equals("y"))
                    ooo.printInvoice(true);
                else
                    ooo.printInvoice(false);
                myRestaurant.activeOrders.remove(ooo);
                myRestaurant.inactiveOrders.add(ooo);
                break;
            default:
                break;
            }
        } while (op > 0 && op < 5);
    }

    /**
     * Check reservation according to the given contact.
     *
     * @param contact the contact associated to a specific reservation.
     */
    private void checkReservation(long contact) {
        ArrayList<Reservation> result = myRestaurant.getReservation(contact);
        if (result.size() == 0) {
            System.out.println("This reservation does not exist");
        } else {
            displayReservation(result);
        }
    }

    /**
     * Display an given reservation list.
     *
     * @param list the reservation list to display.
     */
    private void displayReservation(ArrayList<Reservation> list) {
        for (Reservation r : list) {
            System.out.printf("%s\t%d\t%d\t%d\n", r.getTime().toString(), r.getTableId(), r.getPax(), r.getContact());
        }
    }

    /**
     * Remove reservation according to the given contact.
     *
     * @param contact the contact associated to a specific reservation.
     */
    private void removeReservation(long contact) {
        ArrayList<Reservation> t = myRestaurant.getReservation(contact);

        if (t.size() != 0) {
            int index = 0;
            for (Reservation r : t) {
                System.out.printf("%d\t", index + 1);
                r.print();
                index++;
            }
            System.out.println("Choose an index: ");
            index = sc.getInt() - 1;
            while (index < 0 || index >= t.size()) {
                System.out.println("Invalid index.");
                System.out.println("Choose an index: ");
                index = sc.getInt() - 1;
            }
            myRestaurant.removeReservation(t.get(index));
        }
    }
}
