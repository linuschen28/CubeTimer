package model;

import model.SolveTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolveTimeTest {
    private SolveTime solveTimeTest;

    @BeforeEach
    public void setup() {
        this.solveTimeTest = new SolveTime(12.45);
    }

    @Test
    public void constructorTest() {
        assertEquals(12.45, solveTimeTest.getSolveTime());
    }
}
