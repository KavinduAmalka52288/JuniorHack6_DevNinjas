package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AllocationService {

    public void manageAllocations(Scanner scanner) {
        System.out.println("\n--- Manage Allocations ---");
        System.out.println("1. Allocate Room");
        System.out.println("2. Deallocate Room");
        System.out.println("3. View Allocations");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> allocateRoom(scanner);
            case 2 -> deallocateRoom(scanner);
            case 3 -> viewAllocations();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void allocateRoom(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter Hostel ID: ");
        int hostelId = scanner.nextInt();
        System.out.print("Enter Room ID: ");
        int roomId = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if the room is already full
            String roomCapacityQuery = "SELECT capacity FROM Rooms WHERE room_id = ?";
            PreparedStatement capacityStmt = conn.prepareStatement(roomCapacityQuery);
            capacityStmt.setInt(1, roomId);
            ResultSet capacityResult = capacityStmt.executeQuery();

            if (!capacityResult.next()) {
                System.out.println("Room does not exist.");
                return;
            }

            int capacity = capacityResult.getInt("capacity");

            // Check the number of students already allocated to the room
            String allocationCountQuery = "SELECT COUNT(*) AS allocated FROM Allocations WHERE room_id = ?";
            PreparedStatement countStmt = conn.prepareStatement(allocationCountQuery);
            countStmt.setInt(1, roomId);
            ResultSet countResult = countStmt.executeQuery();

            countResult.next();
            int allocated = countResult.getInt("allocated");

            if (allocated >= capacity) {
                System.out.println("Room is already full.");
                return;
            }

            // Allocate the student to the room
            String allocateQuery = "INSERT INTO Allocations (student_id, hostel_id, room_id) VALUES (?, ?, ?)";
            PreparedStatement allocateStmt = conn.prepareStatement(allocateQuery);
            allocateStmt.setInt(1, studentId);
            allocateStmt.setInt(2, hostelId);
            allocateStmt.setInt(3, roomId);
            allocateStmt.executeUpdate();

            System.out.println("Room allocated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deallocateRoom(Scanner scanner) {
        System.out.print("Enter Student ID to deallocate: ");
        int studentId = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Allocations WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deallocated successfully.");
            } else {
                System.out.println("No allocation found for the provided Student ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllocations() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT a.student_id, s.student_name, a.hostel_id, h.hostel_name, a.room_id, r.room_number
                FROM Allocations a
                JOIN Students s ON a.student_id = s.student_id
                JOIN Hostels h ON a.hostel_id = h.hostel_id
                JOIN Rooms r ON a.room_id = r.room_id
                """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Allocations ---");
            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Student Name: " + rs.getString("student_name"));
                System.out.println("Hostel ID: " + rs.getInt("hostel_id"));
                System.out.println("Hostel Name: " + rs.getString("hostel_name"));
                System.out.println("Room ID: " + rs.getInt("room_id"));
                System.out.println("Room Number: " + rs.getString("room_number"));
                System.out.println("-----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
