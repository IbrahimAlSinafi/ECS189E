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
public class StudentTestCases {
    private IStudent student;
    protected IAdmin admin = new Admin();
    protected IInstructor instructor = new Instructor();

    @Before
    public void setup(){
        this.student = new Student();
    }

    //class exists and has not met its enrolment capacity.
    //create a class with a capacity 2 and have 3 students. It should fail
    @Test
    public void enrolementCapacity(){
        Boolean bool = true;
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.registerForClass("Student2", "Test", 2017);
        this.student.registerForClass("Student3", "Test", 2017);
        bool = this.student.isRegisteredFor("Student3", "Test", 2017);
        assertFalse(bool);
    }

    //class exists and has not met its enrolment capacity.
    //create a class with a capacity 2 and have 2 students. It should pass (but fail)
    @Test
    public void enrolementCapacity2(){
        Boolean bool = true;
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.registerForClass("Student2", "Test", 2017);
        bool = this.student.isRegisteredFor("Student2", "Test", 2017);
        //System.out.println(bool);
        assertTrue(bool);
    }

    //student registered for a class before being dropped.
    @Test
    public void dropClass1(){
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.registerForClass("Student", "Test", 2017);
        this.student.dropClass("Student", "Test", 2017);
        assertTrue(! this.student.isRegisteredFor("Student", "Test", 2017));
    }

    //Student is no longer registered for the class after being dropped
    @Test
    public void dropClass2(){
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.registerForClass("Student", "Test", 2017);
        this.student.dropClass("Student", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
    }

    //student is not registered for a class, so they can't drop it
    @Test
    public void dropClass3(){
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.dropClass("Student", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
    }

    //provided homework exists, student is registered and the class is taught in the current year
    @Test
    public void submitHW1(){
        this.admin.createClass("Test", 2017, "Instructor", 22);
        this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "hw3"));
    }

    //the class is taught in the past year
    @Test
    public void submitHW2(){
        this.admin.createClass("Test", 2016, "Instructor", 22);
        this.student.registerForClass("Student", "Test", 2016);
        this.instructor.addHomework("Instructor", "Test", 2016, "hw3", "Programming");
        assertFalse(this.instructor.homeworkExists("Test", 2016, "hw3"));
    }

    //hw doesn't exist
    @Test
    public void submitHW3(){
        this.admin.createClass("Test", 2017, "Instructor", 22);
        this.student.registerForClass("Student", "Test", 2017);
        //this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        assertFalse(this.instructor.homeworkExists("Test", 2017, "hw3"));
    }

    //student is not register for the class
    @Test
    public void submitHW4(){
        this.admin.createClass("Test", 2017, "Instructor", 22);
        //this.student.registerForClass("Student", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw3", "Programming");
        assertFalse(! this.instructor.homeworkExists("Test", 2017, "hw3"));
    }
}
