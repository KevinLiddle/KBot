package Handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileFinder {

  public static String findFromSrcDir (String fileName) throws FileNotFoundException {
    return find(fileName, "src");
  }

  public static String find(String fileName, String startingPoint) throws FileNotFoundException {
    File source = new File(startingPoint);
    String path = "";
    if(source.isDirectory()){
      for(File file : source.listFiles()) {
        if(file.getName().equals(fileName)) {
          try {
            return file.getCanonicalPath();
          } catch(IOException ioe) {
            System.out.println("Uh oh...can't get this file path");
          }
        } else if(path.equals(""))
          path = find(fileName, startingPoint + "/" + file.getName());
      }
    }
    return path;
  }
}
