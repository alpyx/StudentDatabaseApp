import java.sql.*;
import java.util.HashMap;

public class DataSource {

    public static final String DB_NAME = "uni.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/home/alper/" + DB_NAME;

    public static final String TABLE_SUBJECTS = "subjects";
    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_SUBJECT_NAME = "name";

    public static final String TABLE_COURSES = "courses";
    public static final String COLUMN_COURSE_ID = "_id";
    public static final String COLUMN_COURSE_NAME = "name";
    public static final String COLUMN_COURSE_SEMESTER = "semester";
    public static final String COLUMN_COURSE_SUBJECT = "subject";

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_SUBJECT = "subject";
    public static final String COLUMN_STUDENT_SEMESTER = "semester";

    public static final String VIEW_SUBJECTS_COURSES = "subjects_courses";
    public static final String COLUMN_SUBJECTS_COURSES_SUBJECT_ID = "_id";
    public static final String COLUMN_SUBJECTS_COURSES_SUBJECT_NAME = "subject";
    public static final String COLUMN_SUBJECTS_COURSES_SEMESTER = "semester";
    public static final String COLUMN_SUBJECTS_COURSES_COURSE_ID = "course_id";

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_NOTES_ID = "_id";
    public static final String COLUMN_NOTES_STUDENT_ID = "student_id";
    public static final String COLUMN_NOTES_COURSE_ID = "course";
    public static final String COLUMN_NOTES_NOTE = "note";


    //SELECT students.name, courses.name FROM students INNER JOIN subjects ON students.subject = subjects._id  INNER JOIN courses ON subjects._id = courses.subject ORDER BY students._id;
    //SELECT students.name, students.semester, subjects.name FROM students INNER JOIN subjects ON students.subject = subjects._id ORDER BY students._id
    //INSERT INTO students(name, subject, semester) VALUES("Petar Ivanov", 1, 1);

    //show student's notes from the different courses
//    SELECT students.name, courses.name, notes.note FROM notes INNER JOIN students ON students._id  = notes.student_id INNER JOIN courses ON courses._id = notes.course WHERE students.name = "Alper Ahmedov" ORDER BY notes.note;

    public static final String QUERY_NOTES_FOR_STUDENT = "SELECT " + TABLE_STUDENTS + "." + COLUMN_STUDENT_NAME + ", " + TABLE_COURSES + "." + COLUMN_COURSE_NAME + ", " + TABLE_NOTES + "." + COLUMN_NOTES_NOTE +
            " FROM " + TABLE_NOTES + " INNER JOIN " + TABLE_STUDENTS + " ON " + TABLE_STUDENTS + "." + COLUMN_STUDENT_ID + " = " + TABLE_NOTES + "." + COLUMN_NOTES_STUDENT_ID +
            " INNER JOIN " + TABLE_COURSES + " ON " + TABLE_COURSES + "." + COLUMN_COURSE_ID + " = " + TABLE_NOTES + "." + COLUMN_NOTES_COURSE_ID + " WHERE " + TABLE_STUDENTS + "." + COLUMN_STUDENT_NAME + " = ? ORDER BY " + TABLE_NOTES + "." + COLUMN_NOTES_NOTE;


    //get the average note for the semester for a student
    //SELECT avg(note) FROM notes WHERE student_id = 201216001;


    //insert a note
    // INSERT INTO notes(student_id, course, note) VALUES("201216001", 4, 4);

    //select all of the students's names
    //SELECT students.name FROM students;

    //select all of the subjects
    //SELECT subjects.name FROM subjects

    //select all of the courses
    //SELECT courses.name FROM courses

    private Connection conn;

    private PreparedStatement queryStudent;
    private PreparedStatement querySubject;
    private PreparedStatement queryCourses;
    private PreparedStatement queryNotesForStudent;

    private PreparedStatement insertIntoStudents;
    private PreparedStatement insertIntoSubjects;
    private PreparedStatement insertIntoCourses;


    public boolean open() {

        try {

            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryNotesForStudent = conn.prepareStatement(QUERY_NOTES_FOR_STUDENT);

            return true;

        } catch (SQLException e) {

            System.out.println("Couldn't connect do database " + e.getMessage());
            return false;
        }


    }

    public void close() {

        try {

            if (queryNotesForStudent != null) queryNotesForStudent.close();


        } catch (SQLException e) {

            System.out.println("Couldn't close connection " + e.getMessage());
        }

    }


    public HashMap<String, Integer> queryNotesForStudent(String studentName) {


        try {

            queryNotesForStudent.setString(1, studentName);
            System.out.println(QUERY_NOTES_FOR_STUDENT);
            ResultSet resultSet = queryNotesForStudent.executeQuery();

            HashMap<String, Integer> studentNotes = new HashMap<>();

            while (resultSet.next()) {

                studentNotes.put(resultSet.getString(2), resultSet.getInt(3));
//                studentNotes[resultSet.getString(2)] = resultSet.getString(3);

            }
            return studentNotes;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }


    }

}
