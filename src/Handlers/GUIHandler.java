package Handlers;

import java.io.BufferedReader;

public class GUIHandler extends Handler{

  public BufferedReader guiRequests(String request) {
    return implicitRequests(request);
  }

  private BufferedReader implicitRequests(String request) {
    return renderFile(request.replace("/", ""));
  }
}