package cs3500.pa01.shubh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * A test class for the Writer class.
 */
public class WriterTest extends TestExamples {

  /**
   * Sets up the test by initializing the file tree and sorting the files.
   *
   * @throws IOException if an I/O error occurs during file tree creation.
   */
  public void testSetUp() throws IOException {
    FileTreeWalkerVisitor fileTree = new FileTreeWalkerVisitor(Path.of(directoryPath), fileType);
    files = fileTree.getList();
    Sorter sorter = new Sorter(files);
    sortedFiles = sorter.get(orderingFlag);
  }

  /**
   * Tests the IOException thrown by the Writer class when writing the file.
   */
  @Test
  public void testException() {
    // Arrange
    List<File> sortedFiles = new ArrayList<>();
    String outputPath = "path/to/output/file.txt";
    Writer writer = new Writer(sortedFiles);

    // Act and Assert
    IOException exception = assertThrows(IOException.class, () -> writer.writeFile(outputPath));
    assertEquals("An error occurred while creating the file.", exception.getMessage());
  }

  /**
   * Tests the main functionality of the Writer class.
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testWriterTest() throws IOException {
    this.testSetUp();
    Writer writer = new Writer(sortedFiles);
    writer.writeFile(outputPath);
  }
}