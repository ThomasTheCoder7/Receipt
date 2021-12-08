import ReceiptInfo.Receipt;

import javax.print.attribute.standard.PDLOverrideSupported;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Server {
    public static void main(String[] args)throws Exception {
        ServerSocket ss = new ServerSocket(1334);
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
    static File[] files = new File("Receipts").listFiles();
    static String[] usedR=new String[files.length];
    static int COUNTER = 0;
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
        Thread AutoSaverThread = new Thread(()-> {
            while(true){
                try {
                    while(true){
                        Receipt r = (Receipt) ois.readObject();
                        Receipt.Save(r);
                        Thread.sleep(5000);//Autosave every 20 sec
                        System.out.println("Saved");
                    }} catch (Exception e) { e.printStackTrace();}

            }
        });
        String t;
        int CASE;
        while(true){
            CASE = dis.readInt();
            System.out.println(CASE);

            switch (CASE){
                case 1:
                    FindAReceipt(dis,dos,oos);
                    System.out.println("left");
                    break;
                case 2:
                    break;
                case 5:

                    Receipt r = (Receipt) ois.readObject();
                    Receipt.Save(r);
                    int x = findSpot(r.getId());
                    System.out.println(x);
                    sem[x].release(1);
                    System.out.println("saved");
                    break;
                default:
                    break;
            }

        }


    }catch (Exception e){return;}
    }

    static Semaphore[] sem = new Semaphore[files.length];

    static boolean FindAReceipt(DataInputStream dis,DataOutputStream dos,ObjectOutputStream oos) throws Exception {
        boolean found = false;
        while(!found){
            String id = dis.readUTF();
            String Directory = "Receipts/"+id+".txt";
            for(int i = 0;i<files.length;i++){
                if((id+".txt").equals(files[i].getName())){ found = true; }
            }
            dos.writeBoolean(found);
            if(found){
                System.out.println("ttt");
                if(COUNTER==0||!find(id)){
                    usedR[COUNTER]=id;
                    sem[COUNTER]=new Semaphore(1);
                    sem[COUNTER].acquire();
                    COUNTER++;
                    System.out.println("fff");
                }else

                sem[findSpot(id)].acquire();
                oos.writeObject(Receipt.Load(id));
                oos.flush();
                dos.flush();
            }
            else System.out.println("not found");
        }
        return found;
    }


    static void CreateAReceipt(DataInputStream dis , DataOutputStream dos)throws Exception{



    }
    static ObjectInputStream  OIS;
    static void AutoSaver(Socket s) throws IOException {
        System.out.println("vvv");
        Socket f = s;
        OIS = new ObjectInputStream(f.getInputStream());
        Thread AutoSaverThread = new Thread(()-> {
            while(true){
                try {
                    while(true){

                        Receipt r = (Receipt) OIS.readObject();
                        Receipt.Save(r);
                        Thread.sleep(5000);//Autosave every 20 sec
                        System.out.println("Saved");
                    }



                } catch (Exception e) { e.printStackTrace();}

            }
        });
        AutoSaverThread.start();


    }




    public static void delete(String id){
        String[] temp = new String[usedR.length-1];
        for(int i = 0;i<usedR.length;i++){
            for(int j = i;j< usedR.length;j++){
                if(usedR[i].equals(usedR[j])&&i!=j){
                    for (int k = j;k< usedR.length-1;k++)
                        temp[k]=usedR[k+1];
                }//end if
            }//end inner for
        }//end outer for
        usedR=temp;
    }

    public static boolean find(String id){
        if(COUNTER==0){return false;}
        for(int i = 0;i<usedR.length;i++){
            for(int j = 0;j< usedR.length;j++) {
           if(usedR[i].equals(usedR[j])&&i!=j)return true;
            }
        }
        return false;
    }

    public static int findSpot(String id){
        if(COUNTER==0){return 0;}
        for(int i = 0;i<usedR.length;i++){
            for(int j = 0;j< usedR.length;j++) {
                if(usedR[i].equals(usedR[j])&&i!=j)return j;
            }
        }
        return -1;
    }





}//end class
