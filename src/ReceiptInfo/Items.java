package ReceiptInfo;


import java.io.Serializable;
import java.util.Locale;

public class Items implements Serializable {
    private Item[] items=new Item[5];
    private int nItems=0;
    private double total;


    public int getnItems() {
        return nItems;
    }

    public Item getItems(int i) {
        return items[i];
    }

    public void addItem(String name, double price){
        boolean exists = false;
        total+=price;
        String ID = name.toUpperCase().charAt(0)+""+name.length()+""+name.toUpperCase().charAt(name.length()-1)+""+(int)price;
        for(int i = 0;i<nItems;i++){
            if(ID.equals(items[i].id)){items[i].quantity++; exists=true;}
        }


        if(nItems>=items.length){
            Item[] temp = new Item[nItems*2];
            for(int i = 0;i<items.length;i++){
                temp[i]=items[i];
            }
            items = temp;
        }
        if(!exists) {

        items[nItems++]=new Item(name,ID,price,1);
        }
    }

    public double getTotal() {
        return total;
    }

    public void RemoveAllItemsNamed(String name) {
        int k = 0;
        boolean found = false;
        for (int i = 0; i < nItems; i++) {
            if (name.toLowerCase().equals(items[i].name.toLowerCase())) {
                found=true;

                break;
            }
            k++;
        }
        if (found) {
            total-=items[k].price;
            for (int i = k; i < nItems; i++) { items[i] = items[i + 1];}
            nItems--;
        }

    }
    public void RemoveItem(String name,int Quantity) {
        int k = 0;
        boolean found = false;
        for (int i = 0; i < nItems; i++) {
            if (name.toLowerCase().equals(items[i].name.toLowerCase())) {
                found=true;
                if(items[i].quantity>1){items[i].quantity--; total-=items[k].price; return;}
                break;
            }
            k++;
        }
        if (found) {
            total-=items[k].price;
            for (int i = k; i < nItems; i++) { items[i] = items[i + 1];}
            nItems--;
        }

    }


    public String toString() {
        String s = "";
        for (int i=0;i<nItems;i++){
            s += items[i]+"\n";
        }
        return s;
    }
}
