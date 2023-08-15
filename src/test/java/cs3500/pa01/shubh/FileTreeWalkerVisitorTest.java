package cs3500.pa01.shubh;

import cs3500.pa01.TestExamples;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * A test class for the FileTreeWalkerVisitor class.
 */
public class FileTreeWalkerVisitorTest extends TestExamples {

  /**
   * Tests the creation of a file tree and getting the list of files.
   *
   * @throws IOException if an I/O error occurs.
   */
  @Test
  public void testWriterTest() throws IOException {
    FileTreeWalkerVisitor fileTree = new FileTreeWalkerVisitor(Path.of(directoryPath), fileType);
    files = fileTree.getList();
  }

  /**
   * Tests the IOException thrown by the FileTreeWalkerVisitor when the directory does not exist.
   */
  @Test
  public void testException() {
    FileTreeWalkerVisitor falseTree;
    falseTree = new FileTreeWalkerVisitor(Path.of("non-existent"), fileType);
    Assertions.assertThrows(IOException.class, falseTree::getList);
  }

  /**
   * Tests the visitFileFailed method of the FileTreeWalkerVisitor class.
   */
  @Test
  public void testVisitFileFailed() {
    FileTreeWalkerVisitor fileTree = new FileTreeWalkerVisitor(Path.of(directoryPath), fileType);
    IOException exception = new IOException("Test IOException");

    IOException thrownException = Assertions.assertThrows(IOException.class, () ->
        fileTree.visitFileFailed(Path.of("testFile"), exception));
    Assertions.assertEquals(exception, thrownException);
  }
}