/**
 * Book My Stay App - Use Case 4
 * ------------------------------------------
 * Demonstrates room search and availability check
 * using read-only access to inventory.
 *
 * @author Nikhilendra
 * @version 4.0
 */

import java.util.*;

// -------- Room Abstract Class --------
abstract class Room {
    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
    }

    public String getType() {
        return type;
    }
}

// -------- Concrete Rooms --------
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// -------- Inventory (Read-only access here) --------
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 0); // Not available
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

// -------- Search Service --------
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, List<Room> rooms) {

        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());

            // Filter only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available Count: " + available + "\n");
            }
        }
    }
}

// -------- Main Class --------
public class UC4 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 4.0      ");
        System.out.println("=======================================\n");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Room objects (domain)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Search Service
        RoomSearchService search = new RoomSearchService();

        // Perform search (READ-ONLY)
        search.searchAvailableRooms(inventory, rooms);

        System.out.println("Search completed successfully!");
    }
}