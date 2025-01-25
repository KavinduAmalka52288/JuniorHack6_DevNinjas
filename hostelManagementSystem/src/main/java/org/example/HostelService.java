package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HostelService {
    public void manageHostels(Scanner scanner) {
        System.out.println("\n--- Manage Hostels ---");
        System.out.println("[1] Add Hostel");
        System.out.println("[2] Update Hostel");
        System.out.println("[3] View Hostels");
        System.out.println("[4] Room Management");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addHostel(scanner);
            case 2 -> updateHostel(scanner);
            case 3 -> viewHostels();
            case 4 -> RoomManagement(scanner);
            default -> System.out.println("Invalid choice.");
        }
    }

    private void addHostel(Scanner scanner) {
        System.out.println("Enter Hostel ID:");
        scanner.nextLine();
        String hostelID= scanner.nextLine();
        System.out.print("Enter Hostel Name: ");
        scanner.nextLine(); // Consume newline
        String hostelName = scanner.nextLine();
        System.out.print("Enter Total Rooms: ");
        int number_of_rooms = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Hostels (hostel_id,hostel_name, number_of_rooms) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,hostelID);
            pstmt.setString(2, hostelName);
            pstmt.setInt(3, number_of_rooms);
            pstmt.executeUpdate();
            System.out.println("Hostel added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateHostel(Scanner scanner) {
        System.out.print("Enter Hostel ID to update: ");
        int hostelId = scanner.nextInt();
        System.out.print("Enter New Hostel Name : ");
        scanner.nextLine(); // Consume newline
        String newHostelName = scanner.nextLine();
        System.out.print("Enter New Number of rooms : ");
        String newNumberOfRooms = scanner.nextLine();


        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("UPDATE Hostels SET ");
            boolean needsComma = false;

            if (!newHostelName.isBlank()) {
                sql.append("hostel_name = ?");
                needsComma = true;
            }

            if (!newNumberOfRooms.isBlank()) {
                if (needsComma) sql.append(", ");
                sql.append("number_of_rooms = ?");
            }
            sql.append(" WHERE hostel_id = ?");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (!newHostelName.isBlank()) pstmt.setString(paramIndex++, newHostelName);
            if (!newNumberOfRooms.isBlank()) pstmt.setString(paramIndex++, newNumberOfRooms);
            pstmt.setInt(paramIndex, hostelId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Hostel updated successfully.");
            } else {
                System.out.println("No hostel found with the provided ID.");
            }
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
                System.out.println("Total Rooms: " + rs.getInt("number_of_rooms"));
                System.out.println("-----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void RoomManagement(Scanner scanner){

    }
}
