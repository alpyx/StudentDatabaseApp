import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "uni.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/home/alper/IdeaProjects/StudentDatabaseApp/" + DB_NAME;

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


    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_NOTES_ID = "_id";
    public static final String COLUMN_NOTES_STUDENT_ID = "student_id";
    public static final String COLUMN_NOTES_COURSE_ID = "course";
    public static final String COLUMN_NOTES_NOTE = "note";


    public static final String QUERY_NOTES_FOR_STUDENT = "SELECT " + TABLE_STUDENTS + "." + COLUMN_STUDENT_NAME + ", " + TABLE_COURSES + "." + COLUMN_COURSE_NAME + ", " + TABLE_NOTES + "." + COLUMN_NOTES_NOTE +
            " FROM " + TABLE_NOTES + " INNER JOIN " + TABLE_STUDENTS + " ON " + TABLE_STUDENTS + "." + COLUMN_STUDENT_ID + " = " + TABLE_NOTES + "." + COLUMN_NOTES_STUDENT_ID +
            " INNER JOIN " + TABLE_COURSES + " ON " + TABLE_COURSES + "." + COLUMN_COURSE_ID + " = " + TABLE_NOTES + "." + COLUMN_NOTES_COURSE_ID + " WHERE " + TABLE_STUDENTS + "." + COLUMN_STUDENT_NAME + " = ? ORDER BY " + TABLE_NOTES + "." + COLUMN_NOTES_NOTE;


    public static final String QUERY_AVERAGE_NOTE_FOR_SEMESTER = "SELECT avg(" + COLUMN_NOTES_NOTE + ") FROM " + TABLE_NOTES + " INNER JOIN " + TABLE_COURSES + " ON " + TABLE_NOTES + "." + COLUMN_NOTES_COURSE_ID + " = " + TABLE_COURSES + "." + COLUMN_COURSE_ID + " WHERE " + TABLE_NOTES + "." + COLUMN_NOTES_STUDENT_ID + " = ? AND " + TABLE_COURSES + "." + COLUMN_COURSE_SEMESTER + " = ?";


    public static final String QUERY_NOTE_FOR_COURSE = "SELECT " + TABLE_NOTES + "." + COLUMN_NOTES_NOTE + " FROM " + TABLE_NOTES + " INNER JOIN " + TABLE_COURSES +
            " ON " + TABLE_NOTES + "." + COLUMN_NOTES_COURSE_ID + " = " + TABLE_COURSES + "." + COLUMN_COURSE_ID + " WHERE " + COLUMN_NOTES_STUDENT_ID + " = ? AND " + TABLE_COURSES + "." + COLUMN_COURSE_NAME + " = ?";


    public static final String INSERT_NOTE = "INSERT INTO " + TABLE_NOTES + "(" + COLUMN_NOTES_STUDENT_ID + ", " + COLUMN_NOTES_COURSE_ID + ", " + COLUMN_NOTES_NOTE + ") VALUES(?, ?, ?)";

    public static final String INSERT_STUDENT = "INSERT INTO " + TABLE_STUDENTS + "(" + COLUMN_SUBJECT_NAME + ", " + COLUMN_STUDENT_SUBJECT + ", " + COLUMN_STUDENT_SEMESTER + ")" + " VALUES(?, ?, ?)";

    public static final String INSERT_SUBJECT = "INSERT INTO " + TABLE_SUBJECTS + "( " + COLUMN_SUBJECT_NAME + " )" + "VALUES(?)";

    public static final String INSERT_COURSE = "INSERT INTO " + TABLE_COURSES + "(" + COLUMN_COURSE_NAME + ", " + COLUMN_COURSE_SEMESTER + ", " + COLUMN_COURSE_SUBJECT + ") VALUES(?, ?, ?)";


    public static final String QUERY_STUDENTS = "SELECT " + TABLE_STUDENTS + "." + COLUMN_STUDENT_NAME + ", " + TABLE_STUDENTS + "." + COLUMN_STUDENT_SEMESTER + ", " + TABLE_STUDENTS + "." + COLUMN_STUDENT_SUBJECT + " FROM " + TABLE_STUDENTS;

    public static final String QUERY_ALL_SUBJECTS = "SELECT " + COLUMN_SUBJECT_NAME + " FROM " + TABLE_SUBJECTS;

    public static final String QUERY_COURSES_FOR_SUBJECT = "SELECT " + COLUMN_COURSE_NAME + ", " + COLUMN_COURSE_SEMESTER + ", " + COLUMN_COURSE_SUBJECT + " FROM " + TABLE_COURSES + " WHERE " + COLUMN_COURSE_SUBJECT + " = ?";

    public static final String QUERY_SUBJECT = "SELECT " + COLUMN_SUBJECT_ID + " FROM " + TABLE_SUBJECTS + " WHERE " + COLUMN_SUBJECT_NAME + " = ?";

    public static final String QUERY_COURSE = "SELECT " + COLUMN_COURSE_ID + " FROM " + TABLE_COURSES + " WHERE " + COLUMN_COURSE_NAME + " = ?";

    public static final String QUERY_STUDENT = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENT_ID + " = ?";


    public static final String UPDATE_NOTE = "UPDATE " + TABLE_NOTES + " SET " + COLUMN_NOTES_NOTE + " = ?  WHERE " + COLUMN_NOTES_STUDENT_ID + " = ? AND " + COLUMN_NOTES_COURSE_ID + "= ?";

    public static final String UPDATE_STUDENTS_SEMESTER = "UPDATE " + TABLE_STUDENTS + " SET " + COLUMN_STUDENT_SEMESTER + " = ? WHERE " + COLUMN_STUDENT_ID + " = ?";

    public static final String DELETE_SUBJECT = "DELETE FROM " + TABLE_SUBJECTS + " WHERE " + COLUMN_COURSE_NAME + " = ?";

    private Connection conn;

    private PreparedStatement queryNotesForStudent;
    private PreparedStatement queryAverageNoteForSemester;
    private PreparedStatement queryNoteForCourse;
    private PreparedStatement queryCoursesForSubject;
    private PreparedStatement querySubject;
    private PreparedStatement queryCourse;
    private PreparedStatement queryStudent;

    private PreparedStatement insertStudent;
    private PreparedStatement insertSubject;
    private PreparedStatement insertCourse;
    private PreparedStatement insertNote;

    private PreparedStatement updateNote;
    private PreparedStatement updateStudentSemester;

    private PreparedStatement deleteSubject;


    public boolean open() {

        try {

            conn = DriverManager.getConnection(CONNECTION_STRING);

            queryNotesForStudent = conn.prepareStatement(QUERY_NOTES_FOR_STUDENT);
            queryAverageNoteForSemester = conn.prepareStatement(QUERY_AVERAGE_NOTE_FOR_SEMESTER);
            queryNoteForCourse = conn.prepareStatement(QUERY_NOTE_FOR_COURSE);
            querySubject = conn.prepareStatement(QUERY_SUBJECT);
            queryCoursesForSubject = conn.prepareStatement(QUERY_COURSES_FOR_SUBJECT);
            queryCourse = conn.prepareStatement(QUERY_COURSE);
            queryStudent = conn.prepareStatement(QUERY_STUDENT);


            insertNote = conn.prepareStatement(INSERT_NOTE);
            insertStudent = conn.prepareStatement(INSERT_STUDENT);
            insertSubject = conn.prepareStatement(INSERT_SUBJECT);
            insertCourse = conn.prepareStatement(INSERT_COURSE);

            updateNote = conn.prepareStatement(UPDATE_NOTE);
            updateStudentSemester = conn.prepareStatement(UPDATE_STUDENTS_SEMESTER);


            deleteSubject = conn.prepareStatement(DELETE_SUBJECT);


            return true;

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database " + e.getMessage());
            return false;
        }


    }

    public void close() {

        try {

            //QUERY
            if (queryNotesForStudent != null) queryNotesForStudent.close();
            if (queryAverageNoteForSemester != null) queryAverageNoteForSemester.close();
            if (queryNoteForCourse != null) queryNoteForCourse.close();
            if (queryCoursesForSubject != null) queryCoursesForSubject.close();
            if (queryCourse != null) queryCourse.close();
            if (querySubject != null) querySubject.close();
            if (queryStudent != null) queryStudent.close();


            //INSERT
            if (insertCourse != null) insertCourse.close();
            if (insertNote != null) insertNote.close();
            if (insertStudent != null) insertStudent.close();
            if (insertSubject != null) insertSubject.close();


            //UPDATE
            if (updateNote != null) updateNote.close();
            if (updateStudentSemester != null) updateStudentSemester.close();


            //DELETE
            if (deleteSubject != null) deleteSubject.close();


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

    public int insertCourse(String name, int semester, String subject) throws SQLException {


        int subjectId = querySubject(subject);

        if (subjectId != -1) {
            insertCourse.setString(1, name);
            insertCourse.setInt(2, semester);
            insertCourse.setInt(3, subjectId);

            int affectedRows = insertCourse.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert course.");
            }

            return affectedRows;
        }

        return -1;


    }


    public int insertSubject(String name) throws SQLException {


        insertSubject.setString(1, name);

        int affectedRows = insertSubject.executeUpdate();

        if (affectedRows != 1) {

            throw new SQLException("Couldn't insert subject.");
        }


        return affectedRows;
    }

    public int insertNote(int studentID, int course, int note) throws SQLException {

        //TODO gotta check if the course is from the curriculum of the subject of the student


        insertNote.setInt(1, studentID);
        insertNote.setInt(2, course);


        insertNote.setInt(3, note);


        int affectedRows = insertNote.executeUpdate();

        if (affectedRows != 1) {

            throw new SQLException("Couldn't insert note");

        }
        return affectedRows;


    }


    public String queryStudent(int studentID) {


        try {
            queryStudent.setInt(1, studentID);

            ResultSet resultSet = queryStudent.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(2);
            }


        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
        }

        return "Student not found.";

    }

    public int updateStudentSemester(int studentID, int semester) {


        try {

            if (!queryStudent(studentID).isEmpty()) {

                updateStudentSemester.setInt(2, studentID);
                updateStudentSemester.setInt(1, semester);

                return updateStudentSemester.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println("Failed to update students. " + e.getMessage());
        }
        return -1;
    }


    public int updateNote(int note, int studentID, String courseName) {

        //TODO update a note after an emendation

        try {


            if (queryCourse(courseName) == -1) {
                System.out.println("No such course in the database!");
                return -1;
            }


            updateNote.setInt(1, note);
            updateNote.setInt(2, studentID);
            updateNote.setInt(3, queryCourse(courseName));


            return updateNote.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to update note " + e.getMessage());
            return -1;
        }


    }

    public int insertStudent(String studentName, String subject, int semester) throws SQLException {

        //TODO check if the subject exists - done
        //TODO get the id for the subject from its name

        querySubject.setString(1, subject); // check if the subject exists

        ResultSet resultSet = querySubject.executeQuery();

        if (!resultSet.next()) {

            throw new SQLException("Subject does not exist");

        }


        insertStudent.setString(1, studentName); // setting student name
        insertStudent.setInt(2, resultSet.getInt(1)); // setting subject
        insertStudent.setInt(3, semester); // setting semester

        int affectedRows = insertStudent.executeUpdate();

        if (affectedRows != 1) {
            throw new SQLException("Couldn't insert student.");
        }

        ResultSet generatedKeys = insertStudent.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Couldn't get id for the student");
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

    public int queryCourse(String name) {

        try {


            queryCourse.setString(1, name);

            ResultSet resultSet = queryCourse.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1); //returns the _id of the course
            }

        } catch (SQLException e) {
            System.out.println("Query failed");
        }


        return -1;

    }

    public String queryCourse(int course) {


        //TODO implement queryCourse with int

        try {


            queryCourse.setInt(1, course);

            ResultSet resultSet = queryCourse.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(2);
            }

        } catch (SQLException e) {
            System.out.println("Query failed");
        }
        return null;

    }


    public int querySubject(String name) {


        try {

            querySubject.setString(1, name);

            ResultSet resultSet = querySubject.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }


        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());

        }
        return -1;
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
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_SUBJECTS)) {

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

    public int deleteSubject(String name) throws SQLException {

        deleteSubject.setString(1, name);

        return 0;


    }


    public List<Course> queryCoursesForSubject(String subject) {

        //query the courses for a subject
        //if the subject exists

        int subjectID = querySubject(subject);

        if (subjectID == -1) {
            System.out.println("Subject does not exist!");
            return null;
        }


        try {

            queryCoursesForSubject.setInt(1, subjectID);

            ResultSet resultSet = queryCoursesForSubject.executeQuery();

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
            ResultSet resultSet = queryNotesForStudent.executeQuery();

            if (!resultSet.next()) {

                System.out.println(studentName + " has not been found.");
                return null;
            }

            HashMap<String, Integer> studentNotes = new HashMap<>();

            while (resultSet.next()) {

                studentNotes.put(resultSet.getString(2), resultSet.getInt(3));

            }
            return studentNotes;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        }


    }

}
