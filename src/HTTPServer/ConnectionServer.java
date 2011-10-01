package HTTPServer;

import HTTPServer.Logger.Logger;

import java.io.IOException;
import java.net.Socket;

public abstract class ConnectionServer {

  public Logger logger;

  public abstract void serve(Socket connection) throws IOException;

  public abstract void close(Socket clientSocket) throws IOException;
}
