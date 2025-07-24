import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    boolean isBooked;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - " + (isBooked ? "Booked" : "Available");
    }
}

class Reservation {
    String customerName;
    int roomNumber;
    String category;
    double payment;

    Reservation(String customerName, int roomNumber, String category, double payment) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Reservation for " + customerName + " | Room: " + roomNumber + " (" + category + ") | Paid: $" + payment;
    }
}

public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeRooms();
        loadBookingsFromFile();

        int choice;
        do {
            System.out.println("\n🏨 Hotel Reservation System Menu:");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. View All Reservations");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    displayAvailableRooms();
                    break;
                case 2:
                    bookRoom(scanner);
                    break;
                case 3:
                    cancelReservation(scanner);
                    break;
                case 4:
                    displayReservations();
                    break;
                case 5:
                    System.out.println("👋 Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        } while (choice != 5);
    }

    static void initializeRooms() {
        // Create sample rooms
        for (int i = 101; i <= 105; i++) {
            rooms.add(new Room(i, "Standard"));
        }
        for (int i = 201; i <= 203; i++) {
            rooms.add(new Room(i, "Deluxe"));
        }
        for (int i = 301; i <= 302; i++) {
            rooms.add(new Room(i, "Suite"));
        }
    }

    static void displayAvailableRooms() {
        System.out.println("\n🛏️ Available Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked) {
                System.out.println(room);
            }
        }
    }

    static void bookRoom(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter room category (Standard/Deluxe/Suite): ");
        String category = scanner.nextLine().trim();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (!room.isBooked && room.category.equalsIgnoreCase(category)) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("❌ No available rooms in that category.");
            return;
        }

        double price = getPriceForCategory(category);
        System.out.printf("💵 Price for %s: $%.2f\n", category, price);
        System.out.print("Confirm booking and payment? (yes/no): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            selectedRoom.isBooked = true;
            Reservation reservation = new Reservation(name, selectedRoom.roomNumber, selectedRoom.category, price);
            reservations.add(reservation);
            saveBookingsToFile();
            System.out.println("✅ Booking successful!\n" + reservation);
        } else {
            System.out.println("❌ Booking cancelled.");
        }
    }

    static double getPriceForCategory(String category) {
        switch (category.toLowerCase()) {
            case "standard": return 100.0;
            case "deluxe": return 200.0;
            case "suite": return 300.0;
            default: return 0;
        }
    }

    static void cancelReservation(Scanner scanner) {
        System.out.print("Enter your name to cancel booking: ");
        String name = scanner.nextLine();

        Reservation toRemove = null;
        for (Reservation r : reservations) {
            if (r.customerName.equalsIgnoreCase(name)) {
                toRemove = r;
                break;
            }
        }

        if (toRemove != null) {
            reservations.remove(toRemove);
            for (Room room : rooms) {
                if (room.roomNumber == toRemove.roomNumber) {
                    room.isBooked = false;
                    break;
                }
            }
            saveBookingsToFile();
            System.out.println("✅ Booking cancelled successfully.");
        } else {
            System.out.println("❌ No booking found under that name.");
        }
    }

    static void displayReservations() {
        System.out.println("\n📄 Current Reservations:");
        if (reservations.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    static void saveBookingsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Reservation r : reservations) {
                writer.println(r.customerName + "," + r.roomNumber + "," + r.category + "," + r.payment);
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving bookings: " + e.getMessage());
        }
    }

    static void loadBookingsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    int roomNumber = Integer.parseInt(parts[1]);
                    String category = parts[2];
                    double payment = Double.parseDouble(parts[3]);

                    reservations.add(new Reservation(name, roomNumber, category, payment));

                    for (Room room : rooms) {
                        if (room.roomNumber == roomNumber) {
                            room.isBooked = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading bookings: " + e.getMessage());
        }
    }
}
