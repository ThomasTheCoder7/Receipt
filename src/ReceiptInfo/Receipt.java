package ReceiptInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Receipt implements Serializable {
    static final long serialVersionUID = 53L;
   static Semaphore s = new Semaphore(1);
   private String id="";
   private String ShopName;
   private Date dateOfPurchase;
   private Items items;
   private double total;
   private int quantity;
   SimpleDateFormat  sdf = new SimpleDateFormat("MM/dd/yyyy  hh:mm aa");
   String displayDate;

    public Receipt(String shopName) {
        ShopName = shopName;
        this.dateOfPurchase = new Date();
        this.items = new Items();
        this.total = items.getTotal();
        displayDate = sdf.format(dateOfPurchase);
        IDMAKER();
    }

    public void addItem(String n,double price){
        items.addItem(n,price);
    }
    public void addItem(String n,double price,int Quantity){items.addItem(n,price,Quantity);}
    public void addItem(Item i){items.addItem(i.name,i.price,i.quantity);}
    public void RemoveItems(String n,int q){items.RemoveItem(n,q);}
    public void RemoveAll(String n){items.RemoveAllItemsNamed(n);}
    public int  GetNItems(){return items.getnItems();}
    public static void Save(Receipt r) throws Exception {
        String Directory = "Receipts/"+r.id+".txt";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Directory));
        oos.writeObject(r);
        oos.flush();
        oos.close();
    }

    public String getId() {
        return id;
    }

    public static void  Delete(Receipt r){
        String Directory = "Receipts\\"+r.id+".txt";

        File file = new File(Directory);

        System.out.println(file.delete()+""+file.exists());
    }

    public static Receipt Load(String id) throws Exception {
        String Directory = "Receipts/"+id+".txt";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Directory));
        Receipt r = (Receipt) ois.readObject();
        ois.close();
        return r;
    }
    public void IDMAKER(){
        this.id += "@";
        this.id += ShopName.substring(0,2);
        String init="";
        for(int i = 0;i<items.getnItems();i++){
            init+=items.getItems(i).name.charAt(0);
        }
        id+=init;
        id+= dateOfPurchase.getTime();
    }
    public String toString() {
        String[] byemsg = {"Come again soon!","See you next time!","Thank you for choosing us!"};
     String s = displayDate+"\n"+ShopName+"\n"+id+"\n";
     s+="+===========+======+===========+===========+\n";
     s+="| NAME      | ID   | Quantity  | Price     |\n";
     s+="+===========+======+===========+===========+\n";
     s+=items.toString()+"+===========+======+===========+===========+";
     s+= String.format("\nTotal = %.2f",items.getTotal() );
     s+="\n"+byemsg[new Random().nextInt(byemsg.length)];
     return s;
    }
}
