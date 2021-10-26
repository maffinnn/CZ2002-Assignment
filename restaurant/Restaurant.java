package restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.io.*;
import java.util.Scanner;


/**
 * Restaurant class bundles different systems in the restaurant. The class
 * itself doesn't do a lot.
 *
 * @author owl
 * @author TODO
 * @version 0.0
 */
public class Restaurant {
    public Menu menu;

    public TableManager tableManager;

    public ArrayList<Order> activeOrders;

    public ArrayList<Order> inactiveOrders;

    public HashMap<Integer, Staff> staff;

    public TreeSet<Order> historyOrders;


    public Restaurant() {
        menu = new Menu("Menu", "");
        loadMenu();
        tableManager = new TableManager();
        staff = new HashMap<>();
        loadStaff();
        activeOrders = new ArrayList<>();
        loadOrder();
        inactiveOrders = new ArrayList<>();
        
    }

    /**
     * Returns a list of all active orders.
     *
     * @return A list of all active orders.
     */
    public ArrayList<Order> getActiveOrders() {
        // TODO
        return activeOrders;
    }

    /**
     * Generates a sales report of the given time period.
     *
     * @return A sales report of the given time period.
     */
    public SalesReport generateSalesReport(LocalDate from, LocalDate to) {
        SalesReport report = new SalesReport(from, to);
        ArrayList<Order> historyOrders = loadhistoryOrders(from, to);
        for (int i = 0; i<historyOrders.size(); i++){
            report.processOrder(historyOrders.get(i));
        }
        return report;
    }

    // TODO File load / save functions.

