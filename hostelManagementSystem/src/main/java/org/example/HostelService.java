package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HostelService {
    public void manageHostels(Scanner scanner) {
        System.out.println("\n--- Manage Hostels ---");
        System.out.println("1. Add Hostel");
        System.out.println("2. View Hostels");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addHostel(scanner);
            case 2 -> viewHostels();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void addHostel(Scanner scanner) {
        System.out.print("Enter Hostel Name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter Total Rooms: ");
        int totalRooms = scanner.nextInt();
        System.out.print("Enter Capacity: ");
        int capacity = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Hostels (hostel_name, total_rooms, capacity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, totalRooms);
            pstmt.setInt(3, capacity);
            pstmt.executeUpdate();
            System.out.println("Hostel added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewHostels() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Hostels";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Hostel List ---");
            while (rs.next()) {
                System.out.println("Hostel ID: " + rs.getInt("hostel_id"));
                System.out.println("Name: " + rs.getString("hostel_name"));
                System.out.println("Total Rooms: " + rs.getInt("total_rooms"));
                System.out.println("Capacity: " + rs.getInt("capacity"));
                System.out.println("-----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
