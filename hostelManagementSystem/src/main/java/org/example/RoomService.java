package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class RoomService {

    public void manageRooms(Scanner scanner) {
        System.out.println("\n--- Manage Rooms ---");
        System.out.println("1. Add Room");
        System.out.println("2. View Rooms");
        System.out.println("3. Update Room");
        System.out.println("4. Delete Room");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addRoom(scanner);
            case 2 -> viewRooms();
            case 3 -> updateRoom(scanner);
            case 4 -> deleteRoom(scanner);
            default -> System.out.println("Invalid choice.");
        }
    }

    private void addRoom(Scanner scanner) {
        System.out.print("Enter Hostel ID: ");
        int hostelId = scanner.nextInt();
        System.out.print("Enter Room Number: ");
        scanner.nextLine(); // Consume newline
        String roomNumber = scanner.nextLine();
        System.out.print("Enter Room Capacity: ");
        int capacity = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Rooms (hostel_id, room_number, capacity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, hostelId);
            pstmt.setString(2, roomNumber);
            pstmt.setInt(3, capacity);
            pstmt.executeUpdate();
            System.out.println("Room added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewRooms() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT r.room_id, r.room_number, r.capacity, h.hostel_id, h.hostel_name
                FROM Rooms r
                JOIN Hostels h ON r.hostel_id = h.hostel_id
                """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Room List ---");
            while (rs.next()) {
                System.out.println("Room ID: " + rs.getInt("room_id"));
                System.out.println("Room Number: " + rs.getString("room_number"));
                System.out.println("Capacity: " + rs.getInt("capacity"));
                System.out.println("Hostel ID: " + rs.getInt("hostel_id"));
                System.out.println("Hostel Name: " + rs.getString("hostel_name"));
                System.out.println("-----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateRoom(Scanner scanner) {
        System.out.print("Enter Room ID to update: ");
        int roomId = scanner.nextInt();
        System.out.print("Enter New Room Number (leave blank to keep unchanged): ");
        scanner.nextLine(); // Consume newline
        String newRoomNumber = scanner.nextLine();
        System.out.print("Enter New Capacity (enter -1 to keep unchanged): ");
        int newCapacity = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("UPDATE Rooms SET ");
            boolean needsComma = false;

            if (!newRoomNumber.isBlank()) {
                sql.append("room_number = ?");
                needsComma = true;
            }
            if (newCapacity != -1) {
                if (needsComma) sql.append(", ");
                sql.append("capacity = ?");
            }
            sql.append(" WHERE room_id = ?");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (!newRoomNumber.isBlank()) pstmt.setString(paramIndex++, newRoomNumber);
            if (newCapacity != -1) pstmt.setInt(paramIndex++, newCapacity);
            pstmt.setInt(paramIndex, roomId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Room updated successfully.");
            } else {
                System.out.println("No room found with the provided ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteRoom(Scanner scanner) {
        System.out.print("Enter Room ID to delete: ");
        int roomId = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Rooms WHERE room_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roomId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Room deleted successfully.");
            } else {
                System.out.println("No room found with the provided ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
