import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {


    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {


        DataSource dataSource = new DataSource();

        if (!dataSource.open()) {

            System.out.println("Can't open data source");
            return;

        }

        HashMap<String, Integer> notes = dataSource.queryNotesForStudent("Alper Ahmedov");


        for (String course : notes.keySet()
        ) {
            System.out.println(course + " : " + notes.get(course));
        }


    }
}
