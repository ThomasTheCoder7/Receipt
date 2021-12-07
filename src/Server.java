import ReceiptInfo.Receipt;

import javax.print.attribute.standard.PDLOverrideSupported;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args)throws Exception {
        ServerSocket ss = new ServerSocket(1434);
        ss.setReuseAddress(true); //Enable Multithreading

        int ClientNum = 0;
        while (true){ //as long as Server ready it will accept Clients
            Socket s = ss.accept();
            MT mt = new MT(s);
            Thread thread = new Thread(mt);
            thread.start();

        }
    }
}
class MT implements Runnable{

    Socket socket;
    int k = 0;
    public MT(Socket s){
        this.socket = s;
    }
    public void run() {

        DataOutputStream dos = null;
    try{
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        String t;
        while(true){
            int CASE = dis.readInt();
            switch (CASE){
                case 1:
                    FindAReceipt(dis,dos,oos);
                    break;
                case 2:
                    break;
                case 5:
                    Receipt r = (Receipt) ois.readObject();
                    Receipt.Save(r);
                    System.out.println("saved");
            }
        }


    }catch (Exception e){return;}
    }


    static void FindAReceipt(DataInputStream dis,DataOutputStream dos,ObjectOutputStream oos) throws Exception {
        String id = dis.readUTF();
        String Directory = "Receipts/"+id+".txt";
        File[] files = new File("Receipts").listFiles();
        boolean found = false;
        for(int i = 0;i<files.length;i++){
            if((id+".txt").equals(files[i].getName())){ found = true; }
        }
        dos.writeBoolean(found);
        if(found)
        oos.writeObject(Receipt.Load(id));

    }


    static void CreateAReceipt(DataInputStream dis , DataOutputStream dos)throws Exception{
        String name;
        double price;


    }





}//end class
