package Tests.HTTPServer;

import HTTPServer.Logger.FileLogger;
import Handlers.FileFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class LoggerTest {

  FileLogger logger;

  @Before
  public void setUp() {
    logger = new FileLogger("src/testLog.txt");
  }

  @After
  public void tearDown() {
    File f = new File(logger.location);
    f.delete();
  }

  @Test
  public void logCreatesALogFileWithTotalLogSinceServerStarted() {
    String message = "Well, hello there.";
    logger.log(message);
    try {
      File logFile = new File(FileFinder.findFromSrcDir("testLog.txt"));
      assertEquals(message, new BufferedReader(new FileReader(logFile)).readLine());
    } catch(Exception e) {
      System.out.println("Can't find your log, dude.");
      fail();
    }
  }

  @Test
  public void logAppendsToCurrentLogIfThereIsOne() {
    logger.log("Well, hello there.");
    String message = "Goodbye, sir.";
    logger.log(message);
    try {
      File logFile = new File(FileFinder.findFromSrcDir("testLog.txt"));
      BufferedReader br = new BufferedReader(new FileReader(logFile));
      assertEquals("Well, hello there.", br.readLine());
      assertEquals(message, br.readLine());
    } catch(Exception e) {
      System.out.println("where da logz at?!?!?");
      fail();
    }
  }


}
