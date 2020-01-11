import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    //SELECT avg(note) FROM notes INNER JOIN courses ON notes.course = courses._id WHERE notes.student_id = "201216001" AND courses.semester = 1;

    public static final String QUERY_AVERAGE_NOTE_FOR_SEMESTER = "SELECT avg(" + COLUMN_NOTES_NOTE + ") FROM " + TABLE_NOTES + " INNER JOIN " + TABLE_COURSES + " ON " + TABLE_NOTES + "." + COLUMN_NOTES_COURSE_ID + " = " + TABLE_COURSES + "." + COLUMN_COURSE_ID + " WHERE " + TABLE_NOTES + "." + COLUMN_NOTES_STUDENT_ID + " = ? AND " + TABLE_COURSES + "." + COLUMN_COURSE_SEMESTER + " = ?";


    // get the note for a subject
//    SELECT notes.note FROM notes INNER JOIN courses ON notes.course = courses._id WHERE student_id = " 201216001" AND courses.name = "Deutsch";

    public static final String QUERY_NOTE_FOR_COURSE = "SELECT " + TABLE_NOTES + "." + COLUMN_NOTES_NOTE + " FROM " + TABLE_NOTES + " INNER JOIN " + TABLE_COURSES +
            " ON " + TABLE_NOTES + "." + COLUMN_NOTES_COURSE_ID + " = " + TABLE_COURSES + "." + COLUMN_COURSE_ID + " WHERE " + COLUMN_NOTES_STUDENT_ID + " = ? AND " + TABLE_COURSES + "." + COLUMN_COURSE_NAME + " = ?";


    //insert a note
    // INSERT INTO notes(student_id, course, note) VALUES("201216001", 4, 4);


    //select all of the students
    //SELECT students.name, students.semester, students.subject  FROM students;
    public static final String QUERY_STUDENTS = "SELECT " + TABLE_STUDENTS + "." + COLUMN_STUDENT_NAME + ", " + TABLE_STUDENTS + "." + COLUMN_STUDENT_SEMESTER + ", " + TABLE_STUDENTS + "." + COLUMN_STUDENT_SUBJECT + " FROM " + TABLE_STUDENTS;

    public static final String QUERY_SUBJECTS = "SELECT " + TABLE_SUBJECTS + "." + COLUMN_SUBJECT_NAME + " FROM " + TABLE_SUBJECTS;

    public static final String QUERY_COURSES = "SELECT * FROM " + TABLE_COURSES;
    //select all of the subjects
    //SELECT subjects.name FROM subjects

    //select all of the courses
    //SELECT courses.name FROM courses

    private Connection conn;

    private PreparedStatement queryNotesForStudent;
    private PreparedStatement queryAverageNoteForSemester;
    private PreparedStatement queryNoteForCourse;

    private PreparedStatement insertIntoStudents;
    private PreparedStatement insertIntoSubjects;
    private PreparedStatement insertIntoCourses;


    public boolean open() {

        try {

            conn = DriverManager.getConnection(CONNECTION_STRING);

            queryNotesForStudent = conn.prepareStatement(QUERY_NOTES_FOR_STUDENT);
            queryAverageNoteForSemester = conn.prepareStatement(QUERY_AVERAGE_NOTE_FOR_SEMESTER);
            queryNoteForCourse = conn.prepareStatement(QUERY_NOTE_FOR_COURSE);


            return true;

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database " + e.getMessage());
            return false;
        }


    }

    public void close() {

        try {

            if (queryNotesForStudent != null) queryNotesForStudent.close();
            if (queryAverageNoteForSemester != null) queryAverageNoteForSemester.close();
            if (queryNoteForCourse != null) queryNoteForCourse.close();


        } catch (SQLException e) {

            System.out.println("Couldn't close connection " + e.getMessage());
        }

    }

    public List<Student> queryStudents() {


        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_STUDENTS)
        ) {

            List<Student> students = new ArrayList<>();


            while (resultSet.next()) {


                Student student = new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));

                students.add(student);
            }
            return students;


        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }


    }

    public int queryNoteForCourse(int studentID, String courseName) {


        try {

            queryNoteForCourse.setInt(1, studentID);
            queryNoteForCourse.setString(2, courseName);

            ResultSet resultSet = queryNoteForCourse.executeQuery();

            return resultSet.getInt(1);

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return -1;
        }


    }


    public double queryAverageNoteForSemester(int studentID, int semester) {


        try {

            queryAverageNoteForSemester.setInt(1, studentID);
            queryAverageNoteForSemester.setInt(2, semester);


            ResultSet resultSet = queryAverageNoteForSemester.executeQuery();

            return resultSet.getDouble(1);


        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return -1;
        }


    }


    public List<String> querySubjects() {

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_SUBJECTS)) {

            List<String> subjects = new ArrayList<>();

            while (resultSet.next()) {

                subjects.add(resultSet.getString(1));

            }
            return subjects;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }

    }

    public List<Course> queryCourses() {


        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_COURSES);
        ) {

            List<Course> courses = new ArrayList<>();


            while (resultSet.next()) {


                Course course = new Course(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3));

                courses.add(course);

            }
            return courses;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }

    }


    public HashMap<String, Integer> queryNotesForStudent(String studentName) {


        try {

            queryNotesForStudent.setString(1, studentName);
//            System.out.println(QUERY_NOTES_FOR_STUDENT);
            ResultSet resultSet = queryNotesForStudent.executeQuery();

            if (!resultSet.next()) {

                System.out.println(studentName + " has not been found.");
                return null;
            }

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
