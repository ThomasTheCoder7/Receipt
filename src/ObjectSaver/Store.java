package ObjectSaver;

import ReceiptInfo.Item;
import ReceiptInfo.Receipt;

import java.util.concurrent.Semaphore;

public class Store{
    public static Semaphore s = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {

    }
}
