/**
 * Book My Stay App - Use Case 11
 * ------------------------------------------
 * Demonstrates concurrent booking with thread safety
 * using synchronized methods.
 *
 * @author Nikhilendra
 * @version 11.0
 */

import java.util.*;

// -------- Reservation --------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// -------- Shared Inventory --------
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

    // Synchronized method (critical section)
    public synchronized boolean bookRoom(String type, String guest) {

        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            System.out.println(guest + " booking in progress...");

            // Simulate delay
            try { Thread.sleep(100); } catch (Exception e) {}

            inventory.put(type, available - 1);

            System.out.println("Booking SUCCESS for " + guest +
                    " | Remaining: " + (available - 1));
            return true;
        } else {
            System.out.println("Booking FAILED for " + guest + " (No rooms)");
            return false;
        }
    }

    public void display() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// -------- Booking Task (Thread) --------
class BookingTask extends Thread {

    private RoomInventory inventory;
    private Reservation reservation;

    public BookingTask(RoomInventory inventory, Reservation reservation) {
        this.inventory = inventory;
        this.reservation = reservation;
    }

    public void run() {
        inventory.bookRoom(reservation.roomType, reservation.guestName);
    }
}

// -------- Main Class --------
public class UC11 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 11.0     ");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();

        // Simulate multiple users
        BookingTask t1 = new BookingTask(inventory, new Reservation("Nikhil", "Single Room"));
        BookingTask t2 = new BookingTask(inventory, new Reservation("Rahul", "Single Room"));
        BookingTask t3 = new BookingTask(inventory, new Reservation("Anjali", "Single Room"));

        // Start threads (concurrent requests)
        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (Exception e) {}

        // Final state
        inventory.display();

        System.out.println("\nConcurrent booking handled safely!");
    }
}