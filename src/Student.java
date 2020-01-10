import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {


    private String name;
    private String subject;
    private int semester;

    private List<String> currentCourses;


    public Student(String name, String subject, int semester) {

        this.name = name;
        this.subject = subject;
        this.semester = semester;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
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
