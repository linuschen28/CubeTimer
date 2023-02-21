package persistence;

import model.CubeTimer;
import model.SolveTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CubeTimer ct = new CubeTimer("My cube timer");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            CubeTimer ct = new CubeTimer("My cube timer");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCubeTimer.json");
            writer.open();
            writer.write(ct);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCubeTimer.json");
            ct = reader.read();
            Assertions.assertEquals("My cube timer", ct.getName());
            Assertions.assertEquals(0, ct.getTimeList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            CubeTimer ct = new CubeTimer("My cube timer");
            ct.addTime(new SolveTime(3.33));
            ct.addTime(new SolveTime(2.22));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCubeTimer.json");
            writer.open();
            writer.write(ct);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCubeTimer.json");
            ct = reader.read();
            Assertions.assertEquals("My cube timer", ct.getName());
            List<SolveTime> timeList = ct.getTimeList();
            assertEquals(2, timeList.size());
            checkSolveTime(3.33, timeList.get(0));
            checkSolveTime(2.22, timeList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
