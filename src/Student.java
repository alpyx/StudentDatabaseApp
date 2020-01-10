import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Student {

    private Scanner sc = new Scanner(System.in);
    private static List<String> courses;


    private String name;
    private int gradeYear;
    private String ID;
    private static int id = 1000;

    public Student() {

        courses = new ArrayList<>();

        System.out.println("Enter student name");

        this.name = sc.nextLine();

        System.out.println("Enter student year");
        this.gradeYear = sc.nextInt();


    }


    private void printAvailableCourses() {

        //TODO print available courses for the subject

    }

    public void enroll() {


        //TODO when creating a new student. select a subject


    }

    private void getEnrolledCourses() {


        //TODO list the courses a student is taking part in
    }


    public void payTuition() {


    }

    private boolean isPaid(String course) {

        //TODO check if a student has paid his/her tuition fee
        return false;
    }


}
