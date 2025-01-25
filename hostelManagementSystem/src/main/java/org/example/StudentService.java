package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StudentService {
    public void manageStudents(Scanner scanner) {
        System.out.println("\n--- Manage Students ---");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addStudent(scanner);
            case 2 -> viewStudents();
            case 3 -> updateStudent(scanner);
            case 4 -> deleteStudent(scanner);
            default -> System.out.println("Invalid choice.");
        }
    }

    private void addStudent(Scanner scanner) {
        System.out.print("Enter Student Name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Students (student_name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudents() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Students";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("student_name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Hostel ID: " + rs.getInt("hostel_id"));
                System.out.println("Room ID: " + rs.getInt("room_id"));
                System.out.println("-----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateStudent(Scanner scanner) {
        System.out.print("Enter Student ID to update: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter New Name (leave blank to keep unchanged): ");
        scanner.nextLine(); // Consume newline
        String newName = scanner.nextLine();
        System.out.print("Enter New Email (leave blank to keep unchanged): ");
        String newEmail = scanner.nextLine();
        System.out.print("Enter New Phone (leave blank to keep unchanged): ");
        String newPhone = scanner.nextLine();

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("UPDATE Students SET ");
            boolean needsComma = false;

            if (!newName.isBlank()) {
                sql.append("student_name = ?");
                needsComma = true;
            }
            if (!newEmail.isBlank()) {
                if (needsComma) sql.append(", ");
                sql.append("email = ?");
                needsComma = true;
            }
            if (!newPhone.isBlank()) {
                if (needsComma) sql.append(", ");
                sql.append("phone = ?");
            }
            sql.append(" WHERE student_id = ?");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (!newName.isBlank()) pstmt.setString(paramIndex++, newName);
            if (!newEmail.isBlank()) pstmt.setString(paramIndex++, newEmail);
            if (!newPhone.isBlank()) pstmt.setString(paramIndex++, newPhone);
            pstmt.setInt(paramIndex, studentId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("No student found with the provided ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteStudent(Scanner scanner) {
        System.out.print("Enter Student ID to delete: ");
        int studentId = scanner.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Students WHERE student_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with the provided ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
