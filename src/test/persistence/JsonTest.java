package persistence;

import model.SolveTime;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSolveTime(Double time, SolveTime solveTime) {
        Assertions.assertEquals(time, solveTime.getSolveTime());
    }
}
