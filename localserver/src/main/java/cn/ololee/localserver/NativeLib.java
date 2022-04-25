package cn.ololee.localserver;

public class NativeLib {

  // Used to load the 'localserver' library on application startup.
  static {
    System.loadLibrary("localserver");
  }


  public static native String startServer();
}