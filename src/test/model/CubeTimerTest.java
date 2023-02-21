package model;

import model.CubeTimer;
import model.SolveTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CubeTimerTest {
    private CubeTimer cubeTimerTest;

    @BeforeEach
    public void setup() {
        this.cubeTimerTest = new CubeTimer("Joe");
        EventLog.getInstance().clear();
    }

    @Test
    public void constructorTest() {
        assertEquals("Joe", cubeTimerTest.getName());
        assertEquals(new ArrayList<SolveTime>(), cubeTimerTest.getTimeList());
    }

    @Test
    public void scrambleCubeTest() {
        String scramble = cubeTimerTest.getScramble();
        int scrambleLength = scramble.length() - scramble.replaceAll(" ", "").length();
        assertTrue(scrambleLength >= 19 && scrambleLength <= 21); // tests length of a random scramble
        // since all other aspects of scrambleCube method is completely randomized and/or already tested in acceptTest,
        // no other tests can be written
    }

    @Test
    public void acceptTest() {
        assertFalse(cubeTimerTest.accept(0, 2)); // tests case 1
        assertFalse(cubeTimerTest.accept(4, 5)); // tests case 2
        assertFalse(cubeTimerTest.accept(7, 6)); // tests case 3
        assertFalse(cubeTimerTest.accept(11, 9)); // tests case 4
        assertFalse(cubeTimerTest.accept(12, 13)); // tests case 5
        assertFalse(cubeTimerTest.accept(17, 15)); // tests case 6
        assertTrue(cubeTimerTest.accept(13, 1)); // tests a valid case
        assertTrue(cubeTimerTest.accept(-1, 15)); // tests starting position in scrambleCubeTest method
    }

    @Test
    public void addTimeTest() {
        SolveTime solveTime1 = new SolveTime(15.32);
        SolveTime solveTime2 = new SolveTime(35.48);
        SolveTime solveTime3 = new SolveTime(333.28);

        cubeTimerTest.addTime(solveTime1);
        assertEquals(solveTime1, cubeTimerTest.getTimeList().get(0)); // tests that the time was added

        cubeTimerTest.addTime(solveTime2);
        cubeTimerTest.addTime(solveTime3);
        assertEquals(solveTime2, cubeTimerTest.getTimeList().get(1)); // tests that times were added in right order
        assertEquals(3, cubeTimerTest.getTimeList().size()); // tests size
    }

    @Test
    public void deleteLastSolveTest() {
        cubeTimerTest.deleteLastSolve();
        assertEquals(0, cubeTimerTest.getTimeList().size()); // tests deleting from already empty list

        SolveTime solveTime1 = new SolveTime(15.32);
        SolveTime solveTime2 = new SolveTime(35.48);
        SolveTime solveTime3 = new SolveTime(333.28);

        cubeTimerTest.addTime(solveTime1);
        cubeTimerTest.deleteLastSolve();
        assertEquals(new ArrayList<SolveTime>(), cubeTimerTest.getTimeList()); // tests that the time was removed

        cubeTimerTest.addTime(solveTime1);
        cubeTimerTest.addTime(solveTime2);
        cubeTimerTest.addTime(solveTime3);
        cubeTimerTest.deleteLastSolve();
        assertEquals(solveTime2, cubeTimerTest.getTimeList().get(1)); // tests that 2nd object stays the same
        assertEquals(2, cubeTimerTest.getTimeList().size()); // tests size
    }

    @Test
    public void averageOfFiveTest() {
        SolveTime solveTime1 = new SolveTime(15.32);
        SolveTime solveTime2 = new SolveTime(27.32);
        SolveTime solveTime3 = new SolveTime(4.47);
        SolveTime solveTime4 = new SolveTime(5.00);
        SolveTime solveTime5 = new SolveTime(5.88);
        SolveTime solveTime6 = new SolveTime(15.56);

        cubeTimerTest.addTime(solveTime3);
        cubeTimerTest.addTime(solveTime2);
        cubeTimerTest.addTime(solveTime4);
        cubeTimerTest.addTime(solveTime1);
        assertEquals("N/A", cubeTimerTest.getAverageOfFive()); // tests list size < 5

        cubeTimerTest.addTime(solveTime5);
        assertEquals("11.60", cubeTimerTest.getAverageOfFive()); // tests list size = 5

        cubeTimerTest.addTime(solveTime6);
        assertEquals("13.82", cubeTimerTest.getAverageOfFive()); // tests list size > 5
    }
}