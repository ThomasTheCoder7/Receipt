package ReceiptInfo;

import java.io.Serializable;

public class Item implements Serializable {
    String name,id;
    double price;
    int quantity;
    static final long serialVersionUID = 53L;
    public Item(){

    }


    public Item(String name, String id, double price,int quantity) {
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



    public String toString() {
        return
                 "| "+name+space(name.length())+
                 id+space(String.valueOf(id).length())+
                         quantity+space(String.valueOf(quantity*price).length())+
                         price*quantity+" |"
        ;
    }
}

