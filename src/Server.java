import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
    PrintWriter pw;
    BufferedReader br;
    Socket s;
    int k = 0;
    public MT(Socket s){this.s=s;}
    public void run() {
    try{
        br.readLine();
        pw=new PrintWriter(s.getOutputStream());
        while(true)
        pw.write(-33);
    }catch (Exception e){return;}
    }
}
