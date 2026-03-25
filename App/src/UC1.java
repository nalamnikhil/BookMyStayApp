/**
 * Book My Stay App - Use Case 9
 * ------------------------------------------
 * Demonstrates error handling and validation using
 * custom exceptions and fail-fast design.
 *
 * @author Nikhilendra
 * @version 9.0
 */

import java.util.*;

// -------- Custom Exception --------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------- Inventory --------
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 0);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        inventory.put(type, available - 1);
    }
}

// -------- Validator --------
class BookingValidator {

    public void validate(String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Check valid room type
        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}

// -------- Main Class --------
public class UC9 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 9.0      ");
        System.out.println("=======================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingValidator validator = new BookingValidator();

        String[] testRequests = {
                "Single Room",
                "Double Room",   // no availability
                "Suite Room"     // invalid type
        };

        for (String roomType : testRequests) {

            System.out.println("Booking request for: " + roomType);

            try {
                // Validate input
                validator.validate(roomType, inventory);

                // Process booking
                inventory.reduceRoom(roomType);

                System.out.println("Booking Successful!\n");

            } catch (InvalidBookingException e) {
                // Graceful failure
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }

        System.out.println("System continues running safely!");
    }
}