package cs3500.pa01.shubh;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa01.TestExamples;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * A test class for the Driver class.
 */
public class DriverTest extends TestExamples {

  /**
   * Tests the main method of the Driver class.
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testMain() throws IOException {
    Driver.main(new String[]{directoryPath, orderingFlag, outputPath});
    Files.exists(Path.of(outputPath));
  }

  /**
   * Tests the IOException thrown by the Driver class when the specified directory is invalid.
   */
  @Test
  public void testException() {
    String fakeDirectory = "FakeDirectory/";
    String orderingFlag = "filename";
    String outputPath = "output-test-directory";

    IOException thrownException = assertThrows(IOException.class,
        () -> Driver.main(new String[]{fakeDirectory, orderingFlag, outputPath}));

    assertNotNull(thrownException);
  }
}