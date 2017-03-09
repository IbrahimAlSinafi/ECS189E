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
        assertFalse(InstructorName.contentEquals("Ibrahim"));
    }

    //base case
    @Test
    public void assignHW(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        assertTrue(this.instructor.homeworkExists("Test", 2017, "hw3"));
    }

    //submit the hw, where the hw doesn't exist
    //missing "add homework"
    @Test
    public void assignHW2(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        assertFalse(this.instructor.homeworkExists("Test", 2017, "hw3"));
    }

    //check for future hw that is never been added, year
    @Test
    public void assignHW3(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        assertFalse(this.instructor.homeworkExists("Test", 2019, "hw3"));
    }

    //check for hw that is never been added, different hws
    @Test
    public void assignHW4(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        assertFalse(this.instructor.homeworkExists("Test", 2017, "hw4"));
    }

    //class doesn't exist
    @Test
    public void assignHW5(){
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        assertFalse(this.instructor.homeworkExists("Test", 2017, "hw3"));
    }

    //Student graded on the hw
    @Test
    public void gradesResult(){
        int grade = 0;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 99);
        grade = this.instructor.getGrade("Test", 2017, "hw3", "Student");
        assertTrue("grade can't be negative", grade >= 0);
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

    //assigne grade to hw doesn't exist
    //missing "add homework"
    @Test
    public void gradesResult2(){
        int grade;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 10);
        assertNull(this.instructor.getGrade("Test", 2017, "hw3", "Student"));
    }

    //get a grade from a class created in the past
    @Test
    public void gradesResult3(){
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 99);
        assertNull(this.instructor.getGrade("Test", 2016, "hw3", "Student"));
    }

    //get a grade for a class added in future (2018)
    @Test
    public void gradesResult4() {
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2018, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 99);
        assertNull(this.instructor.getGrade("Test", 2017, "hw3", "Student"));
    }

    //different instructors
    @Test
    public void gradesResult5() {
        this.admin.createClass("Test", 2017, "Instructor", 50);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor1", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor2", "Test", 2017, "hw3", "Student", 99);
        assertNull(this.instructor.getGrade("Test", 2017, "hw3", "Student"));
    }

    //student not register for the class
    @Test
    public void gradesResult6(){
        int grade = 0;
        this.admin.createClass("Test", 2017, "Instructor", 50);
        //this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        this.student.submitHomework("Student", "hw3", "Programming", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "hw3", "Student", 99);
        assertNull(this.instructor.getGrade("Test", 2017, "hw3", "Student"));
    }
}

