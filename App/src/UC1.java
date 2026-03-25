/**
 * Book My Stay App - Use Case 12
 * ------------------------------------------
 * Demonstrates data persistence and recovery using
 * serialization and file handling.
 *
 * @author Nikhilendra
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// -------- Reservation --------
class Reservation implements Serializable {
    String id;
    String guest;
    String roomType;

    public Reservation(String id, String guest, String roomType) {
        this.id = id;
        this.guest = guest;
        this.roomType = roomType;
    }
}

// -------- System State --------
class SystemState implements Serializable {
    HashMap<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(HashMap<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// -------- Persistence Service --------
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("Data saved successfully!");

        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("Data loaded successfully!");
            return state;

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh...");
            return null;
        }
    }
}

// -------- Main Class --------
public class UC12 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 12.0     ");
        System.out.println("=======================================\n");

        PersistenceService service = new PersistenceService();

        // Try to load previous state
        SystemState state = service.load();

        HashMap<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings = new ArrayList<>();
            bookings.add(new Reservation("RES-101", "Nikhil", "Single Room"));

        } else {
            // Restore state
            inventory = state.inventory;
            bookings = state.bookings;
        }

        // Display current state
        System.out.println("\nCurrent Inventory: " + inventory);

        System.out.println("\nBookings:");
        for (Reservation r : bookings) {
            System.out.println(r.id + " | " + r.guest + " | " + r.roomType);
        }

        // Save state before exit
        service.save(new SystemState(inventory, bookings));

        System.out.println("\nSystem recovery handled successfully!");
    }
}