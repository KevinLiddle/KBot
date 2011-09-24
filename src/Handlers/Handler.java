package Handlers;

import HTTPServer.Logger.Logger;
import HTTPServer.Logger.SOLogger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handler {

  public Logger logger;

  public Handler() {
    this(new SOLogger());
  }

  public Handler(Logger _logger) {
    this.logger = _logger;
  }

  public BufferedReader execute(String request) {
    try{
      BufferedReader br = new BufferedReader(new FileReader(new File(FileFinder.findFromSrcDir("routes.txt"))));
      String line = br.readLine();
      while(line != null){  
        String[] route = line.split("\\s*->\\s*");
        if(!line.isEmpty() && !line.startsWith("///") && request.matches(route[0])){
          return callMethod(route[1], request);
        }
        line = br.readLine();
      }
      logger.log("ERROR!! No route matches \"" + request + "\"");
      return notFound();
    } catch(FileNotFoundException e) {
      logger.log("ERROR!! Could not find your routes file...");
      e.printStackTrace();
    } catch(IOException ioe) {
      logger.log("ERROR!! Couldn't read from the routes file...");
    } catch(Exception e ){
      return error();
    }
    return error();
  }

  private BufferedReader error() {
    return renderDefaultHTMLFiles("/views/error.html");
  }

  public BufferedReader notFound() {
    return renderDefaultHTMLFiles("/views/404NotFound.html");
  }

  protected BufferedReader renderHTMLString(String output) {
    return new BufferedReader(new StringReader(output));
  }

  public BufferedReader renderFile(String location) {
    try {
      return renderFile(new FileReader(new File(FileFinder.findFromSrcDir(location))));
    } catch(FileNotFoundException fne) {
      logger.log("ERROR!! Cannot find \"" + location + "\"");
      if(location.equals("error.html")){
        logger.log("WHY DID YOU DELETE THE ERROR PAGE?!?!?!");
        return null;
      }
      return error();
    }
  }

  private BufferedReader renderDefaultHTMLFiles(String location) {
    return renderFile(new InputStreamReader(getClass().getResourceAsStream(location)));
  }

  private BufferedReader renderFile(InputStreamReader isr) {
    return new BufferedReader(isr);
  }

  protected ArrayList<Integer> getIDs(String request) {
    ArrayList<Integer> IDs = new ArrayList<Integer>();
    Pattern ints = Pattern.compile("\\d+");
    Matcher match = ints.matcher(request);
    while(match.find()){
      int id = Integer.parseInt(match.group());
      IDs.add(id);
    }
    return IDs;
  }

  public BufferedReader callMethod(String methodName, String request) throws Exception {
    String[] route = methodName.split("#");
    String className = "Handlers." + route[0];
    try{
      Class<?> handlerClass = Class.forName(className);
      Method method = handlerClass.getMethod(route[1], String.class);
      return (BufferedReader) method.invoke(handlerClass.newInstance(), request);
    } catch(ClassNotFoundException cnf) {
      logger.log("ERROR!! Class: \"" + className + "\" not found.");
    } catch(NoSuchMethodException nsm) {
      logger.log("ERROR!! Method: \"" + route[1] + "\" not found.");
    } catch(InvocationTargetException ite) {
      logger.log("ERROR!! The method \"" + route[1] + "\" has thrown an exception");
    } catch(IllegalAccessException iae) {
      logger.log("ERROR!! You cannot access the method \"" + route[1] + "\" from here.");
    } catch(InstantiationException ie) {
      logger.log("ERROR!! Couldn't instantiate the class: " + route[0]);
    }
    throw new Exception();
  }
}
