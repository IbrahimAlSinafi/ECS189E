import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ibrahim on 3/3/17.
 */
public class InstructorTestCases {
    private IInstructor instructor;
    protected IAdmin admin = new Admin();
    protected IStudent student = new Student();

    @Before
    public void setup(){
        this.instructor = new Instructor();
    }

    //provided this instructor has been assigned to this class.
    @Test
    public void InstructorAssigned1(){
        String InstructorName = null;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        InstructorName = this.admin.getClassInstructor("Test", 2017);
        //System.out.println(InstructorName.contentEquals("Instructor"));
        assertTrue(InstructorName.contentEquals("Instructor"));
    }

    //provided this instructor has been assigned to this class.
    //change Instructor name
    @Test
    public void InstructorAssigned2(){
        String InstructorName = null;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        InstructorName = this.admin.getClassInstructor("Test", 2017);
        //System.out.println(InstructorName.contentEquals("Ibrahim"));
        assertFalse(InstructorName.contentEquals("Ibrahim"));
    }

    //provided this instructor has been assigned to this class,
    //the homework has been assigned and the student has submitted this homework.
    @Test
    public void HWAdded1(){
        boolean bool = true;
        String InstructorName = "";
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        InstructorName = this.admin.getClassInstructor("Test", 2017);
        this.instructor.addHomework(InstructorName, "Test", 2017, "hw3", "Programming");
        bool = this.student.hasSubmitted("Student", "hw3", "Test", 2017);
        assertTrue(bool);
    }

    //provided this instructor has been assigned to this class,
    //the homework has been assigned and the student has submitted this homework.
    //Add hw3 but students submited hw4 (FALSE)
    @Test
    public void HWAdded2(){
        boolean bool = true;
        String InstructorName = "";
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        InstructorName = this.admin.getClassInstructor("Test", 2017);
        this.instructor.addHomework(InstructorName, "Test", 2017, "hw3", "Programming");
        bool = this.student.hasSubmitted("Student", "hw4", "Test", 2017);
        assertFalse(bool);
    }

    //can't accept negative grades
    @Test
    public void gradesResult1(){
        int grade = 0;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", -1);
        grade = this.instructor.getGrade("Test", 2017, "hw3", "Student");
        assertNull(grade);
        //assertFalse("grade can't be negative", grade < 0);
    }

    //Student graded on the hw
    @Test
    public void gradesResult2(){
        int grade = 0;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 99);
        grade = this.instructor.getGrade("Test", 2017, "hw3", "Student");
        assertTrue("grade can't be negative", grade >= 0);
    }

    //Instructor got a grade before the class created
    @Test
    public void gradesResult3(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 99);
        assertNull(this.instructor.getGrade("Test", 2016, "hw3", "Student"));
    }

    //submit the hw, where the hw doesn't exist
    //missing "add homework"
    @Test
    public void assignHW(){
        boolean bool = true;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        bool = this.instructor.homeworkExists("Test", 2017, "hw3");
        //System.out.println(bool);
        assertFalse(bool);
    }

    //assigne grade to hw doesn't exist
    //missing "add homework"
    @Test
    public void assignGrade(){
        int grade;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 10);
        assertNull(this.instructor.getGrade("Test", 2017, "hw3", "Student"));
    }
}

