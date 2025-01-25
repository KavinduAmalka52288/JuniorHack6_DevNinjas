package org.example;

import java.security.PublicKey;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        System.out.println("------------------------------------- Welcome to the Hostel Management System---------------------");
        System.out.println("                               ----------------ABC university------------------------------");



        System.out.println("Please enter the number of the action you want to proceed...");

        System.out.println("[1] manage Hostels");
        System.out.println("[2] manage students");
        System.out.println("[3] manage Student Allocation");
        System.out.println("[0] Exit");


        Scanner input = new Scanner(System.in);
        Integer input1 = input.nextInt();

            switch (input1){
                case 1 :
                    System.out.println("Hosetl Management....");


                    break;

                case 2:
                    System.out.println("Student Management");



            }




        }

        }

