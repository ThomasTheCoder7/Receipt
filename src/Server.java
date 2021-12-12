import ReceiptInfo.Receipt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Server {
    static int NThreads =0;
    public static void main(String[] args)throws Exception {
        ServerSocket ss = new ServerSocket(1234);

        ss.setReuseAddress(true); //Enable Multithreading
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
    static Semaphore[] sem = new Semaphore[files.length];
    static String id;
    Socket socket;
    int k = 0;
    public MT(Socket s){

        this.socket = s;
        for(int i = 0;i< files.length;i++){
            usedR[i]=files[i].getName();
        }

    }
    public void run() {

        DataOutputStream dos = null;
    try{
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        int CASE;
        while(true){
            CASE = dis.readInt();
            System.out.println(CASE);
            switch (CASE){
                case 1:
                    FindAReceipt(dis,dos,oos);
                    System.out.println("left");
                    Receipt r = (Receipt) ois.readObject();
                    Receipt.Save(r);
                    int x = findSpot(id+".txt");
                    System.out.println(x);
                    sem[x].release(1);
                    break;
                case 2:
                    CreateAReceipt(dis,dos,ois);
                    RefreshFiles();
                    break;
                case 3:
                    boolean found = false;
                    while(!found) {
                        id = dis.readUTF()+".txt";
                        for (int i = 0; i < files.length; i++) {
                            if (id.equals(files[i].getName())) {
                                found = true;
                                break;
                            }
                        }
                        dos.writeBoolean(found);
                    }
                    int spot = findSpot(id);
                    if(sem[spot]==null){sem[spot]=new Semaphore(1);}
                    sem[spot].acquire();
                   Receipt.Delete(Receipt.Load(id.substring(0,id.length()-4)));
                   sem[spot].release();
                case 5:

                    break;
                default:
                    socket.close();
                    break;
            }

        }
    }catch (Exception e){
    }
    }


    static boolean FindAReceipt(DataInputStream dis,DataOutputStream dos,ObjectOutputStream oos) throws Exception {
        boolean found = false;
        MT.files = new File("Receipts").listFiles();
        while(!found){
            id = dis.readUTF();
            String ID =id+".txt";
            for(int i = 0;i<files.length;i++){
            if((ID).equals(files[i].getName())){ found = true; }
            }
            dos.writeBoolean(found);
            if(found){
                int spot = findSpot(ID);
                if (sem[spot]==null){sem[spot]=new Semaphore(1);}
                while (!sem[spot].tryAcquire());

                oos.writeObject(Receipt.Load(id));
                oos.flush();
                dos.flush();
            }
            else System.out.println("not found");
        }
        return found;
    }





    static void CreateAReceipt(DataInputStream dis , DataOutputStream dos,ObjectInputStream ois)throws Exception{
        Receipt r = (Receipt) ois.readObject();
        Receipt.Save(r);
    }
    public static int findSpot(String id){
        for(int i = 0;i<usedR.length;i++){
            if(id.equals(usedR[i]))return i;
        }
        return -1;
    }



    static void RefreshFiles(){
        File[] F1 = new File("Receipts").listFiles();
        String[] usedR1 = new String[F1.length];
        for(int i = 0;i< F1.length;i++){
            usedR1[i]=F1[i].getName();
        }
        usedR=usedR1;
        Semaphore[] temp = new Semaphore[F1.length];
        int k=0;

        for(int i =0;i<sem.length;i++){
            if(sem[i]==null)
                temp[i]=new Semaphore(1);
            else
                temp[i]=sem[i];
        }
        MT.files = F1;
        sem=temp;
    }



}//end class
