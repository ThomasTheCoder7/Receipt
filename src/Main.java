import ReceiptInfo.Items;
import ReceiptInfo.Receipt;


import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Items items = new Items();
        items.addItem("Ramen", 13);
        items.addItem("Coffee", 5);
        items.addItem("Ramen", 13);
        items.addItem("Ramen", 13);
        items.addItem("Honey", 5);
        items.addItem("Ice cream", 7);
        items.addItem("Milk", 8);
        items.addItem("Honey", 5);
        Receipt r = new Receipt("BinDaood", items);
        FileOutputStream fos = new FileOutputStream(new File("ggg.txt"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(r);
        FileInputStream fis = new FileInputStream("ggg.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Receipt t = (Receipt) ois.readObject();
        t.addItem("COCA",3);
        System.out.println(t);
        fis = new FileInputStream("ggtg.txt");
        ois = new ObjectInputStream(fis);
        Receipt a = (Receipt) ois.readObject();
        a.addItem("COCO",30);
        System.out.println(a);
    }
}
