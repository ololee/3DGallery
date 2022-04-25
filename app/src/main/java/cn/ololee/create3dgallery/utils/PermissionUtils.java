package cn.ololee.create3dgallery.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

  public static void getPermissions(Activity activity) {
    getReadStoragePermissions(activity);
    getWriteStoragePermissions(activity);
    getAccessWifiState(activity);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private static void getReadStoragePermissions(Activity activity) {
    int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {

      } else {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private static void getAccessWifiState(Activity activity) {
    int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE);

    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_WIFI_STATE)) {

      } else {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
      }
    }
  }

  private static void getWriteStoragePermissions(Activity activity) {
    int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

      } else {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
      }
    }
  }
}