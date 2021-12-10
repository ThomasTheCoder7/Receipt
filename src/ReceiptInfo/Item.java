package ReceiptInfo;

import java.io.Serializable;

public class Item implements Serializable {
    String name,id;
    double price;
    int quantity;
    static final long serialVersionUID = 53L;
    public Item(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item(String name, String id, double price, int quantity) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public String space(int len){
        int num = 5;
        String s="";
        int t = len - num;
        num = num - t;
            for(int i = 0;i<=num;i++){

                s+=" ";

            }
            return s;
    }
    //10
    String spaceName(int len){
        String s="";
        int num = Math.abs(len-10);
        for(int i = 0;i<num;i++){
            s+=" ";
        }

        return s+"| ";
    }
    //9
    String spaceQuantity(int len){
        String s="";
        int num = Math.abs(len-10);
        for (int i =0;i<num;i++){s+=" ";}
        return s+"| ";
    }

    String spacePrice(int len){
        String s="";
        int num = Math.abs(len-9);
        for(int i = 0;i<num;i++){s+=" ";}
        return s+" |";
    }
    //5
    String spaceID(int len){
        String s="";
        int num = Math.abs(len-5);
        for(int i =0;i<num;i++){s+=" ";}
        return s+"| ";
    }

    public String toString() {
        if(name.length()>8)
        name = name.substring(0,5)+".."+name.substring(name.length()-2);
        System.out.println(name.length()+"lrn");
        return
                 "| "+name+spaceName(name.length())+id+spaceID(String.valueOf(id).length())+
                         quantity+ spaceQuantity(String.valueOf(quantity).length())+
                         price*quantity+spacePrice(String.valueOf(quantity*price).length());
    }
}

