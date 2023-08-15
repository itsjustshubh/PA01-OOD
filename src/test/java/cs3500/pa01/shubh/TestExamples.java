package cs3500.pa01.shubh;

import static java.nio.file.Files.walk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

abstract class TestExamples {
  public static String fileType = ".md";
  public static String directoryPath = "test-directory";
  public static String outputPath = directoryPath + File.separator + "output" + fileType;
  public ArrayList<File> sortedFiles;
  public static String orderingFlag = "filename";
  public static final String arraysFile = directoryPath + File.separator + "arrays" + fileType;
  private static final List<String> arraysContent;
  private static LocalDateTime arraysCreatedDate = LocalDateTime.of(2023, 3, 31, 14, 53);
  private static LocalDateTime arraysModifiedDate = LocalDateTime.of(2023, 4, 4, 11, 37);
  public static ArrayList<File> files;
  public static final String vectorsFile = directoryPath + File.separator + "vectors" + fileType;

  private static final List<String> vectorsContent;
  private static LocalDateTime vectorsCreatedDate = LocalDateTime.of(2023, 4, 3, 15, 1);
  private static LocalDateTime vectorsModifiedDate = vectorsCreatedDate;

  static {
    arraysContent = List.of(
        "# Java Arrays",
        "- [[An **array** is a collection of variables of the same type]], referred to by a common "
            + "name.",
        "- In Java, arrays are objects, and must be created dynamically (at runtime).",
        "", "## Declaring an Array", "- [[General Form: type[] arrayName;]]", "- ex: int[] myData;",
        "", "- The above [[only creates a reference]] to an array object, but [[no array has "
            + "actually been created yet]].", "", "## Creating an Array (Instantiation)",
        "- [[General form: arrayName = new type[numberOfElements];]]",
        "- ex: myData = new int[100];",
        "", "- Data types of the reference and array need to match.", "- [[numberOfElements must "
            + "be a positive Integer.]]",
        "- [[Gotcha: Array size is not modifiable once instantiated.]]"
    );
    vectorsContent = List.of(
        "# Vectors", "- [[Vectors act like resizable arrays]].", "",
        "## Declaring a vector", "- [[General Form: Vector<type> v = new Vector();]]", "- Example: "
            + "Vector<Integer> v = new Vector();", "",
        "- [[type needs to be a valid reference type]]", "", "## Adding an element to a vector",
        "- [[v.add(object of type);]]", "", "- Reminder - "
            + "go back and review clearing a vector!"
    );
  }

  @BeforeAll
  public static void setUp() throws IOException {
    File directory = new File(directoryPath);

    boolean created = directory.mkdir();
    if (created) {
      System.out.println("Directory created successfully.");
    } else {
      System.out.println("Directory already exists or an error occurred.");
    }

    createTestFile(vectorsFile, vectorsContent);
    createTestFile(arraysFile, arraysContent);

    setTestFileDates(arraysFile, arraysCreatedDate,  arraysModifiedDate);
    setTestFileDates(arraysFile, vectorsCreatedDate, vectorsModifiedDate);
  }

  private static void createTestFile(String fullPath, List<String> content) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
      for (String line : content) {
        writer.write(line);
        writer.newLine();
      }
      System.out.println("File created successfully!");
    } catch (IOException e) {
      System.out.println("An error occurred while creating the file.");
      e.printStackTrace();
      throw e;
    }
  }

  private static void setTestFileDates(String fullPath,
                                       LocalDateTime createdDateTime,
                                       LocalDateTime modifiedDateTime) throws IOException {
    Path filePath = Path.of(fullPath);
    try {
      Files.setLastModifiedTime(filePath, FileTime.from(modifiedDateTime.toInstant(ZoneOffset.UTC)));
      Files.setAttribute(filePath,
          "basic:creationTime",
          FileTime.from(createdDateTime.toInstant(ZoneOffset.UTC)));
    } catch (IOException e) {
      System.out.println("Failed to set file dates: " + e.getMessage());
    }
  }

  @AfterAll
  public static void tearDown() throws IOException {
    // Clean up the temporary test directory if it exists
    if (Files.exists(Path.of(directoryPath))) {
      Files.walk(Path.of(directoryPath))
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }
  }
}