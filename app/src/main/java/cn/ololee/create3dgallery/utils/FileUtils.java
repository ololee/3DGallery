package cn.ololee.create3dgallery.utils;

import android.os.CancellationSignal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils {
  /**
   * 文件复制块大小 10Mb
   */
  public static final long COPY_BLOCK = 10 * 1024 * 1024;

  public static void copyFile(File src, File dst) throws IOException {
    copyFileWithChannelImpl(src, dst, new CancellationSignal());
  }

  private static void copyFileWithChannelImpl(File sourceFile, File dest,
      CancellationSignal cancellationSignal) {
    FileChannel inputChannel = null, outputChannel = null;
    try {
      inputChannel = new FileInputStream(sourceFile).getChannel();
      outputChannel = new FileOutputStream(dest).getChannel();
      long current = 0;
      long targetSize = inputChannel.size();
      long transformBlock = targetSize < COPY_BLOCK ? targetSize : COPY_BLOCK;
      while (((current + transformBlock) < targetSize) && (!cancellationSignal.isCanceled())) {
        outputChannel.transferFrom(inputChannel, current, transformBlock);
        current += transformBlock;
      }
      if (cancellationSignal.isCanceled()) {
        return;
      }
      if (current < targetSize) {
        outputChannel.transferFrom(inputChannel, current, targetSize - current);
      }
      current = targetSize;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (inputChannel != null) {
          inputChannel.close();
        }
        if (outputChannel != null) {
          outputChannel.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
