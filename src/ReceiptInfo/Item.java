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


    String space(int len,int total){
        String s="";
        int num = Math.abs(len-total);
        for(int i =0;i<num;i++){s+=" ";}
        return s+"| ";
    }

    public String toString() {
        String PRICE;
        if(name.length()>13)
        name = name.substring(0,10)+".."+name.substring(name.length()-2);
        PRICE = String.format("%.2f",quantity*price);
        if(PRICE.length()>9){PRICE =PRICE.substring(0,8)+"..";}
        return
                 "| "+name+space(name.length(),15)+id+space(String.valueOf(id).length(),5)+
                         quantity+ space(String.valueOf(quantity).length(),9)+
                         PRICE+space(PRICE.length(),11);
    }
}

