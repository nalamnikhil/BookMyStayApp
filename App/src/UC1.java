/**
 * Book My Stay App - Use Case 8
 * ------------------------------------------
 * Demonstrates booking history tracking and reporting
 * using List and reporting service.
 *
 * @author Nikhilendra
 * @version 8.0
 */

import java.util.*;

// -------- Reservation --------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// -------- Booking History --------
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings
    public List<Reservation> getAllBookings() {
        return history;
    }
}

// -------- Report Service --------
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> list) {
        System.out.println("\nAll Booking History:");
        for (Reservation r : list) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> list) {
        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : list) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("\nBooking Summary Report:");
        for (String type : countMap.keySet()) {
            System.out.println(type + " → " + countMap.get(type) + " bookings");
        }
    }
}

// -------- Main Class --------
public class UC8 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 8.0      ");
        System.out.println("=======================================\n");

        BookingHistory history = new BookingHistory();
        BookingReportService report = new BookingReportService();

        // Add confirmed bookings
        history.addReservation(new Reservation("RES-101", "Nikhil", "Single Room"));
        history.addReservation(new Reservation("RES-102", "Rahul", "Double Room"));
        history.addReservation(new Reservation("RES-103", "Anjali", "Single Room"));

        // Show history
        report.showAllBookings(history.getAllBookings());

        // Generate report
        report.generateSummary(history.getAllBookings());

        System.out.println("\nReporting completed successfully!");
    }
}