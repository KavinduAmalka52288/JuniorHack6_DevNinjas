package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StudentService {
    public void manageStudents(Scanner scanner) {
        System.out.println("\n Student Management.......");
        System.out.println("[1] Add Student");
        System.out.println("[2] Remove Students");
        System.out.println("[3] Update Student");
        System.out.println("[4] View Student Details");
        System.out.println("[0] Main menu");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> addStudent(scanner);
            //case 2 -> removeStudents(scanner);
            case 3 -> updateStudent(scanner);
            case 4 -> viewStudent(scanner);
        }
    }

    private void addStudent(Scanner scanner) {
        System.out.println("Add a student... ");
        System.out.print("Add the Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Add the studentId(eg: abc001): ");
        String studentId = scanner.nextLine();
        System.out.print("Add the age: ");
        Integer age = scanner.nextInt();
        System.out.print("Add the department: ");
        String department = scanner.nextLine();


        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Students (student_id, name, age, department) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(2, department);

            pstmt.executeUpdate();
            System.out.println("Successfully added the record of the student Id :"+studentId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudent(Scanner scanner) {
        System.out.println("\n view student details...");
        System.out.print("Enter the student Id : ");
        scanner.nextLine();
        String Id = scanner.nextLine();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Students WHERE student_id=Id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("student_name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Department: " + rs.getString("department"));

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateStudent(Scanner scanner) {
        System.out.println("\n Update Student...");
        System.out.print("Student Id : ");
        scanner.nextLine();
        String Id = scanner.nextLine();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Students WHERE student_id=Id";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id"));
                System.out.println("Name: " + rs.getString("student_name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Department: " + rs.getString("department"));

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Enter the attribute to update [1-name, 2-age , 3- department");
        Scanner scanner1 = new Scanner(System.in);
        Integer att = scanner1.nextInt();

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("UPDATE Students SET ");
            boolean needsComma = false;

        switch (att){
            case 1 :
                sql.append("name = ?");
                needsComma = true;
            case 2 :
                sql.append("age = ?");
                needsComma = true;

            case 3 :
                sql.append("department = ?");
                needsComma = true;

        }
            sql.append(" WHERE student_id = ?");


            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            int paramIndex = 1;


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


}
