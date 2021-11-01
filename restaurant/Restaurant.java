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

    public HashMap<Integer, MenuComponent> menuReference;

    public TableManager tableManager;

    public ArrayList<Order> activeOrders;

    public ArrayList<Order> inactiveOrders;

    public HashMap<Integer, Staff> staff;

    public TreeSet<Order> historyOrders;


    public Restaurant() {
        menuReference = new HashMap<>();
        loadMenuReference();
        menu = new Menu(-1, "Menu", "");
        loadMenu();
        tableManager = new TableManager();
        staff = new HashMap<>();
        loadStaff();
        activeOrders = new ArrayList<>();
        loadOrder();
        inactiveOrders = new ArrayList<>();
        historyOrders = new TreeSet<>();
        
    }

    /**
     * Returns a list of all active orders.
     *
     * @return A list of all active orders.
     */
    public ArrayList<Order> getActiveOrders() {
        return activeOrders;
    }

    public Order getOrderbytableId(int tableId){
        int n = activeOrders.size();
        Order o = null;
        for (int i = 0; i< n; i++){
            if (activeOrders.get(i).getTableId() == tableId){
                o = activeOrders.get(i);
                break;
            }
        }
        return o;
    }

    /**
     * Generates a sales report of the given time period.
     *
     * @return A sales report of the given time period.
     */
    public SalesReport generateSalesReport(LocalDate from, LocalDate to) {
        SalesReport report = new SalesReport(from, to);
        if(historyOrders == null) 
            loadhistoryOrders();

        Iterator it = historyOrders.iterator();
        for (;it.hasNext();){
            Order o = (Order)it.next();
            if (o.reservation.time.toLocalDate().compareTo(from)>=0 && o.reservation.time.toLocalDate().compareTo(to)<=0){
                report.processOrder(o);
            }
        }
        return report;
    }

    // All save and load functions
    public void loadMenuReference(){
        try{
            String fileName = "restaurant\\MenuReference.txt";
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String l;
            while((l = br.readLine()) != null){
                String[] temp = l.split(":");
                int itemCode = Integer.parseInt(temp[0]);
                int flag = Integer.parseInt(temp[1]);
                if (flag == 0){
                    // alacarte item
                    String itemName = temp[2];
                    Double price = Double.parseDouble(temp[3]);
                    String itemDescription = temp[4];
                    MenuLeaf ml = new MenuLeaf(itemCode, itemName, itemDescription);
                    ml.setPrice(price);
                    menuReference.put(itemCode,ml);
                }else{
                    // bundle item
                    /* assumed always the component of the bundle item has alreay been parsed */
                    String itemName = temp[2];
                    double price = Double.parseDouble(temp[3]);
                    MenuBundle mb = new MenuBundle(itemCode, itemName, "");
                    mb.setPrice(price);
                    String[] bundleItems = temp[4].split(",");
                    int numOfItems = bundleItems.length;
                    for (int i =0; i< numOfItems; i++){
                        int childCode = Integer.parseInt(bundleItems[i]);
                        mb.addChild(menuReference.get(childCode));
                    }
                    menuReference.put(itemCode, mb);
                }
               
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveMenuReference(){
        try{
            String fileName = "restaurant\\MenuReference.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (MenuComponent mc : menuReference.values()){
                if (mc instanceof MenuLeaf){
                    bw.write(mc.code+":0:"+mc.name+":"+mc.getPrice()+":"+mc.description+"\n");
                }else{
                    String t = mc.code+":1:"+mc.name+":"+mc.getPrice()+":";
                    
                    for (int i =0 ; i<mc.getChildrenCount(); i++){
                        t = t + mc.getChild(i).code+",";
                    }
                    bw.write(t.substring(0, t.length()-1)+"\n");
                }
                
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    
    private void loadMenu() {

        try{
            String fileName = "restaurant\\Menu.txt";

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int numOfCategory = Integer.parseInt(br.readLine());
            for(int i = 0; i < numOfCategory; i++){
                String categoryName = br.readLine();
                menu.addChild(new Menu(-1, categoryName, ""));
                int numOfItems = Integer.parseInt(br.readLine());
                for(int j = 0; j < numOfItems; j++){
                    int itemCode = Integer.parseInt(br.readLine());
                    menu.getChild(i).addChild(menuReference.get(itemCode));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMenu() {
        try {
            String fileName = "restaurant\\Menu.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(menu.getChildrenCount() + "\n");

            for (int i = 0; i < menu.getChildrenCount(); i++) {
                MenuComponent category = menu.getChild(i);
                bw.write(category.name + "\n");
                bw.write(category.getChildrenCount() +"\n");
                for(int j = 0; j < category.getChildrenCount(); j++){
                    MenuComponent item = category.getChild(j);
                    bw.write(item.code+"\n");
                }
                
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
                    l = br.readLine();
                    String[] temp = l.split(",");
                    int quantity = Integer.parseInt(temp[0]);
                    int itemCode = Integer.parseInt(temp[1]);
                    curOrder.addItem(menuReference.get(itemCode), quantity);
                }
                activeOrders.add(curOrder);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveOrder(){
        // save active orders
        try {
            String filename = "restaurant\\activeOrders.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(activeOrders.size()+"\n");
            for(Order curOrder: activeOrders){
                Reservation r = curOrder.reservation;
                bw.write(r.time.toString() + "," + r.tableId + "," + r.pax + ","+ r.contact + "\n");
                bw.write(curOrder.getStaffId() + "\n");
                bw.write(curOrder.orderedItems.getChildrenCount() + "\n");
                for(int i = 0; i < curOrder.orderedItems.getChildrenCount(); i++){
                    MenuComponent mc = curOrder.orderedItems.getChild(i);
                    int quantity = mc.getQuantity();
                    bw.write(quantity+","+mc.code+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // save history orders
        try {
            
            for(Order curOrder: inactiveOrders){
                String filename = "restaurant\\historyOrder\\" + curOrder.reservation.time.toString()+".txt";
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                Reservation r = curOrder.reservation;
                bw.write(r.time.toString() + "," + r.tableId + "," + r.pax + ","+ r.contact + "\n");
                bw.write(curOrder.getStaffId() + "\n");
                bw.write(curOrder.getTotalPrice() + "\n");
                bw.write(curOrder.orderedItems.getChildrenCount() + "\n");
                for(int i = 0; i < curOrder.orderedItems.getChildrenCount(); i++){
                    MenuComponent mc = curOrder.orderedItems.getChild(i);
                    bw.write(mc.getQuantity()+","+mc.code+"\n");
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
        saveMenuReference();
        saveMenu();
        saveOrder();
        saveStaff();
    }

    public void loadhistoryOrders(){
        String pathName = "restaurant\\historyOrders\\";

        File file = new File(pathName);
        File[] fileList = file.listFiles();
        
        for (int i =0; i<fileList.length; i++){
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileList[i]));
                String l = br.readLine();
                String[] temp = l.split(",");
                LocalDateTime time = LocalDateTime.parse(temp[0]);
                int tableId = Integer.parseInt(temp[1]);
                int pax = Integer.parseInt(temp[2]);
                long contact = Long.parseLong(temp[3]);
                int staffId = Integer.parseInt(br.readLine());
                double totalPrice = Double.parseDouble(br.readLine());
                int numOfItems = Integer.parseInt(br.readLine());
                Reservation r = new Reservation(time, tableId, pax, contact);
                Order o = new Order(r, staff.get(staffId));
                for (int j = 0; j< numOfItems; j++) {
                    String[] t = br.readLine().split(",");
                    int quantity = Integer.parseInt(t[0]);
                    int itemCode = Integer.parseInt(t[1]);
                    o.addItem(menuReference.get(itemCode), quantity);
                }
                historyOrders.add(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
           
        }

    }


}
