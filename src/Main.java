import ReceiptInfo.Receipt;

import java.awt.*;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws Exception {

    Receipt r = Receipt.Load("@Sa1639910665927");
        Receipt.Delete(r);
        System.out.println(r);



    }
}
