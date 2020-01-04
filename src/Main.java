import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("How many students do we have?");
        int number = sc.nextInt();

        for (int i = 0; i < number; i++) {

            Student stud1 = new Student();
            stud1.enroll();
            stud1.payTuition();
            System.out.println(stud1.toString());
            students.add(stud1);

        }


    }
}
