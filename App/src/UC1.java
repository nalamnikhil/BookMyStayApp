/**
 * Book My Stay App - Use Case 7
 * ------------------------------------------
 * Demonstrates add-on service selection for reservations
 * using Map and List.
 *
 * @author Nikhilendra
 * @version 7.0
 */

import java.util.*;

// -------- Add-On Service --------
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println(name + " - ₹" + cost);
    }
}

// -------- Service Manager --------
class AddOnServiceManager {

    // Map → ReservationID → List of Services
    private HashMap<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getName() + " to Reservation " + reservationId);
    }

    // Display services
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// -------- Main Class --------
public class UC7 {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   Book My Stay App - Version 7.0      ");
        System.out.println("=======================================\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES-101";

        // Add services
        manager.addService(reservationId, new Service("Breakfast", 200));
        manager.addService(reservationId, new Service("Airport Pickup", 500));
        manager.addService(reservationId, new Service("Extra Bed", 300));

        // Display services
        manager.displayServices(reservationId);

        // Total cost
        double total = manager.calculateTotalCost(reservationId);
        System.out.println("\nTotal Add-On Cost: ₹" + total);

        System.out.println("\nAdd-on services processed successfully!");
    }
}