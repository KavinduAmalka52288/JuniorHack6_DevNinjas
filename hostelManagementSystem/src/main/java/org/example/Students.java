package org.example;

import java.util.Scanner;

public class Students {

    public String studentId;
    public String name;
    public Integer age;
    public String department;

    public Students(String student_id, String name, Integer age, String department) {
        this.studentId = student_id;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    public void studentManagement(){
        System.out.println("[1] Add student");
        System.out.println("[2] Remove Student");
        System.out.println("[3] Update student");
        System.out.println("[4] View student details");
        System.out.println("[0] Main menu");

        Scanner stu = new Scanner(System.in);



    }





    public void addStudent(String name, String studentId, Integer age, String department){
        Scanner add = new Scanner(System.in);
        System.out.println("Add the name :");
        name = add.nextLine();

        System.out.println("Add the studentId(eg: abc001) :");
        studentId = add.nextLine();

        System.out.println("Add the age :");
        age = add.nextInt();

        System.out.println("Add the department :");
        department = add.nextLine();







    }


}
