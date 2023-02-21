package persistence;

import model.CubeTimer;
import model.SolveTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CubeTimer ct = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCubeTimer() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCubeTimer.json");
        try {
            CubeTimer ct = reader.read();
            Assertions.assertEquals("My cube timer", ct.getName());
            Assertions.assertEquals(0, ct.getTimeList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCubeTimer.json");
        try {
            CubeTimer ct = reader.read();
            Assertions.assertEquals("My cube timer", ct.getName());
            List<SolveTime> timeList = ct.getTimeList();
            assertEquals(2, timeList.size());
            checkSolveTime(32.32, timeList.get(0));
            checkSolveTime(34.21, timeList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}