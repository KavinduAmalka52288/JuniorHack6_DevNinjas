package org.example;

import org.example.HostelService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HostelService hostelService = new HostelService();
        StudentService studentService = new StudentService();
        AllocationService allocationService = new AllocationService();

        while (true) {
            System.out.println("\n----------------------------------------------- Welcome to the Hostel Management System ---------------------------------");
            System.out.println("\n                               ---------------------------ABC university----------------------");
            System.out.println("[1] Manage Hostels");
            System.out.println("[2] Manage Students");
            System.out.println("[3] Manage Student Allocations");
            System.out.println("[0] Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> hostelService.manageHostels(scanner);
                case 2 -> studentService.manageStudents(scanner);
                case 3 -> allocationService.manageAllocations(scanner);
                case 0 -> {
                    System.out.println("Exiting... Thank you!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
