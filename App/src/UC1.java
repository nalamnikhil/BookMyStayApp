/**
 * Book My Stay App - Use Case 10
 * ------------------------------------------
 * Demonstrates booking cancellation and inventory rollback
 * using Stack (LIFO).
 *
 * @author Nikhilendra
 * @version 10.0
 */

import java.util.*;

// -------- Reservation --------
class Reservation {
    String reservationId;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// -------- Inventory --------
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 0);
        inventory.put("Double Room", 1);
    }

    public void increaseRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void display() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " → " + inventory.get(key));
        }
    }
}

// -------- Cancellation Service --------
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();
    private HashMap<String, Reservation> confirmedBookings = new HashMap<>();

    // Add confirmed booking (simulate previous step)
    public void addBooking(Reservation r) {
        confirmedBookings.put(r.reservationId, r);
    }

    // Cancel booking
    public void cancel(String reservationId, RoomInventory inventory) {

        System.out.println("\nCancelling Reservation: " + reservationId);

        // Validate existence
        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Error: Reservation not found!");
            return;
        }

        Reservation r = confirmedBookings.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.increaseRoom(r.roomType);

        // Remove booking
        confirmedBookings.remove(reservationId);

        System.out.println("Cancellation Successful!");
        System.out.println("Released Room ID: " + r.roomId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// -------- Main Class --------
public class UC10 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 10.0     ");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Simulate confirmed bookings
        service.addBooking(new Reservation("RES-101", "Single Room", "SI-1234"));
        service.addBooking(new Reservation("RES-102", "Double Room", "DO-5678"));

        // Cancel bookings
        service.cancel("RES-101", inventory);
        service.cancel("RES-999", inventory); // invalid

        // Show updated inventory
        inventory.display();

        // Show rollback stack
        service.showRollbackStack();

        System.out.println("\nSystem rollback handled successfully!");
    }
}