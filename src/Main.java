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
        Receipt r = new Receipt("RingedCityMarket", items);
        System.out.println(r);
        Receipt.Save(r);
    }
}
