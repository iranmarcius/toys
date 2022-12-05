package toys.servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedBodyServletInputStream extends ServletInputStream {
  private final InputStream cachedBodyInputStream;

  public CachedBodyServletInputStream(byte[] cachedBody) {
    cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
  }

  @Override
  public int read() throws IOException {
    return cachedBodyInputStream.read();
  }

  @Override
  public boolean isFinished() {
    try {
      return cachedBodyInputStream.available() == 0;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener listener) {
    throw new UnsupportedOperationException();
  }

}
