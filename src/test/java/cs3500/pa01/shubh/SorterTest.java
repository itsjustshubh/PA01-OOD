package cs3500.pa01.shubh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa01.TestExamples;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * A test class for the Sorter class.
 */
public class SorterTest extends TestExamples {

  /**
   * Sets up the test by initializing the file tree.
   *
   * @throws IOException if an I/O error occurs during file tree creation.
   */
  public void testSetUp() throws IOException {
    FileTreeWalkerVisitor fileTree = new FileTreeWalkerVisitor(Path.of(directoryPath), fileType);
    files = fileTree.getList();
  }

  /**
   * Tests the main functionality of the Sorter class.
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testMain() throws IOException {
    this.testSetUp();
    Sorter sorter = new Sorter(files);
    ArrayList<File> sortedFilename = sorter.get("filename");
    ArrayList<File> sortedModified = sorter.get("modified");
    ArrayList<File> sortedCreated = sorter.get("created");
    assertEquals(sortedFilename, sortedModified);
  }

  /**
   * Tests the IllegalArgumentException thrown by the Sorter class.
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testIllegalArgumentException() throws IOException {
    File fakeFile = new File("hi");
    this.testSetUp();
    Sorter sorter = new Sorter(files);
    assertThrows(IllegalArgumentException.class, () -> sorter.get("size"));
    assertThrows(RuntimeException.class, () -> sorter.getFileCreationTime(fakeFile));
  }
}