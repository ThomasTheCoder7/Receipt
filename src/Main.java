import ReceiptInfo.Items;
import ReceiptInfo.Receipt;


import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
    Receipt r = Receipt.Load("@UQ1638911913041");
        System.out.println(r);
    }
}
