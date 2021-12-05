# Receipt application


## **About the project**
This project is an application that simulates Receipts in any shop and the created Receipts can be saved in external folder that can be recovered later on.
I used in the saving and loading methods semaphores to synchronise the shared resource "I mean by that the external folder", later on I will clarify in detail how did I made the saving and loading methods in the class.
____
# **Features**

###
**Printing the receipt**
**Saving the receipt**
**Loading the receipt**
**Unique id**
___
## Printing the receipt

Each receipt will be printed in this way.
![image-0](https://github.com/ThomasTheCoder7/Receipt/blob/master/EXAMPLE.png?raw=true%27%3E)
___


## Saving the receipt
Whenever you call the static method "Save" you need to pass the Object that you want to save and it will be saved in the external file by it's ReceiptID, I used the FileWriter class to make that happen.
___
## Loading the receipt
Whenever you call the static method "Load" you need to pass the id of the receipt you want to get and if the program finds it it will print it out, if it did not find the receipt you will get the message "Not Found".
___
## Unique id
Each ID will be generated by method called IDMAKER which will construct the id in this way, first two letters of the shop name,first letter of each item in the list,the time that ID got created in seconds.
___
## Flexiblity in the item list
I made two classes Items , Item these two classes helped me to organize how the items are added, removed and created,this including generating unique id for each item , calculate the quantity and calculating the total.
