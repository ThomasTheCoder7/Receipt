import ReceiptInfo.Receipt;

import java.awt.*;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws Exception {

    Receipt r = Receipt.Load("@Sa1639643664013");
    r.RemoveItems("Coke",4);
    r.addItem("Coke",2,4);
        System.out.println(r);



    }
}
