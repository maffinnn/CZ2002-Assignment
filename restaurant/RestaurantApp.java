package restaurant;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantApp {

    Scanner sc = new Scanner(System.in);

    Restaurant myRestaurant;

    private void makeOrder(int staffId, int tableId, int pax){
        Staff s = myRestaurant.staff.get(staffId);
        ArrayList<Reservation> reservationList = myRestaurant.tableManager.getReservation(tableId);
        Reservation r;
        if (reservationList == null){
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

    private boolean editItem(MenuComponent category){
        System.out.print(
                "0.\tgo back\n" +
                        "1.\tadd item\n" +
                        "2.\tupdate item\n" +
                        "3.\tremove item\n" +
                        "4.\tremove this category\n" +
                        "Choose option:" );
        int op = sc.nextInt();
        switch(op){
            case 1:{ //add item
                if(category.name.compareTo("Combo") != 0 && category.name.compareTo("Promotions") != 0) {
                    System.out.print("Item name: ");
                    String name = sc.nextLine();
                    System.out.print("Item description: ");
                    String description = sc.nextLine();
                    System.out.print("Item price: ");
                    double price = Double.parseDouble(sc.nextLine());
                    MenuLeaf item = new MenuLeaf(name, description, price);
                    category.addChild(item);
                }
                else { //TODO add bundle

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
            System.out.print("0.\t Go back\n");
            displayCategory();
            System.out.printf("%d.\tCreate a New Category\n", myRestaurant.menu.getChildrenCount() + 1);
            System.out.print("Choose Category:");
            int op = sc.nextInt();
            MenuComponent category;
            if (op == 0) return;
            else if (op > myRestaurant.menu.getChildrenCount()) {
                System.out.print("Category Name:");
                String name = sc.nextLine();
                category = new Menu(name, "");
                myRestaurant.menu.addChild(category);
            } else {
                category = myRestaurant.menu.getChild(op - 1);
            }
            flag = editItem(category);
        }

    }

    private void printReport(){
        
    }

    private void displayCategory(){
        Menu menu = myRestaurant.menu;
        System.out.println(menu.name);
        for(int i = 0; i < menu.getChildrenCount(); i++) {
            MenuComponent category = menu.getChild(i);
            System.out.printf("%d.\t%s\n", i + 1, category.name);
        }
    }

    private void displayItems(MenuComponent category){
        for(int j = 0; j < category.getChildrenCount(); j++){
            MenuComponent item = category.getChild(j);
            System.out.printf("%d\t%s\t%.2f\n", j + 1, item.name, item.getPrice());
            System.out.printf("\t%s\n", item.description);
        }
    }

    private void displayCombos(MenuComponent combo){
        for(int j = 0; j < combo.getChildrenCount(); j++){
            MenuComponent mb = combo.getChild(j);
            System.out.printf("%d\t%s\t%.2f\n", j + 1, mb.name, mb.getPrice());
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



    private void run() {
        myRestaurant = new Restaurant();
        displayMenu();
        displayReservations();
        displayTables();
    }

    public static void main(String args[]) {
        RestaurantApp myApp = new RestaurantApp();
        myApp.run();
    }
}
