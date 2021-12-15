import ReceiptInfo.Item;
import ReceiptInfo.Receipt;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cashier {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",1234);
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        DataInputStream dis = new DataInputStream(s.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        Scanner scan = new Scanner(System.in);
        while (true) {

            System.out.println("1--> Find a Receipt");
            System.out.println("2--> Create a New Receipt");
            System.out.println("3--> Delete a Receipt");
            System.out.println("4--> Exit");
            System.out.print("Please pick a number:");
            int statement = scan.nextInt();
            scan.nextLine(); //reset scanner.
            switch (statement){
                case 1:
                    dos.writeInt(1);
                    FindAReceipt(dis,dos,ois,oos);
                    break;
                case 2:
                    dos.writeInt(2);
                    dos.flush();
                    CreateAReceipt(dis,dos,oos);
                    break;
                case 3:
                    dos.writeInt(3);
                    System.out.print("Please Enter the Receipt ID:");
                    String id = scan.nextLine();
                    dos.writeUTF(id);
                    while(true){
                        if(dis.readBoolean()){
                            System.out.println("Deleting....");
                            System.out.println("Item Deleted!");
                            break;
                        }
                        else System.out.println("invalid Receipt ID");
                        System.out.print("Please Enter the Receipt ID:");
                        id = scan.nextLine();
                        dos.writeUTF(id);
                    }
                break;
                case 4:
                    System.out.println("GOOD BYE !");
                    return;
                default:
                    System.out.println("invalid input!");
                    break;

            }

        }

    }



        public static void FindAReceipt(DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos) throws Exception{
        Scanner scan = new Scanner(System.in);
            String id;
            while (true) {
                System.out.print("Please Enter the Receipt ID or type 'exit':");
                id = scan.nextLine();
                if(id.equals("exit")){return;}
                dos.writeUTF(id);
                dos.flush();
                if(dis.readBoolean()){System.out.println("Connecting...."); break; }
                else System.out.println("invalid Receipt ID");
            }

            Receipt r = (Receipt) ois.readObject();
            System.out.println("Connected!");
            ReceiptOperations(r,oos,dos);
            oos.writeObject(r);
        }


        public static void CreateAReceipt(DataInputStream dis,DataOutputStream dos,ObjectOutputStream oos)throws Exception{
        Scanner scan = new Scanner(System.in);
        String ShopName;
        while(true){
            System.out.print("Please Enter the shop name:");
            ShopName = scan.nextLine();
            while(ShopName.length()<2){
            System.out.println("invalid name");
            System.out.print("Please Enter the shop name:");
            ShopName = scan.nextLine();

            }


            Pattern p = Pattern.compile("^[a-z ']*$",Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(ShopName);
            if(m.find()){break;}//if string contains only letters I leave loop else I will print invalid name and I will ask you again.
            else{ System.out.println("invalid name");}
        }
        Receipt r = new Receipt(ShopName);
        r.addItem(GenerateItem());
        ReceiptOperations(r,oos,dos);
        oos.writeObject(r);
        }

        static Item GenerateItem(){
            Item i = new Item();
            Scanner scan = new Scanner(System.in);
            String itemname;
            while (true) {
                System.out.print("Please Enter item name:");
                itemname = scan.nextLine();
                while(itemname.length()<2){
                    System.out.println("invalid name");
                    System.out.print("Please Enter item name:");
                    itemname = scan.nextLine();
                }
                Pattern p = Pattern.compile("^[a-z '0-9]*$",Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(itemname);
                if(m.find()&&itemname.length()>2){break;}//if string contains only letters I leave loop else I will print invalid name and I will ask you again.
                else{ System.out.println("invalid name");}
            }
            i.setName(itemname);
            double itemprice;
            while(true){
                System.out.print("Please Enter "+itemname+"'s price:");
                itemprice = scan.nextDouble();
                if(itemprice >  0){ break; }
                else{System.out.println("invalid price");}
            }
            i.setPrice(itemprice);
            int itemquantity;
            while(true){
                System.out.print("How many do you want:");
                itemquantity = scan.nextInt();
                if(itemprice >  0){ break; }
                else{System.out.println("invalid quantity");}
            }
            System.out.println("");
            i.setQuantity(itemquantity);
        return i;
        }

        static void ReceiptOperations(Receipt r,ObjectOutputStream oos,DataOutputStream dos) throws Exception {
            Scanner scan = new Scanner(System.in);
            while(true){

                System.out.println("1--> Add item");
                System.out.println("2--> Delete item");
                System.out.println("3--> Print the receipt");
                System.out.println("4--> Exit");
                System.out.print("Please make your choice:");
                int k = scan.nextInt();
                scan.nextLine();
                switch (k){
                    case 1:
                        r.addItem(GenerateItem());
                        String y;
                        while(true){
                            System.out.print("Add another item (y/n):");
                            y=scan.nextLine();
                            if(y.equals("y")){r.addItem(GenerateItem());}
                            if(y.equals("n")){break;}
                            System.out.print("invalid input please enter the letter 'y' for yes and the letter 'n' for no.");
                        }
                        break;
                    case 2:
                        if(r.GetNItems()<=0){
                            System.out.println("There is no items to delete!");
                            break;
                        }
                        System.out.print("Enter item name:");
                        String name = scan.nextLine();
                        System.out.print("Enter the quantity:");
                        String qty = scan.nextLine();
                        if(qty.equals("*")){r.RemoveAll(name);}
                        else r.RemoveItems(name, Integer.parseInt(qty));
                        System.out.println(r);
                        break;
                    case 3:
                        System.out.println(r);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("invalid input");
                        break;
                }
            }
        }
    }

