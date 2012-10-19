package toys.tests.junit;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.swing.ImageIcon;

import toys.utils.ImageToys;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtilsTest {
	public static void main(String[] args) throws IOException {
		Image img = new ImageIcon("C:/temp/faxina.jpg").getImage();
		BufferedImage out = new BufferedImage(
			img.getWidth(null),
			img.getHeight(null),
			BufferedImage.TYPE_INT_RGB
		);
		Graphics2D g2d = out.createGraphics();
		g2d.drawImage(img, null, null);
		g2d.dispose();
		
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(ba);
		encoder.encode(out);
		
		byte[] b = ImageToys.setScale(ba.toByteArray(), 0.7, 0.7);
		FileOutputStream fo = new FileOutputStream("C:/temp/teste.jpg");
		fo.write(b);
		fo.close();
		
	}
}
