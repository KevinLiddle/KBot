package HTTPServer.Logger;

import java.io.*;

public class FileLogger implements Logger {

  public String location;

  public FileLogger() {
    this("src/log.txt");
  }

  public FileLogger(String _location) {
    this.location = _location;
  }

  public void log(String message) {
    try {
      FileWriter fw = new FileWriter(location, true);
      BufferedWriter bw = new BufferedWriter(fw);

      bw.write(message + "\n");
      bw.close();
    } catch(IOException ioe) {
      System.out.println("Could not write to log file!");
    }
  }
}
