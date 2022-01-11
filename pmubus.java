package pmubus;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

class BusList { //nodelist for buses
    class Bus { //node class
        int code;
        String destination;
        Bus next;
        
        Bus() {
            code = 0;
            destination = "x";
            next = null;
        }

        Bus(int code, String destination, Bus next) {
            this.code = code;
            this.destination = destination;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Bus code is " + code + " and destination is " + destination;
        }
    }
    
    
    
    private Bus head;
    
    BusList() { //create a list without any data
        head = null;
    }
    
    BusList(Bus head) { //create a list based on an existing head
        this.head = head;
    }
    
    void save() { //saves the list to a text document
        try {
            FileWriter fw = new FileWriter("PMUBus.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            Bus cur = head;
            while(cur != null) {
                bw.write(cur + "\n");
                cur = cur.next;
            }
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    void load() { //loads the data that is saved in the text document so that the nodes exist even after closing program and reopening
        try {
            FileReader reader = new FileReader("PMUBus.txt");
            Scanner scan = new Scanner(reader);
            while (scan.hasNext()) {
                String[] line = scan.nextLine().split(" "); //scans each line in the document and turns it into an array
                //line is "Bus code is [code] and destination is [destination]"; words are separated by spaces
                int code = Integer.parseInt(line[3]); //takes the word in position 3 (code) and converts it to an integer
                String destination = line[7]; //takes the word in position 7 (destination)
                insertLast(code,destination); //create a new node with information
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    
    void display() { //displays the list that is saved in the text document
        try {
            FileReader fr = new FileReader("PMUBus.txt");
            BufferedReader br = new BufferedReader(fr);
            int i;
            while ((i = br.read()) != -1) {
                System.out.print((char)i);
            }
            br.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteFirst() { //remove the first node in the list
        if (head != null) { //if list has data
            Bus cur = head;
            head = head.next;
            cur.next = null;
        }
        save();
    }
    
    public void deleteLast() { //remove the last node in the list
        if (head != null) {
            if(head.next == null) { //if list has only one node, then last is the same as first
                deleteFirst();
            }
            else {
                Bus last = head;
                Bus previoustolast = head;
                while (last.next != null) {
                    previoustolast = last;
                    last = last.next;
                }
                previoustolast.next = null;
            }
        }
        save();
    }
    
    public void deleteAtPosition(int position) { //remove node at a certain position in the list
        if (position == 0) { //position 0 means the first node
            deleteFirst();
        }
        else {
            Bus previous = head;
            int count = 1;
            while (count <= position-1) {
                previous = previous.next;
                count++;
            }
            Bus cur = previous.next;
            previous.next = cur.next;
            cur.next = null;
        }
        save();
    }
    
    public void insertFront(int code, String destination) { //adds new node to the front of the list
        Bus newBus = new Bus(code, destination, head); //new node at the front = the node's link is the old head
        head = newBus; //new node becomes the head
        save();
    }
    
    public void insertLast(int code, String destination) { //adds new node to the end of the list
        Bus newBus = new Bus(code, destination, null);
        if (head == null) { //if list had no data, new node added becomes the head
            head = newBus;
        }
        else {
            Bus previous = head;
            while (previous.next != null) {
                previous = previous.next;
            }
            previous.next = newBus;
        }
        save();
    }
    
    public void insertAtPosition(int code, String destination, int position) { //adds new node to a certain position in the list
        Bus newBus = new Bus(code, destination, null);
        if (position == 0) { //position 0 means the front of the list
            insertFront(code, destination);
        }
        else {
            Bus previous = head;
            int count = 1;
            while (count <= position-1) {
                previous = previous.next;
                count++;
            }
            Bus current = previous.next;
            newBus.next = current;
            previous.next = newBus;
        }
        save();
    }
    
    public int length() { //determine how long the list is
        Bus cur = head;
        int c = 0;
        while (cur != null) {
            c++;
            cur = cur.next;
        }
        return c;
    }
    
    public boolean find(int code) { //find whether a bus with a certain code exists in the list
        Bus cur = head;
        while (cur != null) {
            if (cur.code == code) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }
    
    public boolean find(String destination) { //find whether a bus with a certain destination exists in the list
        Bus cur = head;
        while (cur != null) {
            if (cur.destination.equalsIgnoreCase(destination)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }
    
    public Bus getBus(int position){ //return the node that is in a certain position
        Bus current = head;
        for (int i = 0; i < position; i++)
            current = current.next;
        return current;
    }
}



class RequestList { //nodelist for requests
    class Request {
        String req;
        Request next;

        public Request(String req, Request next) {
            this.req = req;
            this.next = next;
        }

        @Override
        public String toString() {
            return req;
        }
    }
    
    
    
    private Request head;
    
    RequestList() {
        head = null;
    }
    
    RequestList(Request head) {
        this.head = head;
    }
    
    void save() {
        try {
            FileWriter fw = new FileWriter("Requests.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            Request cur = head;
            while(cur != null) {
                bw.write(cur + "\n");
                cur = cur.next;
            }
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    void load() {
        try {
            FileReader reader = new FileReader("Requests.txt");
            Scanner scan = new Scanner(reader);
            while (scan.hasNext()){
                String line = scan.nextLine();
                insertLast(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    void display() {
        try {
            FileReader fr = new FileReader("Requests.txt");
            BufferedReader br = new BufferedReader(fr);
            int i;
            while ((i = br.read()) != -1) {
                System.out.print((char)i);
            }
            br.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteFirst() {
        if (head != null) {
            Request cur = head;
            head = head.next;
            cur.next = null;
        }
        save();
    }
    
    public void deleteLast() {
        if (head != null) {
            if (head.next == null) {
                deleteFirst();
            }
            else {
                Request last = head;
                Request previoustolast = head;
                while (last.next != null) {
                    previoustolast = last;
                    last = last.next;
                }
                previoustolast.next = null;
            }
        }
        save();
    }
    
    public void deleteAtPosition(int position) {
        if (position == 0) {
            deleteFirst();
        }
        else {
            Request previous = head;
            int count = 1;
            while (count <= position-1) {
                previous = previous.next;
                count++;
            }
            Request cur = previous.next;
            previous.next = cur.next;
            cur.next = null;
        }
        save();
    }
    
    public void insertFront(String req) {
        Request newReq = new Request(req, head);
        head = newReq;
        save();
    }
    
    public void insertLast(String req) {
        Request newReq = new Request(req, null);
        if (head == null) {
            head = newReq;
        }
        else {
            Request previous = head;
            while (previous.next != null) {
                previous = previous.next;
            }
            previous.next = newReq;
        }
        save();
    }
    
    public void insertAtPosition(String req, int position) {
        Request newReq = new Request(req, null);
        if (position == 0) {
            insertFront(req);
        }
        else {
            Request previous = head;
            int count = 1;
            while (count <= position-1) {
                previous = previous.next;
                count++;
            }
            Request current = previous.next;
            newReq.next = current;
            previous.next = newReq;
        }
        save();
    }
    
    public int length() {
        Request cur = head;
        int c = 0;
        while (cur != null) {
            c++;
            cur = cur.next;
        }
        return c;
    }
    
    public Request getRequest(int position){
        Request current = head;
        for (int i = 0; i < position; i++)
            current = current.next;
        return current;
    }
}



class AdminController {
    private BusList buses;
    private RequestList requests;
    
    public AdminController(BusList buses, RequestList requests) {
        this.buses = buses;
        this.requests = requests;
    }
    
    public void admin_menu() { //prints the admin menu
        System.out.println("\n******************** ADMIN MENU ********************");
        System.out.println("1 •Add new bus and its information."); //in main: choosing this should prompt user to enter code
        System.out.println("2 •Delete the information of the Bus."); //in main: choosing this should prompt user to enter code
        System.out.println("3 •Update the requests or new requirements from the students.");
            //a. Update bus info (prompts user to enter code)
            //b. View requests
            //c. Delete requests
        System.out.println("4 •Generate the reports of the bus.");
        System.out.println("5. back");
        System.out.print("choice: ");
    }
    
    public void addBus(int code) { //add bus with code and destination
        if(buses.find(code)) { //if bus with code already exists
            System.out.println("Sorry, this bus code is already in the catalog");
        }
        else {
            System.out.print("Enter bus destination: ");
            String destination = pmubus.input.nextLine(); //input destination
            buses.insertLast(code, destination); //adds new bus to end of buslist
            System.out.println("Bus has been added to the catalog successfully");
        }
    }
    
    public void removeBus(int code) { //remove bus with a certain code
        if(buses.find(code)) { //see if the code exists in the list
            for (int i = 0; i < buses.length(); i++) { //loop to find the bus with that code
                if (buses.getBus(i).code == code) {
                    buses.deleteAtPosition(i);
                    break;
                }
            }
            System.out.println("Bus with code " + code + " has been removed from catalog successfully");
        }
        else {
            System.out.println("There is no bus in the catalog with code " + code);
        }
    }
    
    
    
    public void updateBusInfo(int code) { //we created the method called updateBusInfo to update the destination of a bus with a certain code
        if(buses.find(code)) { //see if the code exists in the list
            System.out.print("Enter new destination: "); //if the code is in the list, it will print "enter new destination" 
            String destination = pmubus.input.nextLine(); //to input a new destination
            
            for (int i = 0; i < buses.length(); i++) { //and here we created a loop to find the bus with that code
                if (buses.getBus(i).code == code) {
                    buses.deleteAtPosition(i); //delete the bus that was there
                    buses.insertAtPosition(code, destination, i); //replace it with a new bus with the same code but updated destination
                    break;
                }
            }
            System.out.println("Bus with code " + code + " has been updated"); //and here it will print "buss with code" and the code that was entered, has been updated
        }
        else {
            System.out.println("There is no bus in the catalog with code " + code); //otherwise, it will print "There is no bus in the catalog with the code that has been entered 
        }
    }
    
    public void viewRequests() { //for this method, method viewRequests displays student requests that are saved
        if (requests.length() == 0) { //if the requests lenght is zero that means that there is no requests
            System.out.println("No requests found"); //and it will print "no requests found"
        }
        else {
            System.out.println("---------------------------");
            requests.display(); //otherwise, the requests will display
            System.out.println("---------------------------");
        }
    }
    
    public void deleteRequests() { //and here we used deleteRequests method to chooses a request to remove
        if (requests.length() == 0) { //if the requests length is zero which means no requests, it will print "No requests found"
            System.out.println("No requests found");
        }
        else {
            for (int i = 0; i < requests.length(); i++) {
                System.out.println((i + 1) + ". " + requests.getRequest(i)); //and here we created a for loop to display the requests, numbered (or starting from 1)
            }

            System.out.print("Enter request number to delete: "); //and print "Enter request number to delete: "
            int reqnum = pmubus.input.nextInt(); //inputs which request to remove

            requests.deleteAtPosition((reqnum-1)); //after they enter the request nubmer to delete, the request will be deleted immediately
        }
    }
    
    public void generateBusReport() { //and now we created the method generateBusReport. to display all of the busses that have been added by the adminstrators, we used the generateBusReport method
        System.out.println("---------------------------");
        buses.display();
        System.out.println("---------------------------");
    }
}



class UserController {
    private BusList buses;
    private RequestList requests;

    public UserController(BusList buses, RequestList requests) {
        this.buses = buses;
        this.requests = requests;
    }
    
    public void user_menu() { //prints the user menu
        System.out.println("\n******************** USER MENU ********************");
        System.out.println("•Inquire about the specific bus including the information. (find)");
        System.out.println("\ta. By Bus Code");
        System.out.println("\tb. By Destination");
        System.out.println("1 •Request for a new item");
        System.out.println("2 •Complain if any");
        System.out.println("3. back");
        System.out.print("choice: ");
    }
    
    void inquiring (char choice) { //provides information about a specific bus
        if (choice == 'a' || choice == 'A') { //a. by bus code
            System.out.print("Enter bus code: ");
            int code = pmubus.input.nextInt(); //input code
            pmubus.input.nextLine(); //this is to read the rest of the line so to avoid any errors in using input.nextLine later
            
            if (buses.find(code)) { //see if the code exists in the list
                for (int i = 0; i < buses.length(); i++) { //loop to find the bus with that code
                    if (buses.getBus(i).code == code) {
                        System.out.println(buses.getBus(i)); //print bus info
                        break;
                    }
                }
            }
            else {
                System.out.println("There is no bus in the catalog with code " + code);
            }
        }
        else { //b. by destination
            System.out.print("Enter destination: ");
            String destination = pmubus.input.nextLine(); //input destination
            
            if (buses.find(destination)) { //see if bus with destination exists in the list
                for (int i = 0; i < buses.length(); i++) { //loop to find the bus with that destination
                    if (buses.getBus(i).destination.equalsIgnoreCase(destination)) {
                        System.out.println(buses.getBus(i)); //print bus info
                        break;
                    }
                }
            }
            else {
                System.out.println("There is no bus in the catalog with destination " + destination);
            }
        }
    }
    
    void request(String req) {
        requests.insertLast(req);
        System.out.println("Request has been submitted");
    }
    
    void complain() {
        System.out.println("Write your complaint");
        String complaint = pmubus.input.nextLine();
    }
}



public class pmubus { //main class
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        File busdata = new File("PMUBus.txt");
        File request = new File("Requests.txt");
        
        if(!busdata.exists()) { //sees if file "PMUBus" exists, and if not, it creates it
            try {
                FileWriter writebusdata = new FileWriter("PMUBus.txt");
            } catch (Exception e) {
                System.out.println("Error writing file");
            }
        }
        
        if(!request.exists()) { //sees if file "Requests" exists, and if not, it creates it
            try {
                FileWriter writerequest = new FileWriter("Requests.txt");
            } catch (Exception e) {
                System.out.println("Error writing file");
            }
        }
        
        BusList buses = new BusList();
        buses.load();
        RequestList requests = new RequestList();
        requests.load();
        AdminController admin = new AdminController(buses, requests);
        UserController user = new UserController(buses, requests);
        
        while (true) { //loop repeats until user exits the program
            main_menu();
            int choice = input.nextInt(); //input choice
            input.nextLine(); //this is to read the rest of the line so to avoid any errors in using input.nextLine later
            if (choice == 1){ //Choice: Administrator
                while (true) {
                    admin.admin_menu();
                    int choice1 = input.nextInt();
                    if (choice1 == 1) { //Add new bus and its information
                        System.out.print("Enter bus code: ");
                        int code = input.nextInt();
                        input.nextLine();
                        admin.addBus(code);
                    }
                    else if (choice1 == 2) { //Delete the information of the bus
                        System.out.print("Enter bus code: ");
                        int code = pmubus.input.nextInt();
                        admin.removeBus(code);
                    }
                    else if (choice1 == 3) { //Update the requests or new requirements from the students
                        System.out.println("a. Update bus information");
                        System.out.println("b. View requests");
                        System.out.println("c. Delete requests");
                        char pick = pmubus.input.next().charAt(0);
                        switch(pick) {
                            case 'a':
                            case 'A':
                                System.out.print("Enter bus code: ");
                                int code = pmubus.input.nextInt();
                                input.nextLine();
                                admin.updateBusInfo(code);
                                break;
                            case 'b':
                            case 'B':
                                admin.viewRequests();
                                break;
                            case 'c':
                            case 'C':
                                admin.deleteRequests();
                                break;
                        }
                    }
                    else if (choice1 == 4) { //Generate the reports of the bus
                        admin.generateBusReport();
                    }
                    else if (choice1 == 5) { //back
                        break;
                    }
                }
            }
            else if (choice == 2){ //Choice: User
                while (true) {
                    user.user_menu();
                    char choice2 = pmubus.input.nextLine().charAt(0);
                    
                    if (choice2 == 'a' || choice2 == 'A' || choice2 == 'b' || choice2 == 'B') //Inquire about the specific bus including info
                        user.inquiring(choice2);

                    else if (choice2 == '1') { //Request for a new item
                        System.out.print("Enter your request: ");
                        String req = input.nextLine();
                        user.request(req);
                    }

                    else if (choice2 == '2') //Complain if any
                        user.complain();

                    else if (choice2 == '3') //back
                        break;
                }
            }
            else if (choice == 3) //Choice: exit
                break; //breaks the loop so it no longer repeats
        }
        
        
        
    }
    
    public static void main_menu() {
        System.out.println("***************** PMU *****************");
        System.out.println("Please Select the user: ");
        System.out.println("\t1. Administrator");
        System.out.println("\t2. User");
        System.out.println("\t3. exit");
        System.out.print("choice: ");
    }
}