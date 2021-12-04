package ReceiptInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Receipt implements Serializable {
   static Semaphore s = new Semaphore(1);
   private String id="";
   private String ShopName;
   private Date dateOfPurchase;
   private Items items;
   private double total;
   private int quantity;
   SimpleDateFormat  sdf = new SimpleDateFormat("MM/dd/yyyy  hh:mm aa");
   String displayDate;

    public Receipt(String shopName, Items items) {
        ShopName = shopName;
        this.dateOfPurchase = new Date();
        this.items = items;
        this.total = items.getTotal();
        displayDate = sdf.format(dateOfPurchase);
        IDMAKER();
    }

    public static void Save(Receipt r) throws IOException, InterruptedException {
        s.acquire();
        String Directory = "Receipts/"+r.id+".txt";
        FileWriter fileWriter = new FileWriter(Directory);
        fileWriter.write(r.toString());
        fileWriter.close();
        Thread.sleep(1000);
        s.release();
    }

    public static String Load(String id) throws IOException, InterruptedException {
        //s.acquire();
        String Directory = "Receipts/"+id+".txt";
        File[] files = new File("Receipts").listFiles();
        boolean found = false;
        for(int i = 0;i<files.length;i++){
            if((id+".txt").equals(files[i].getName())){
                found = true;
            }

        }

        if(found){
            s.acquire();
            String receipt = "";
            Scanner scan = new Scanner(new File(Directory));
            while (scan.hasNext()){
                receipt+=scan.nextLine()+"\n";
            }
            Thread.sleep(1000);
            s.release();
        return receipt;
        }else
    return "Not Found";
    }
    public void IDMAKER(){
        SimpleDateFormat sdff = new SimpleDateFormat("MMddyyyyHH");
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
     String s = displayDate+"\n"+ShopName+"\n"+id+"\n";
     s+="+====================================+\n";
     s+="| NAME        ID    Quantity   Price |\n";
     s+="+====================================+\n";

     s+=items.toString()+"+====================================+";
     return s;
    }
}