    private void loadMenu() {

        try{
            String fileName = "restaurant\\alacarteMenu.txt";

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numOfCategory = Integer.parseInt(br.readLine());
            for(int i = 0; i < numOfCategory; i++){
                String categoryName = br.readLine();
                menu.addChild(new Menu(categoryName, ""));
                int numOfItems = Integer.parseInt(br.readLine());
                for(int j = 0; j < numOfItems; j++){
                    String name = br.readLine();
                    String description = br.readLine();
                    double price = Double.parseDouble(br.readLine());
                    MenuLeaf item = new MenuLeaf(name, description, price);
                    menu.getChild(i).addChild(item);
                }
            }

            fileName = "restaurant\\comboMenu.txt";

            br = new BufferedReader(new FileReader(fileName));
            String categoryName = br.readLine();
            MenuComponent combo = new Menu(categoryName, "");
            menu.addChild(combo);


            int numOfCombos = Integer.parseInt(br.readLine());
            for(int i = 0; i < numOfCombos; i++){
                String comboName = br.readLine();
                MenuBundle thisCombo = new MenuBundle(comboName, "");
                combo.addChild(thisCombo);
                int numOfItems = Integer.parseInt(br.readLine());
                for(int j = 0; j < numOfItems; j++){
                    String name = br.readLine();
                    String description = br.readLine();
                    MenuLeaf item = new MenuLeaf(name, description, 0);
                    thisCombo.addChild(item);
                }
                double price = Double.parseDouble(br.readLine());
                thisCombo.setPrice(price);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMenu() {
        try {
            String fileName = "restaurant\\alacarteMenu.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(menu.getChildrenCount() - 1 + "\n");

            for (int i = 0; i < menu.getChildrenCount(); i++) {
                MenuComponent category = menu.getChild(i);
                if (category.name.compareTo("Combo") != 0) {
                    bw.write(category.name + "\n");
                    bw.write(category.getChildrenCount() +"\n");
                    for(int j = 0; j < category.getChildrenCount(); j++){
                        MenuComponent item = category.getChild(j);
                        bw.write(item.name + "\n" + item.description + "\n" + item.getPrice() +"\n");
                    }
                } else {
                    saveCombos(category);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCombos(MenuComponent category) {
        try {
            String fileName = "restaurant\\alacarteMenu.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(category.name + "\n");
            bw.write(category.getChildrenCount());
            for(int i = 0; i < category.getChildrenCount(); i++){
                MenuComponent curCombo = category.getChild(i);
                bw.write(curCombo.name +"\n");
                bw.write(curCombo.getChildrenCount() + "\n");
                for(int j = 0; j < curCombo.getChildrenCount(); j++){
                    bw.write(curCombo.getChild(j) +"\n");
                }
                bw.write(curCombo.getPrice() +"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void loadOrder(){
        try{
            String fileName = "restaurant\\activeOrders.txt";

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numOfOrders = Integer.parseInt(br.readLine());
            for(int i = 0; i < numOfOrders; i++){
                {
                    String l = br.readLine();
                    String[] list = l.split(",");
                    LocalDateTime time = LocalDateTime.parse(list[0]);
                    int tableId = Integer.parseInt(list[1]);
                    int pax = Integer.parseInt(list[2]);
                    long contact = Long.parseLong(list[3]);
                    Reservation r = new Reservation(time, tableId, pax, contact);

                    int staffId = Integer.parseInt(br.readLine());
                    Staff s = staff.get(staffId);
                    Order curOrder = new Order(r, s);
                    int n = Integer.parseInt(br.readLine());
                    for(int j = 0; j < n; j++){
                        int flag = Integer.parseInt(br.readLine());
                        if(flag == 1){
                            l = br.readLine();
                            list = l.split(",");
                            int quantity = Integer.parseInt(list[0]);
                            String name = list[1];
                            double price = Double.parseDouble(list[2]);
                            MenuBundle mb = new MenuBundle(name,"");
                            mb.setPrice(price);
                            int numOfItems = Integer.parseInt(br.readLine());
                            for(int k = 0; k < numOfItems; k++){
                                String itemName = br.readLine();
                                MenuLeaf item = new MenuLeaf(itemName, "");
                                mb.addChild(item);
                            }
                            curOrder.order.addChild(mb);
                        } else {
                            l = br.readLine();
                            list = l.split(",");
                            int quantity = Integer.parseInt(list[0]);
                            String name = list[1];
                            double price = Double.parseDouble(list[2]);
                            MenuLeaf ml = new MenuLeaf(name,"",price);
                            curOrder.order.addChild(ml);
                        }
                    }
                    activeOrders.add(curOrder);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveOrder(){
        try {
            String filename = "restaurant\\activeOrders.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(activeOrders.size()+"\n");
            for(Order curOrder: activeOrders){
                Reservation r = curOrder.reservation;
                bw.write(r.time.toString() + "," + r.tableId + "," + r.pax + ","+ r.contact + "\n");
                bw.write(curOrder.getStaffId() + "\n");
                bw.write(curOrder.order.getChildrenCount() + "\n");
                for(int i = 0; i < curOrder.order.getChildrenCount(); i++){
                    MenuComponent mc = curOrder.order.getChild(i);
                    if(mc instanceof MenuBundle a){
                        bw.write(1 + "\n");
                        bw.write(mc.getQuantity() + "," + mc.name + "," + mc.getPrice() + "\n");
                        bw.write(mc.getChildrenCount() +"\n");
                        for(int j = 0; j < a.getChildrenCount(); j++){
                            bw.write(mc.getChild(j).name + "\n");
                        }
                    } else {
                        bw.write(0 +"\n");
                        bw.write(mc.getQuantity() + "," + mc.name + "," + mc.getPrice() + "\n");
                    }
                }
            }

        } catch (IOException e) {
                e.printStackTrace();
        }

        try {
            String time = LocalDateTime.now().toString();
            String filename = "restaurant\\historyOrder\\" + time +".txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(inactiveOrders.size()+"\n");
            for(Order curOrder: inactiveOrders){
                Reservation r = curOrder.reservation;
                bw.write(r.time.toString() + "," + r.tableId + "," + r.pax + ","+ r.contact + "\n");
                bw.write(curOrder.getStaffId() + "\n");
                bw.write(curOrder.getTotalPrice() + "\n");
                bw.write(curOrder.order.getChildrenCount() + "\n");
                for(int i = 0; i < curOrder.order.getChildrenCount(); i++){
                    MenuComponent mc = curOrder.order.getChild(i);
                    if(mc instanceof MenuBundle a){
                        bw.write(1 + "\n");
                        bw.write(mc.getQuantity() + "," + mc.name + "," + mc.getPrice() + "\n");
                        bw.write(mc.getChildrenCount() +"\n");
                        for(int j = 0; j < a.getChildrenCount(); j++){
                            bw.write(mc.getChild(j).name + "\n");
                        }
                    } else {
                        bw.write(0 +"\n");
                        bw.write(mc.getQuantity() + "," + mc.name + "," + mc.getPrice() + "\n");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadStaff(){
        try {
            String fileName = "restaurant\\staff.txt";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numOfStaff = Integer.parseInt(br.readLine());
            for(int i = 0; i < numOfStaff; i++){
                String name = br.readLine();
                String[] list = br.readLine().split(",");
                int id = Integer.parseInt(list[0]);
                String title = list[1];
                String gender = list[2];
                Staff curStaff = new Staff(name, id, title, gender);
                staff.put(id, curStaff);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStaff(){
        try {
            String filename = "restaurant\\staff.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(staff.size() +"\n");
            for(Staff curStaff: staff.values()){
                bw.write(curStaff.getName() + "\n");
                bw.write(curStaff.getId() + "," + curStaff.getTitle() + "," + curStaff.getGender() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(){
        saveOrder();
        saveStaff();
    }

    public ArrayList<Order> loadhistoryOrders(LocalDate from, LocalDate to){
        return null;
    }


}
