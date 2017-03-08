import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class AdminTestCases {
    private IAdmin admin;
    protected IStudent student = new Student();

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void createClassInCurYear() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    //create a class in the past
    @Test
    public void createClassInPast() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    //create two classes in a diff years, one of the them in the past
    @Test
    public void twoDiffClasses(){
        admin.createClass("Test1", 2017, "Instructor", 22);
        admin.createClass("Test1", 2016, "Instructor", 22);
        assertFalse(this.admin.classExists("Test1", 2016));

    }

    //@return Whether class {@code className} exists in year {@code year}
    @Test
    public void existClass1(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue("No class is created", this.admin.classExists("Test", 2017));
    }

    //@return The capacity (maximum number of enrollees) for class {@code className} in year {@code year}
    //capacity is 0
    @Test
    public void capacityClass(){
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse("can't create a class with capacity 0",this.admin.classExists("Test", 2017));
    }

    //@return The capacity (maximum number of enrollees) for class {@code className} in year {@code year}
    //capacity is -1
    @Test
    public void capacityClass2(){
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse("Can't creat a class with capacity negative",this.admin.classExists("Test", 2017));
    }

    //@return The capacity (maximum number of enrollees) for class {@code className} in year {@code year}
    @Test
    public void capacityClass3(){
        this.admin.createClass("Test", 2017, "Instructor", 2);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    //@return The name of the instructor for class {@code className} in year {@code year}
    @Test
    public void instructorName(){
        String nameStr = null;
        this.admin.createClass("Test", 2017, "Instructor", 15);
        nameStr = this.admin.getClassInstructor("Test", 2017);
        assertNotNull(nameStr);
        assertEquals(nameStr, "Instructor");
    }

    @Test
    public void enrollment(){
        boolean bool = true;
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Student1", "Test", 2017);
        bool = this.student.isRegisteredFor("Student", "Test", 2017);
        //System.out.println(bool);
        assertFalse(bool);
    }
}
