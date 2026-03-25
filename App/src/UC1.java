/**
 * Book My Stay App - Use Case 5
 * ------------------------------------------
 * Demonstrates booking request handling using Queue (FIFO).
 *
 * @author Nikhilendra
 * @version 5.0
 */

import java.util.*;

// -------- Reservation Class --------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// -------- Booking Queue --------
class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Request added for " + r.getGuestName());
    }

    // View all requests
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue:");

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Get next request (FIFO)
    public Reservation getNextRequest() {
        return queue.peek(); // no removal (no allocation yet)
    }
}

// -------- Main Class --------
public class UC5 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 5.0      ");
        System.out.println("=======================================\n");

        BookingQueue bookingQueue = new BookingQueue();

        // Guests send booking requests
        bookingQueue.addRequest(new Reservation("Nikhil", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double Room"));
        bookingQueue.addRequest(new Reservation("Anjali", "Suite Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        // Show next request (without removing)
        System.out.println("\nNext Request to Process:");
        Reservation next = bookingQueue.getNextRequest();
        if (next != null) {
            next.display();
        }

        System.out.println("\nAll requests stored in arrival order!");
    }
}