import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {


    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) throws SQLException {


        DataSource dataSource = new DataSource();

        if (!dataSource.open()) {

            System.out.println("Can't open data source");
            return;

        }

        HashMap<String, Integer> notes = dataSource.queryNotesForStudent("Alper Ahmedov");


//        if (notes != null) {
//            for (String course : notes.keySet()
//            ) {
//                System.out.println(course + " : " + notes.get(course));
//            }
//        }


        List<Student> students = dataSource.queryStudents();


//        for (Student student : students) {
//
//            System.out.println(student.getName());
//        }

        List<String> subjects = dataSource.querySubjects();


//        for (String subject : subjects
//        ) {
//            System.out.println(subject);
//        }

        double average = dataSource.queryAverageNoteForSemester(201216001, 1);
//        System.out.println(average);
//
        int note = dataSource.queryNoteForCourse(201216001, "Deutsch");
//        System.out.println(note);

//        dataSource.insertNote(201216001, 4, 4);

//        List<Course> coursesForSubject = dataSource.queryCoursesForSubject("Informatik");


//        for (Course course : coursesForSubject
//        ) {
//
//            System.out.println(course.getName());
//        }

//        System.out.println(dataSource.updateNote(2, 201216001, "Höhere Mathematik P"));

//        System.out.println(dataSource.queryCourse("Programmierung und Computeranwendung"));

//        System.out.println(dataSource.insertSubject("asd"));

//        System.out.println(dataSource.queryCourse("Sport"));
//        System.out.println(dataSource.querySubject("Mechatronik"));


//        System.out.println(dataSource.insertCourse("JAVA 8", 1, "Informatik"));

        System.out.println(dataSource.updateNote(5, 201216001, "Sport"));


        dataSource.close();

    }
}
