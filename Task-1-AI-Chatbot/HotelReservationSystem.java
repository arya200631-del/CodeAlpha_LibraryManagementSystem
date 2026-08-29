import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean isBooked;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.isBooked = false;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { this.isBooked = booked; }

    @Override
    public String toString() {
        return String.format("Room #%d | Category: %-10s | Rate: $%.2f/night | Status: %s",
                roomNumber, category, pricePerNight, (isBooked ? "BOOKED" : "AVAILABLE"));
    }
}

class Reservation {
    private String reservationId;
    private String guestName;
    private Room room;
    private int nights;
    private double totalAmount;

    public Reservation(String guestName, Room room, int nights) {
        this.reservationId = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
        this.totalAmount = room.getPricePerNight() * nights;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public Room getRoom() { return room; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return String.format("ID: %s | Guest: %s | Room: %d (%s) | Nights: %d | Total: $%.2f",
                reservationId, guestName, room.getRoomNumber(), room.getCategory(), nights, totalAmount);
    }
}

public class HotelReservationSystem {
    private static final List<Room> rooms = new ArrayList<>();
    private static final Map<String, Reservation> reservations = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();

        while (true) {
            System.out.println("\n===========================================");
            System.out.println("  🏨 GRAND HOTEL RESERVATION MANAGEMENT    ");
            System.out.println("===========================================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Search Rooms by Category");
            System.out.println("3. Make a Reservation");
            System.out.println("4. Cancel a Reservation");
            System.out.println("5. View All Active Bookings");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayRooms(false);
                    break;
                case "2":
                    searchByCategory();
                    break;
                case "3":
                    makeReservation();
                    break;
                case "4":
                    cancelReservation();
                    break;
                case "5":
                    viewBookings();
                    break;
                case "6":
                    System.out.println("Thank you for using Grand Hotel Management System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose between 1 and 6.");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 80.0));
        rooms.add(new Room(102, "Standard", 80.0));
        rooms.add(new Room(201, "Deluxe", 150.0));
        rooms.add(new Room(202, "Deluxe", 150.0));
        rooms.add(new Room(301, "Suite", 280.0));
        rooms.add(new Room(302, "Suite", 280.0));
    }

    private static void displayRooms(boolean showAll) {
        System.out.println("\n--- Current Room Inventory ---");
        for (Room room : rooms) {
            if (showAll || !room.isBooked()) {
                System.out.println(room);
            }
        }
    }

    private static void searchByCategory() {
        System.out.print("\nEnter category to search (Standard / Deluxe / Suite): ");
        String cat = scanner.nextLine().trim();

        System.out.println("\nAvailable rooms in " + cat + ":");
        boolean found = false;
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(cat) && !room.isBooked()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms found under '" + cat + "'.");
        }
    }

    private static void makeReservation() {
        System.out.println("\n--- Make a Reservation ---");
        displayRooms(false);

        System.out.print("\nEnter Room Number to book: ");
        int roomNum;
        try {
            roomNum = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number format.");
            return;
        }

        Room selectedRoom = null;
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNum && !r.isBooked()) {
                selectedRoom = r;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room " + roomNum + " is either invalid or already booked.");
            return;
        }

        System.out.print("Enter Guest Full Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Number of Nights: ");
        int nights;
        try {
            nights = Integer.parseInt(scanner.nextLine().trim());
            if (nights <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Nights must be a positive integer.");
            return;
        }

        double total = selectedRoom.getPricePerNight() * nights;
        System.out.printf("Total charge: $%.2f. Confirm payment (Y/N)? ", total);
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("y") || confirm.equalsIgnoreCase("yes")) {
            selectedRoom.setBooked(true);
            Reservation res = new Reservation(name, selectedRoom, nights);
            reservations.put(res.getReservationId(), res);

            System.out.println("\n Payment Successful!");
            System.out.println("Booking Confirmed: " + res);
        } else {
            System.out.println("Payment cancelled. Booking aborted.");
        }
    }

    private static void cancelReservation() {
        System.out.print("\nEnter Reservation ID to cancel: ");
        String resId = scanner.nextLine().trim();

        Reservation res = reservations.get(resId);
        if (res != null) {
            res.getRoom().setBooked(false);
            reservations.remove(resId);
            System.out.printf("Reservation %s for %s cancelled successfully. Refund processed: $%.2f\n",
                    resId, res.getGuestName(), res.getTotalAmount());
        } else {
            System.out.println("Reservation ID not found.");
        }
    }

    private static void viewBookings() {
        System.out.println("\n--- Active Bookings ---");
        if (reservations.isEmpty()) {
            System.out.println("No active reservations.");
            return;
        }
        for (Reservation res : reservations.values()) {
            System.out.println(res);
        }
    }
}
