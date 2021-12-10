import ReceiptInfo.Receipt;

public class Main {
    public static void main(String[] args) throws Exception {

    Receipt r = Receipt.Load("@Co1639010529923");
        Receipt.Delete(r);


    }
}
