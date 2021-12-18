# Receipt application 🧾

In this project I made a Java application that creates,deletes,print and save receipts for future usage i.e returning an item to a shop,replaceing an item.

# Features 🚀
<br>
<br>

> # Multi threaded Server  <img src="https://cdn-icons-png.flaticon.com/512/3208/3208726.png" width="40px"/>
I made a class called Server to handle the saving operation so every receipt can be saved and restored from the server by inserting the receipt id. The server class is multi threaded so it can serve as many cashiers as possible, but each receipt can be accessed by only one cashier at a time for security reasons
I used semaphores to make that happen.
> 
> 

>

># Saving/Restoring 📝

In the Receipt class there is two static methods Save/Load. Save to save every Receipt object in an external folder, Load is used to load every object that has been saved by the Save method.
I used ObjectOutputStream, ObjectInputStream and the Serializable interface to save and load these objects .

> # Printed Receipts 🖶

each receipt will be printed with the necessary data. i.e date and time, shop name,receipt id,each and every item that have bought and total price.
this is a sample of a printed receipt

<img src="https://github.com/ThomasTheCoder7/Receipt/blob/master/example.png?raw=true" width="500px"/>




># Unique id  ☑️

Each receipt will have it's own unique id, this id consists of first two letters of the shop name and the number of milliseconds since January 1,1970.

#
<br>
<br>
<br>
<br>

# Code :coffee: 

****in this part I will be talking about the program code.****

># Semaphore  :no_entry: 
 
An array of semaphores will be initialised with the size of (number of Receipts in the folder), everytime a user request to modify a receipt it will go to the index of the file in the semaphore array and the semaphore will be initiliased with number 1 "Binary semaphore"

<img src="https://github.com/ThomasTheCoder7/Receipt/blob/master/Resources/semaphore.png?raw=true" width="800">


># Server   <img src="https://cdn-icons-png.flaticon.com/512/3208/3208726.png" width="40px"/>
	


Like I said before the server is multi threaded so it's going to reuse the same address more than once and then run the class MT in different thread.

<img src="https://github.com/ThomasTheCoder7/Receipt/blob/master/Resources/Server.png?raw=true" width = "800">

># Find A Receipt : :mag: 
the cashier will input the receipt id, then the receipt id will be sent to the server and wait for boolean response, if it's true it will load the object then send it to the cashier + the boolean statement. If the response is false, it will only send the boolean statement and the cashier will print a message "invalid Receipt ID"
and it will ask you to input the receipt id once again or exit.

<img src="https://github.com/ThomasTheCoder7/Receipt/blob/master/Resources/FindAreceipt.png?raw=true" width ="900">

># Create A New Receipt :shopping_cart:  
Cashier will create a new object of Receipt and grant the cashier the ability to add items , delete items and print the receipt. Then send the object to the server to save later on and update the semaphore list.
<br>
<img src ="https://github.com/ThomasTheCoder7/Receipt/blob/master/Resources/CreateAnewReceipt.png?raw=true" width = "900">

>#  DeleteAReceipt  :wastebasket: 
Cashier will input the receipt id and send it to the server if the receipt exists it gonna delete it and return true, if the server couldn't find the receipt it will return false and it will asks you to enter the receipt id or exit.

<img src="https://github.com/ThomasTheCoder7/Receipt/blob/master/Resources/DeleteAreceipt.png?raw=true" width="900">
