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
                        if(FindAReceipt(dis,dos,oos)) {
                            Receipt r = (Receipt) ois.readObject();
                            Receipt.Save(r);
                            System.out.println(id);
                            int x = findSpot(id + ".txt");
                            sem[x].release(1);
                            System.out.println(sem[x]);
                        }
                        break;
                    case 2:
                        //Creating a receipt
                        CreateAReceipt(dis,dos,ois);
                        RefreshFiles();
                        break;
                    case 3:
                        //Deleting a receipt
                        DeleteAReceipt(dis,dos,oos);
                        break;
                    case 5:
                        socket.close();
                        break;
                    default:
                        break;
                }

            }
        }catch (Exception e){
        }
    }

    static void DeleteAReceipt(DataInputStream dis,DataOutputStream dos,ObjectOutputStream oos) throws Exception {
        boolean found = false;
        String aid="";
        while(!found) {
           aid=dis.readUTF();

            if(aid.equals("exit")){return;}
            aid +=".txt";

            for (int i = 0; i < files.length; i++) {
                if (aid.equals(files[i].getName())) {
                    found = true;
                    break;
                }
            }
            dos.writeBoolean(found);
        }
        dos.flush();
        int spot = findSpot(aid);
        if(sem[spot]==null){sem[spot]=new Semaphore(1);}
        sem[spot].acquire();
        Receipt.Delete(Receipt.Load(aid.substring(0,aid.length()-4)));

        dos.writeInt(5);
        RefreshFiles();
    }
    static boolean FindAReceipt(DataInputStream dis,DataOutputStream dos,ObjectOutputStream oos) throws Exception {
        boolean found = false;

        int spot;
        while(!found){
            spot=0;
            id = dis.readUTF();
            String ID =id+".txt";
            if(id.equals("exit")){return false;}
            for(int i = 0;i<MT.files.length;i++){
                if((ID).equals(MT.files[i].getName())){ found = true; break;}
                spot++;
            }
            dos.writeBoolean(found);
            if(found){
                spot = findSpot(ID);
               if(sem[spot]==null){sem[spot]=new Semaphore(1);}
               sem[spot].acquire();
                oos.writeObject(Receipt.Load(id));
                oos.flush();
                dos.flush();
                id=ID.substring(0,ID.length()-4);
                return true;
            }
            else System.out.println("not found");
        }
        return found;
    }





    static void CreateAReceipt(DataInputStream dis , DataOutputStream dos,ObjectInputStream ois)throws Exception{
        if(dis.readUTF().equals("exit")){return;}
        Receipt r = (Receipt) ois.readObject();
        Receipt.Save(r);
        System.out.println("FFFF");
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
                else temp[i]=new Semaphore(1);
            }else
                temp[i] = new Semaphore(1);

        }

        MT.files = F1;
        MT.sem=temp;

    }



}//end class