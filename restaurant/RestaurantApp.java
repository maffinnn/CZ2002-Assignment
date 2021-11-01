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

    private boolean editItem(MenuComponent category){
        System.out.print(
                "0.\tGo back\n" +
                        "1.\tAdd item\n" +
                        "2.\tUpdate item\n" +
                        "3.\tRemove item\n" +
                        "4.\tRemove this category\n" +
                        "Choose option:" );
        int op = sc.nextInt();
        switch(op){
            case 1:{ 
                //add item
                if(category.name.compareTo("Combo") != 0 && category.name.compareTo("Promotions") != 0) {
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
                    }while(itemCode < 0 || myRestaurant.menuReference.containsKey(itemCode));
                    
                    MenuLeaf item = new MenuLeaf(itemCode, name, description, price);
                    myRestaurant.menuReference.put(itemCode, item);
                    category.addChild(item);
                }
                else { 
                    // add bundle
                    System.out.print("Combo name: ");
                    String name = sc.nextLine();
                    System.out.print("Number of items this combo contains: ");
                    int numOfItems = Integer.parseInt(sc.nextLine());
                    
                    Random rand = new Random();
                    int ComboCode;
                    do {
                        ComboCode = rand.nextInt(10000);
                    }while(ComboCode < 0 || myRestaurant.menuReference.containsKey(ComboCode));
                    MenuBundle Combo = new MenuBundle(ComboCode, name, "");
                    
                    for (int i =0; i< numOfItems; i++) {
                        System.out.print("Combo item code: ");
                        int itemCode = Integer.parseInt(sc.nextLine());
                        while(!myRestaurant.menuReference.containsKey(itemCode)){
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
            } break;
            case 2:{ //update item
                displayItems(category);

            }
            default: return false;
        }
        return true;
    }

    private void editMenu(){
        boolean flag = false;
        while(!flag) {
            System.out.print("\t0.Go back\n");
            displayCategory();
            System.out.printf("\t%d.Create a New Category\n", myRestaurant.menu.getChildrenCount() + 1);
            System.out.print("Choose Category:");
            int op = sc.nextInt();
            MenuComponent category;
            if (op == 0) return;
            else if (op > myRestaurant.menu.getChildrenCount()) {
                System.out.print("Category Name:");
                String name = sc.nextLine();
                category = new Menu(-1,name, "");
                myRestaurant.menu.addChild(category);
            } else {
                category = myRestaurant.menu.getChild(op - 1);
            }
            flag = editItem(category);
        }

    }

    private void printReport(LocalDate from, LocalDate to){
        SalesReport report = myRestaurant.generateSalesReport(from, to);
        report.print();
    }

    private void displayCategory(){
        Menu menu = myRestaurant.menu;
        System.out.print("\t"+menu.name+"category");
        for(int i = 0; i < menu.getChildrenCount(); i++) {
            MenuComponent category = menu.getChild(i);
            System.out.printf("\t%d.%s\n", i + 1, category.name);
        }
    }

    private void displayItems(MenuComponent category){
        for(int j = 0; j < category.getChildrenCount(); j++){
            MenuComponent item = category.getChild(j);
            System.out.printf("\t%d.%s\t%.2f\n", item.code, item.name, item.getPrice());
            System.out.printf("\t%s\n", item.description);
        }
    }

    private void displayCombos(MenuComponent combo){
        for(int j = 0; j < combo.getChildrenCount(); j++){
            MenuComponent mb = combo.getChild(j);
            System.out.printf("%d\t%s\t%.2f\n", mb.code, mb.name, mb.getPrice());
            for(int k = 0; k < mb.getChildrenCount(); k++){
                MenuComponent item = mb.getChild(k);
                System.out.printf("\t\t%s\n", item.name);
            }
        }
    }

    private void displayMenu(){
        Menu menu = myRestaurant.menu;
        System.out.println(menu.name);
        for(int i = 0; i < menu.getChildrenCount(); i++){
            MenuComponent category = menu.getChild(i);
            System.out.println(category.name);
            if(category.name.compareTo("Combo") != 0 && category.name.compareTo("Promotions") != 0){
                displayItems(category);
            } else {
                displayCombos(category);
            }
        }
    }

    private  void displayReservations(){
        myRestaurant.tableManager.printAllReservations();
    }

    private void displayTables(){
        myRestaurant.tableManager.printAllTables();
    }

    private void displayOrders(){
        System.out.println("activeOrders");
        int numOfOrders = myRestaurant.activeOrders.size();
        for (int i  =0; i<numOfOrders; i++){
            Order o = myRestaurant.activeOrders.get(i);
            o.print();
        }
    }

    private void editOrder(Order o){
        int op;
        do{
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n"+
            "\t1.Add item\n"+
            "\t2.Remove item\n");
            op = sc.nextInt();
            switch(op){
            case 1: 
                System.out.print("Enter item code: ");
                int itemCode =sc.nextInt();
                while(!myRestaurant.menuReference.containsKey(itemCode)){
                    System.out.println("Invalid item code, please enter again");
                    System.out.print("Enter item code: ");
                    itemCode = sc.nextInt();
                }
                System.out.print("Enter quantity: ");
                int quantity = sc.nextInt();
                o.addItem(myRestaurant.menuReference.get(itemCode), quantity);
                System.out.printf("%d serving(s) of %s has been added\n", quantity, myRestaurant.menuReference.get(itemCode).name);
                break;
            case 2:
                System.out.print("Enter item code: ");
                int code =sc.nextInt();
                while(!myRestaurant.menuReference.containsKey(code)){
                    System.out.println("Invalid item code, please enter again");
                    System.out.print("Enter item code: ");
                    itemCode = sc.nextInt();

                }
                int itemIndex = o.getItemIndex(code);
                while(itemIndex==-1){
                    System.out.println("Invalid item code, please enter again");
                    System.out.print("Enter item code: ");
                    itemCode = sc.nextInt();
                }
                o.orderedItems.removeChild(itemIndex);
                break;
                default: break;
            }
        
        }while(op>0&&op<3);   
        
    }

    private void run() {
        myRestaurant = new Restaurant();
        int op;
        do {
            System.out.println("========================================");
            System.out.println("\tWelcome to Restaurant 0.0");
            System.out.println("========================================");
            System.out.print("(1) Menu\n"+
                            "(2) Order\n"+
                            "(3) Reservation\n"+
                            "(4) Check Table Availability\n"+
                            "(5) Print Sales Revenue Report\n"+
                            "(6) Shut down the program\n");
            System.out.println("Choose an option:");
            op = sc.nextInt();
            
            switch(op){
            case 1: runMenu(); break;
            case 2: runOrder(); break;
            case 3: runReservation(); break;
            case 4: myRestaurant.tableManager.printAllTables(); break;
            case 5:
                System.out.println("----------------------------------------");
                System.out.print("Enter the start date(format:dd/mm/yyyy): ");
                String l = sc.next();
                LocalDate from = LocalDate.parse(l, dt_formatter);
                System.out.print("Enter the end date(format:dd/mm/yyyy): ");
                l = sc.next();
                LocalDate to = LocalDate.parse(l, dt_formatter);
                while(to.isBefore(from)){
                    System.out.println("Invalid query of period, please enter again");
                    System.out.print("Enter the start date(format:dd/mm/yyyy): ");
                    l = sc.next();
                    from = LocalDate.parse(l, dt_formatter);
                    System.out.print("Enter the end date(format:dd/mm/yyyy): ");
                    l = sc.next();
                    to = LocalDate.parse(l, dt_formatter);
                }
                printReport(from, to); 
                break;
            case 6: 
                System.out.println("Shut down the program? (Y/N)");
                String cm = sc.next();
                // == tests object references, .equals() tests the string values
                if ( cm.equals("Y") || cm.equals("y")) {
                    myRestaurant.saveAll();
                    System.out.println("save all ... ");
                    return;
                }
                break;
            default: break;    
            }
        }while(op>0&&op<7);
    }
    
    private void makeOrder(int staffId, int tableId, int pax){

        Staff s = myRestaurant.staff.get(staffId);
        ArrayList<Reservation> reservationList = myRestaurant.tableManager.getReservation(tableId);
        Reservation r;
        if (reservationList.size() == 0){
            r = new Reservation(LocalDateTime.now(), tableId, pax, 0);
        }else r = reservationList.get(0);

        Order newOrder = new Order(r, s);
        myRestaurant.activeOrders.add(newOrder);
    }

    /**
     * @param time The customer(s) requested time
     */
    // staff will check all the available table before makeReservation() is called
    private void makeReservation(LocalDateTime time, int tableId, int pax, long contact){
        
        Reservation r = new Reservation(time, tableId, pax, contact);
        myRestaurant.tableManager.addReservation(r);

    }

    private void runReservation(){
        int op;
        do{
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n"+
                             "\t1.Make Reservation\n"+
                             "\t2.Check Reservation\n"+
                             "\t3.Remove Reservation\n");
            System.out.print("Choose an option:");
            op = sc.nextInt();
            switch(op){
            case 1: 
                System.out.print("Enter the date(format: dd/mm/yyyy): ");
                LocalDate date = LocalDate.parse(sc.next(), dt_formatter);
                while(date.isBefore(LocalDate.now())){
                    System.out.println("invalid date, please try again");
                    System.out.print("Enter the date(format: dd/mm/yyyy): ");
                    date = LocalDate.parse(sc.next(), dt_formatter);
                }
                System.out.print("Enter the timing(format: 13:00): ");
                LocalTime time =  LocalTime.parse(sc.next());
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                
                System.out.print("Enter tableId: ");
                int tableId = sc.nextInt();
                System.out.print("Enter pax: ");
                int pax = sc.nextInt();
                System.out.print("Enter contact number: ");
                long contact = sc.nextLong();
                makeReservation(dateTime, tableId, pax, contact);
                break;
            case 2: 
                System.out.println("Enter contact number: ");
                long c = sc.nextLong();
                ArrayList<Reservation> temp = myRestaurant.tableManager.getReservation(c);
                if (temp.size() == 0){
                    System.out.println("This reservation does not exist");
                }
                else {
                    for(Reservation r: temp){
                        System.out.printf("%s\t%d\t%d\t%d\n", r.time.toString(), r.tableId, r.pax, r.contact);
                    }
                }
                break;
            case 3:
                System.out.println("Enter contact number: ");
                long cc = sc.nextLong();
                ArrayList<Reservation> t = myRestaurant.tableManager.getReservation(cc);
                if (t.size() != 0){
                    for (Reservation r: t)
                        myRestaurant.tableManager.removeReservation(r);
                }
                
            break;
            default: break;
            }
        }while(op>0&&op<4);
    }

    private void runMenu(){
        int op;
        do{
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n"+
                             "\t1.View Menu\n"+
                             "\t2.Edit Menu\n");
            System.out.print("Choose an option:");
            op = sc.nextInt();
            switch(op){
            case 1: 
                displayMenu();
                break;
            case 2: 
                editMenu();
                break;
            default: break;
            }
        }while(op>0&&op<3);
    }

    private void runOrder(){
        int op;
        do{
            System.out.println("----------------------------------------");
            System.out.print("\t0.Go back\n"+
                             "\t1.Make Order\n"+
                             "\t2.View Order\n"+
                             "\t3.Edit Order\n"+
                             "\t4.Print Invoice\n");
            System.out.print("Choose an option:");
            op = sc.nextInt();
            switch(op){
            case 1: 
                System.out.print("Enter your staffId: ");
                int staffId = sc.nextInt();
                System.out.print("Enter the tableId: ");
                int tableId = sc.nextInt();
                System.out.print("Enter pax: ");
                int pax = sc.nextInt();
                makeOrder(staffId, tableId, pax);
                System.out.print("Order is created!\n");
                break;
            case 2: 
                System.out.print("Enter tableId: ");
                int Id = sc.nextInt();
                Order o = myRestaurant.getOrderbytableId(Id);
                while(o == null){
                    System.out.print("Invalid tableId, please try again\n");
                    System.out.print("Enter tableId: ");
                    Id = sc.nextInt();
                    o = myRestaurant.getOrderbytableId(Id);
                }
                o.print();
                break;
            case 3:
                System.out.print("Enter tableId: ");
                int t = sc.nextInt();
                Order oo = myRestaurant.getOrderbytableId(t);
                while(oo == null){
                    System.out.print("Invalid tableId, please try again\n");
                    System.out.print("Enter tableId: ");
                    t = sc.nextInt();
                    oo = myRestaurant.getOrderbytableId(t);
                }
                editOrder(oo);
                break;
            case 4:
                System.out.print("Enter tableId: ");
                int tt = sc.nextInt();
                Order ooo = myRestaurant.getOrderbytableId(tt);
                while(ooo == null){
                    System.out.print("Invalid tableId, please try again\n");
                    System.out.print("Enter tableId: ");
                    tt = sc.nextInt();
                    ooo = myRestaurant.getOrderbytableId(tt);
                }
                System.out.println("Are you a memeber of Restaurant 0.0? (Y/N): ");
                String ans = sc.next();
                if (ans.equals("Y")||ans.equals("y")) ooo.printInvoice(true);
                else ooo.printInvoice(false);
                myRestaurant.activeOrders.remove(ooo);
                myRestaurant.inactiveOrders.add(ooo);
                break;
            default: break;
            }

        }while(op>0&&op<5);
        
    }
}
