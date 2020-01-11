import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {


    private String name;
    private String subject;
    private int semester;


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


}
