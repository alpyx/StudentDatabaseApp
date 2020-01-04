import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSource {

    public static final String DB_NAME = "uni.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:home/alper/" + DB_NAME;

    public static final String TABLE_SUBJECTS = "subjects";
    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_SUBJECT_NAME = "name";


    public static final String TABLE_COURSES = "courses";
    public static final String COLUMN_COURSE_ID = "_id";
    public static final String COLUMN_COURSE_NAME = "name";
    public static final String COLUMN_COURSE_SEMESTER = "semester";
    public static final String COLUMN_COURSE_SUBJECT = "subject";


// SELECT students.name, courses.name FROM students INNER JOIN subjects ON students.subject = subjects._id  INNER JOIN courses ON subjects._id = courses.subject ORDER BY students._id;
    //SELECT students.name, students.semester, subjects.name FROM students INNER JOIN subjects ON students.subject = subjects._id ORDER BY students._id
//INSERT INTO students(name, subject, semester) VALUES("Petar Ivanov", 1, 1);


    private Connection conn;

    private PreparedStatement queryStudent;
    private PreparedStatement querySubject;
    private PreparedStatement queryCourses;

    private PreparedStatement insertIntoStudents;
    private PreparedStatement insertIntoSubjects;
    private PreparedStatement insertIntoCourses;


    public boolean open() {

        try {

            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;

        } catch (SQLException e) {

            System.out.println("Couldn't connect do database " + e.getMessage());
            return false;
        }


    }

    public boolean close() {


        return false;
    }


}
