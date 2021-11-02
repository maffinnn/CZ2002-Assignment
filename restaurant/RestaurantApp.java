package restaurant;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class RestaurantApp {

    Scanner sc = new Scanner(System.in);
    DateTimeFormatter dt_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    Restaurant myRestaurant;

    public static void main(String args[]) {
        System.out.println("Initiating ... ");
        RestaurantApp myApp = new RestaurantApp();
        myApp.run();
    }

    private boolean editItem(MenuComponent category) {
        System.out.print("0.\tGo back\n" + "1.\tAdd item\n" + "2.\tUpdate item\n" + "3.\tRemove item\n"
                + "4.\tRemove this category\n" + "Choose option:");
        int op = Integer.parseInt(sc.nextLine());
        switch (op) {
        case 1: {
            // add item
            if (category.name.compareTo("Combo") != 0 && category.name.compareTo("Promotions") != 0) {
                System.out.print("Item name: ");
                String name = sc.nextLine();
                System.out.print("Item description: ");
                String description = sc.nextLine();
                System.out.print("Item price: ");
                double price = Double.parseDouble(sc.nextLine());
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
                int numOfItems = Integer.parseInt(sc.nextLine());

                Random rand = new Random();
                int ComboCode;
                do {
                    ComboCode = rand.nextInt(10000);
                } while (ComboCode < 0 || myRestaurant.menuReference.containsKey(ComboCode));
                MenuBundle Combo = new MenuBundle(ComboCode, name, "");
                displayMenu();
                for (int i = 0; i < numOfItems; i++) {

                    System.out.print("Combo item code: ");
                    int itemCode = Integer.parseInt(sc.nextLine());
                    while (!myRestaurant.menuReference.containsKey(itemCode)) {
                        System.out.println("Invalid item code, please try again");
                        System.out.print("Combo item code: ");
                        itemCode = Integer.parseInt(sc.nextLine());
                    }
                    Combo.addChild(myRestaurant.menuReference.get(itemCode));
                }

                System.out.print("Combo price: ");
                double price = Double.parseDouble(sc.nextLine());
                Combo.setPrice(price);
                myRestaurant.menuReference.put(ComboCode, Combo);
                category.addChild(Combo);
            }
        }
            break;
        case 2: { // update item
            if (category.name.compareTo("Combo") != 0 && category.name.compareTo("Promotions") != 0) {
                displayItems(category);
            } else {
                displayCombos(category);
            }
            System.out.println("Enter item code: ");
            int itemCode = Integer.parseInt(sc.nextLine());
            while (!myRestaurant.menuReference.containsKey(itemCode)) {
                System.out.println("Invalid item code, please try again");
                System.out.print("Enter item code: ");
                itemCode = Integer.parseInt(sc.nextLine());
            }

            MenuComponent curItem = myRestaurant.menuReference.get(itemCode);

            if (curItem instanceof MenuLeaf) {

                System.out.print("0.\tGo back\n" + "1.\tupdate name\n" + "2.\tupdate description\n"
                        + "3.\tupdate price\n" + "Choose option: ");
                int curOp = Integer.parseInt(sc.nextLine());
                switch (curOp) {
                case 1: {
                    System.out.print("Enter new name: ");
                    curItem.name = sc.nextLine();
                    break;
                }
                case 2: {
                    System.out.print("Enter new description: ");
                    curItem.description = sc.nextLine();
                    break;
                }
                case 3: {
                    System.out.print("Enter new price: ");
                    double newPrice = Double.parseDouble(sc.nextLine());
                    while (newPrice <= 0) {
                        System.out.println("Invalid price, please enter again.");
                        System.out.print("Enter new price: ");
                        newPrice = Double.parseDouble(sc.nextLine());
                    }
                    curItem.setPrice(newPrice);
                    break;
                }
                default:
                    break;
                }

            } else {
                System.out.print("0.\tGo back\n" + "1.\tupdate name\n" + "2.\tupdate description\n"
                        + "3.\tupdate price\n" + "4.\tadd item\n" + "5.\tremove item\n" + "Choose option: ");
                int curOp = Integer.parseInt(sc.nextLine());
                while (curOp >= 1 && curOp <= 5) {
                    switch (curOp) {
                    case 1: {
                        System.out.print("Enter new name: ");
                        curItem.name = sc.nextLine();
                        break;
                    }
                    case 2: {
                        System.out.print("Enter new description: ");
                        curItem.description = sc.nextLine();
                        break;
                    }
                    case 3: {
                        System.out.print("Enter new price: ");
                        double newPrice = Double.parseDouble(sc.nextLine());
                        while (newPrice <= 0) {
                            System.out.println("Invalid price, please enter again.");
                            System.out.print("Enter new price: ");
                            newPrice = Double.parseDouble(sc.nextLine());
                        }
                        curItem.setPrice(newPrice);
                        break;
                    }
                    case 4: {
                        displayMenu();
                        System.out.print("Enter item code: ");
                        itemCode = Integer.parseInt(sc.nextLine());
                        while (!myRestaurant.menuReference.containsKey(itemCode)) {
                            System.out.println("Invalid item code, please try again");
                            System.out.print("Enter item code: ");
                            itemCode = Integer.parseInt(sc.nextLine());
                        }
                        curItem.addChild(myRestaurant.menuReference.get(itemCode));
                        break;
                    }
                    case 5: {
                        MenuBundle combo = (MenuBundle) curItem;
                        combo.print(false);
                        System.out.println("Enter item code: ");
                        itemCode = Integer.parseInt(sc.nextLine());
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
                    curOp = Integer.parseInt(sc.nextLine());
                }

            }
            break;

        }
        case 3: {
            if (category.name.compareTo("Combo") != 0 && category.name.compareTo("Promotions") != 0) {
                displayItems(category);
            } else {
                displayCombos(category);
            }
            System.out.println("Enter item code: ");
            int itemCode = Integer.parseInt(sc.nextLine());
            while (!myRestaurant.menuReference.containsKey(itemCode)) {
                System.out.println("Invalid item code, please try again");
                System.out.print("Enter item code: ");
                itemCode = Integer.parseInt(sc.nextLine());
            }
            int itemIndex = ((Menu) category).contains(itemCode);
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

    private void editMenu() {
        boolean flag = false;
        while (!flag) {
            System.out.print("\t0.Go back\n");
            displayCategory();
            System.out.printf("\t%d.Create a New Category\n", myRestaurant.menu.getChildrenCount() + 1);
            System.out.print("Choose Category:");
            int op = Integer.parseInt(sc.nextLine());
            // String dummy = sc.nextLine();
            MenuComponent category;
            if (op == 0)
                return;
            else if (op > myRestaurant.menu.getChildrenCount()) {
                System.out.print("Category Name:");
                String name = sc.nextLine();
                category = new Menu(-1, name, "");
                myRestaurant.menu.addChild(category);
            } else {
                category = myRestaurant.menu.getChild(op - 1);
            }
            flag = editItem(category);
        }

    }

    private void printReport(LocalDate from, LocalDate to) {
        SalesReport report = myRestaurant.generateSalesReport(from, to);
        report.print();
    }

    private void displayCategory() {
        Menu menu = myRestaurant.menu;
        // System.out.print("\t" + menu.name + "category");
        for (int i = 0; i < menu.getChildrenCount(); i++) {
            MenuComponent category = menu.getChild(i);
            System.out.printf("\t%d.%s\n", i + 1, category.name);
        }
    }

    private void displayItems(MenuComponent category) {
        for (int j = 0; j < category.getChildrenCount(); j++) {
            MenuComponent item = category.getChild(j);
            System.out.printf("%04d\t%s\t%.2f\n", item.code, item.name, item.getPrice());
            System.out.printf("\t%s\n", item.description);
        }
    }

    private void displayCombos(MenuComponent combo) {
        for (int j = 0; j < combo.getChildrenCount(); j++) {
            MenuComponent mb = combo.getChild(j);
            System.out.printf("%04d\t%s\t%.2f\n", mb.code, mb.name, mb.getPrice());
            for (int k = 0; k < mb.getChildrenCount(); k++) {
                MenuComponent item = mb.getChild(k);
                System.out.printf("\t\t%s\n", item.name);
            }
        }
    }

    private void displayMenu() {
        Menu menu = myRestaurant.menu;
        System.out.println(menu.name);
        for (int i = 0; i < menu.getChildrenCount(); i++) {
            MenuComponent category = menu.getChild(i);
            System.out.println(category.name);
            if (category.name.compareTo("Combo") != 0 && category.name.compareTo("Promotions") != 0) {
                displayItems(category);
            } else {
                displayCombos(category);
            }
        }
    }

    private void displayReservations() {
        myRestaurant.tableManager.printAllReservations();
    }

    private void displayTables() {
        myRestaurant.tableManager.printAllTables();
    }

    private void displayOrders() {
        System.out.println("activeOrders");
        int numOfOrders = myRestaurant.activeOrders.size();
        for (int i = 0; i < numOfOrders; i++) {
            Order o = myRestaurant.activeOrders.get(i);
            o.print();
        }
    }

    private void editOrder(Order o) {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.Add item\n" + "\t2.Remove item\n");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
            case 1:
                displayMenu();
                System.out.print("Enter item code: ");
                int itemCode = Integer.parseInt(sc.nextLine());
                while (!myRestaurant.menuReference.containsKey(itemCode)) {
                    System.out.println("Invalid item code, please enter again");
                    System.out.print("Enter item code: ");
                    itemCode = Integer.parseInt(sc.nextLine());
                }
                System.out.print("Enter quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());
                o.addItem(myRestaurant.menuReference.get(itemCode), quantity);
                System.out.printf("%d serving(s) of %s has been added\n", quantity,
                        myRestaurant.menuReference.get(itemCode).name);
                break;
            case 2:
                o.print();
                if (o.orderedItems.getChildrenCount() == 0) {
                    System.out.println("No item in this order.");
                    break;
                }
                System.out.print("Enter item code: ");
                int code = Integer.parseInt(sc.nextLine());
                while (!myRestaurant.menuReference.containsKey(code) || o.getItemIndex(code) == -1) {
                    System.out.println("Invalid item code, please enter again");
                    System.out.print("Enter item code: ");
                    code = Integer.parseInt(sc.nextLine());
                }
                int itemIndex = o.getItemIndex(code);
                MenuComponent itemInOrder = o.orderedItems.getChild(itemIndex);
                System.out.print("Enter item quantity: ");
                quantity = Integer.parseInt(sc.nextLine());
                while (quantity > itemInOrder.getQuantity() || quantity < 0) {
                    System.out.println("Invalid quantity, please enter again");
                    System.out.print("Enter item quantity: ");
                    quantity = Integer.parseInt(sc.nextLine());
                }
                if (quantity == itemInOrder.getQuantity()) {
                    o.orderedItems.removeChild(itemIndex);
                    break;
                }
                itemInOrder.setQuantity(itemInOrder.getQuantity() - quantity);
                break;
            default:
                break;
            }

        } while (op > 0 && op < 3);

    }

    private void run() {
        myRestaurant = new Restaurant();
        int op;
        do {
            System.out.println("========================================");
            System.out.println("\tWelcome to Restaurant 0.0");
            System.out.println("========================================");
            System.out.print("(1) Menu\n" + "(2) Order\n" + "(3) Reservation\n" + "(4) Check Table Availability\n"
                    + "(5) Print Sales Revenue Report\n" + "(6) Shut down the program\n");
            System.out.println("Choose an option:");
            op = Integer.parseInt(sc.nextLine());

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
                myRestaurant.tableManager.printAllTables();
                break;
            }
            case 5:
                System.out.println("----------------------------------------");
                System.out.print("Enter the start date(format:dd/mm/yyyy): ");
                String l = sc.nextLine();
                LocalDate from = LocalDate.parse(l, dt_formatter).minusDays(1);

                System.out.println(from);

                System.out.print("Enter the end date(format:dd/mm/yyyy): ");
                l = sc.nextLine();
                LocalDate to = LocalDate.parse(l, dt_formatter).plusDays(1);

                System.out.println(to);

                while (to.isBefore(from)) {
                    System.out.println("Invalid query of period, please enter again");
                    System.out.print("Enter the start date(format:dd/mm/yyyy): ");
                    l = sc.nextLine();
                    from = LocalDate.parse(l, dt_formatter);
                    System.out.print("Enter the end date(format:dd/mm/yyyy): ");
                    l = sc.nextLine();
                    to = LocalDate.parse(l, dt_formatter);
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

    private void makeOrder(int staffId, int tableId, int pax) {

        Staff s = myRestaurant.staff.get(staffId);
        ArrayList<Reservation> reservationList = myRestaurant.tableManager.getReservation(tableId);
        Reservation r;
        if (reservationList.size() == 0) {
            r = new Reservation(LocalDateTime.now(), tableId, pax, 0);
        } else
            r = reservationList.get(0);

        Order newOrder = new Order(r, s);
        myRestaurant.activeOrders.add(newOrder);
    }

    /**
     * @param time The customer(s) requested time
     */
    // staff will check all the available table before makeReservation() is called
    private void makeReservation(LocalDateTime time, int tableId, int pax, long contact) {

        Reservation r = new Reservation(time, tableId, pax, contact);
        myRestaurant.tableManager.addReservation(r);

    }

    private void runReservation() {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.Make Reservation\n" + "\t2.Check Reservation\n"
                    + "\t3.Remove Reservation\n");
            System.out.print("Choose an option:");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
            case 1:
                System.out.print("Enter the date(format: dd/mm/yyyy): ");
                LocalDate date = LocalDate.parse(sc.nextLine(), dt_formatter);
                while (date.isBefore(LocalDate.now())) {
                    System.out.println("invalid date, please try again");
                    System.out.print("Enter the date(format: dd/mm/yyyy): ");
                    date = LocalDate.parse(sc.nextLine(), dt_formatter);
                }
                System.out.print("Enter the timing(format: 13:00): ");
                LocalTime time = LocalTime.parse(sc.nextLine());
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                displayTables();
                System.out.print("Enter tableId: ");
                int tableId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter pax: ");
                int pax = Integer.parseInt(sc.nextLine());
                System.out.print("Enter contact number: ");
                long contact = Long.parseLong(sc.nextLine());
                makeReservation(dateTime, tableId, pax, contact);
                break;
            case 2:
                System.out.println("Enter contact number: ");
                long c = Long.parseLong(sc.nextLine());
                ArrayList<Reservation> temp = myRestaurant.tableManager.getReservation(c);
                if (temp.size() == 0) {
                    System.out.println("This reservation does not exist");
                } else {
                    for (Reservation r : temp) {
                        System.out.printf("%s\t%d\t%d\t%d\n", r.time.toString(), r.tableId, r.pax, r.contact);
                    }
                }
                break;
            case 3:
                System.out.println("Enter contact number: ");
                long cc = Long.parseLong(sc.nextLine());
                ArrayList<Reservation> t = myRestaurant.tableManager.getReservation(cc);

                if (t.size() != 0) {
                    int index = 0;
                    for (Reservation r : t) {
                        System.out.printf("%d\t", index + 1);
                        r.print();
                        index++;
                    }
                    System.out.println("Choose an index: ");
                    index = Integer.parseInt(sc.nextLine()) - 1;
                    while (index < 0 || index >= t.size()) {
                        System.out.println("Invalid index.");
                        System.out.println("Choose an index: ");
                        index = Integer.parseInt(sc.nextLine()) - 1;
                    }
                    myRestaurant.tableManager.removeReservation(t.get(index));
                }

                break;
            default:
                break;
            }
        } while (op > 0 && op < 4);
    }

    private void runMenu() {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.View Menu\n" + "\t2.Edit Menu\n");
            System.out.print("Choose an option:");
            op = Integer.parseInt(sc.nextLine());
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

    private void runOrder() {
        int op;
        do {
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n" + "\t1.Make Order\n" + "\t2.View Order\n" + "\t3.Edit Order\n"
                    + "\t4.Print Invoice\n");
            System.out.print("Choose an option:");
            op = Integer.parseInt(sc.nextLine());
            switch (op) {
            case 1:
                System.out.print("Enter your staffId: ");
                int staffId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter the tableId: ");
                int tableId = Integer.parseInt(sc.nextLine());
                System.out.print("Enter pax: ");
                int pax = Integer.parseInt(sc.nextLine());
                makeOrder(staffId, tableId, pax);
                System.out.print("Order is created!\n");
                break;
            case 2:
                displayTables();
                System.out.print("Enter tableId: ");
                int Id = Integer.parseInt(sc.nextLine());
                Order o = myRestaurant.getOrderbytableId(Id);
                if (o == null) {
                    System.out.print("No active order on this table.\n");
                    break;
                }
                o.print();
                break;
            case 3:
                displayTables();
                System.out.print("Enter tableId: ");
                int t = Integer.parseInt(sc.nextLine());
                Order oo = myRestaurant.getOrderbytableId(t);
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
                int tt = Integer.parseInt(sc.nextLine());
                Order ooo = myRestaurant.getOrderbytableId(tt);
                while (ooo == null) {
                    System.out.print("Invalid tableId, please try again\n");
                    System.out.print("Enter tableId: ");
                    tt = Integer.parseInt(sc.nextLine());
                    ooo = myRestaurant.getOrderbytableId(tt);
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
}
