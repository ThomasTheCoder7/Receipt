import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Casher {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",1334);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner scan = new Scanner(System.in);
        int k;
        boolean start = true;
        while (true) {
            System.out.println("1--> Find a Receipt");
            System.out.println("2--> Exit");
            System.out.print("Please pick a number:");
            if (scan.nextInt() == 2) {break;}
            while (true) {
                System.out.print("Enter the Receipt ID:");
                bw.write(scan.nextLine());
                k = Integer.parseInt(br.readLine());
                if(k==-33)System.out.println("invalid Receipt ID");
                }
            }//inner while
        }//outer while


    }

