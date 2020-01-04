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

        int i = 1;
        for (String course : courses
        ) {

            System.out.println(course);


        }


    }

    public void enroll() {


        System.out.println("Choose a subject to enroll.");
        printAvailableCourses();
        int choice = sc.nextInt();


        String key = availableCourses.get(choice);

        int i = 1;

        while (choice != 0) {

            if (!enrolledCourses.containsKey(key)) {

                enrolledCourses.put(key, 600.0);
                i++;
                System.out.println("Course successfully enrolled!");

            } else {
                System.out.println("You have already enrolled this course! Try enrolling another one!");
            }


            if (enrolledCourses.size() == availableCourses.size()) {
                break;
            }
            System.out.println("Choose course to enroll. (0 to quit)");
            printAvailableCourses();
            choice = sc.nextInt();
            key = availableCourses.get(choice);


        }


    }

    private void getEnrolledCourses() {


        System.out.println("Courses you enrolled in:");

        int i = 1;
        for (String key : enrolledCourses.keySet()

        ) {

            System.out.println("\t" + i + ". " + key);
            i++;
        }
    }


    public void payTuition() {

        getEnrolledCourses();


        System.out.println("Choose for which course you wish to pay tuition. Choose 0 to quit.");

        int key = sc.nextInt();

        String course = availableCourses.get(key);
        System.out.println("The remaining amount that is to be paid is: " + enrolledCourses.get(course));

        System.out.println("Please enter the amount you wish to pay");
        double money = sc.nextDouble();


        while (key != 0) {

            if (enrolledCourses.containsKey(course) && enrolledCourses.get(course) >= money) {


                double remainingFee = enrolledCourses.get(course);

                enrolledCourses.put(course, remainingFee - money);
                System.out.println("Your payment was successful. Thank you!");


            } else {
                System.out.println("You have already payed the total fee for this course. Thank you!!");

            }

            System.out.println("Choose for which course you wish to pay tuition. Choose 0 to quit.");
            getEnrolledCourses();


            sc.nextLine();
            key = sc.nextInt();

            if (key == 0) {
                break;

            }
            course = availableCourses.get(key);


            if (!isPaid(course)) {
                System.out.println("The remaining amount that is to be paid is: " + enrolledCourses.get(course));


                System.out.println("Please enter the amount you wish to pay");
                money = sc.nextDouble();
            }


        }


    }

    private boolean isPaid(String course) {


        if (enrolledCourses.get(course) == 0) {

            return true;

        }
        return false;
    }

    @Override
    public String toString() {

        return "Name: " + name + "\n" +
                "ID: " + ID + "\n" +
                "Enrolled courses: " + enrolledCourses.keySet().toString() + "\n";
    }


}
