package Tests.Handlers;

import Handlers.FileFinder;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileFinderTest {

  @Test
  public void findReturnsThePathToTheGivenFile() throws FileNotFoundException {
    assertEquals("/Users/unclebob/Projects/KBot/src/HTTPServer/Logger/Logger.java", FileFinder.find("Logger.java", "src"));
  }

  @Test
  public void tryingToFindAFileThatDoesntExistThrowsFileNotFoundException() {
    boolean exceptionThrown = false;
    try {
      new FileReader(FileFinder.find("this_aint_no_real_file.java", "src"));
    } catch (FileNotFoundException fne) {
      exceptionThrown = true;
    }
    assertTrue(exceptionThrown);
  }

}
