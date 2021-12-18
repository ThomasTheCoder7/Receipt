import ReceiptInfo.Receipt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Server {
    static int NThreads =0;
    public static void main(String[] args)throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        File folder = new File("Receipts");
        if(!folder.exists()){ folder.mkdir();}
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
                        //Find a receipt
                        FindAReceipt(dis,dos,oos);
                        Receipt r = (Receipt) ois.readObject();
                        Receipt.Save(r);
                        int x = findSpot(id+".txt");

                        sem[x].release(1);
                        break;
                    case 2:
                        //Creating a receipt
                        CreateAReceipt(dis,dos,ois);
                        RefreshFiles();
                        break;
                    case 3:
                        //Deleting a receipt
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
                        RefreshFiles();


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

        int spot;
        while(!found){
            spot=0;
            id = dis.readUTF();
            String ID =id+".txt";
            for(int i = 0;i<MT.files.length;i++){
                if((ID).equals(MT.files[i].getName())){ found = true; break;}
                spot++;
            }
            dos.writeBoolean(found);
            if(found){
                System.out.println(spot);
               if(sem[spot]==null){sem[spot]=new Semaphore(1);}
               sem[spot].acquire();
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
        RefreshFiles();
    }
    public static int findSpot(String id){
        int x = 0;
        for(int i = 0;i<MT.files.length;i++){
            if(id.equals(MT.files[i].getName())){return x;}
            x++;
        }
        return -1;
    }



    static void RefreshFiles(){
        File[] F1 = new File("Receipts").listFiles();
        String[] usedR1 = new String[F1.length];
        Semaphore[] temp = new Semaphore[F1.length];
        for(int i =0;i<temp.length;i++){

            if(i<sem.length) {
                if (sem[i] == null)
                    temp[i] = new Semaphore(1);
                else if(sem[i].availablePermits()==0)
                    temp[i] = new Semaphore(0);
                else sem[i]=new Semaphore(1);
            }else
                temp[i] = new Semaphore(1);

        }

        MT.files = F1;
        MT.sem=temp;

    }



}//end class