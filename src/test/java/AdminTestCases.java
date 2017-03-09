import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017
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

    //create two diff classes same year
    @Test
    public void twoDiffClasses(){
        admin.createClass("Test1", 2017, "Instructor", 22);
        admin.createClass("Test2", 2017, "Instructor", 22);
        assertTrue(this.admin.classExists("Test1", 2017));

    }

    //create a class in the past
    @Test
    public void createClassInPast() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    //no class exist
    @Test
    public void noClassExist(){
        assertFalse(this.admin.classExists("Test", 2017));
    }

    //create two classes in a diff years, one of the them in the past
    @Test
    public void twoSameClasses(){
        admin.createClass("Test1", 2017, "Instructor", 22);
        admin.createClass("Test1", 2016, "Instructor", 22);
        assertFalse(this.admin.classExists("Test1", 2016));
    }

    //create more than 2 classes per instructor
    @Test
    public void moreSameClass(){
        admin.createClass("Test1", 2017, "Instructor", 22);
        admin.createClass("Test1", 2017, "Instructor", 22);
        admin.createClass("Test1", 2017, "Instructor", 22);
        assertFalse(this.admin.classExists("Test1", 2017));

    }

    //@return The capacity (maximum number of enrollees) for class {@code className} in year {@code year}
    //capacity is 0
    @Test
    public void capacityClass(){
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    //@return The capacity (maximum number of enrollees) for class {@code className} in year {@code year}
    //capacity is -1
    @Test
    public void capacityClass2(){
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
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

    //check for diff student names
    @Test
    public void enrollment(){
        boolean bool = true;
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Student1", "Test", 2017);
        bool = this.student.isRegisteredFor("Student", "Test", 2017);
        //System.out.println(bool);
        assertFalse(bool);
    }

    //check the capacity of the class
    //capacity is 2 and we have 2 students
    @Test
    public void capacityChanged(){
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.admin.changeCapacity("Test", 2017, 2);
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.registerForClass("Student2", "Test", 2017);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
    }

    //check the capacity of the class
    //capacity is 2 and we have 3 students
    @Test
    public void capacityChanged2(){
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.admin.changeCapacity("Test", 2017, 2);
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.registerForClass("Student2", "Test", 2017);
        this.student.registerForClass("Student3", "Test", 2017);
        assertEquals(3, this.admin.getClassCapacity("Test", 2017));
    }

    //check the capacity of the class
    //capacity is 2, # of students 2 => capacity changed to 1
    @Test
    public void capacityChanged3(){
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.registerForClass("Student1", "Test", 2017);
        this.student.registerForClass("Student2", "Test", 2017);
        this.admin.changeCapacity("Test", 2017, 1);
        assertEquals(2, this.admin.getClassCapacity("Test", 2017));
    }
}
