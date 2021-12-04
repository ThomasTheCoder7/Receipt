import ReceiptInfo.Items;
import ReceiptInfo.Receipt;


import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Items items = new Items();
        items.addItem("Honey", 5);
        items.addItem("Coffee", 5);
        items.addItem("Bread", 5);
        items.addItem("Cheese", 5);
        items.addItem("Milk", 5);
        items.addItem("Honey", 5);
        Receipt r = new Receipt("JawadsMall", items);


        Thread T = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Receipt.Load("@JaH1638470966826"));
                } catch (Exception e) { System.out.println(e); }}
        });
        T.start();
        T.run();
    }
}
