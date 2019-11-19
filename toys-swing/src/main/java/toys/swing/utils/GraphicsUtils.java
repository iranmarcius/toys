package toys.swing.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Esta classe possui métodos utilitários para operações envolvendo elementos
 * gráficos.
 * @author Iran Marcius
 */
public class GraphicsUtils {

	/**
	 * Desenha uma string na posição informada com a fonte e cor informadas.
	 * @param g Graphics
	 * @param s String
	 * @param x Coluna
	 * @param y Linha
	 * @param font Fonte a ser utilizada
	 * @param color Cor a ser utilizada
	 */
	public static void drawString(Graphics g, String s, int x, int y, Font font,
		Color color) {
		g.setFont(font);
		g.setColor(color);
		g.drawString(s, x, y);
	}

	/**
	 * Desenha uma string dentro do retângulo informado, utilizando o alinhamento
	 * informado e cor e fonte atuais.
	 * @param g Graphics
	 * @param s String
	 * @param r Retângulo com a área que deverá ser utilizada como referência para
	 * o alinhamento da string
	 * @param horizontalAlignment Alinhamento horizontal
	 * @param verticalAlignment Alinhamento vertical
	 */
	public static void drawString(Graphics g, String s, Rectangle r,
		int horizontalAlignment, int verticalAlignment) {

		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(s);
		int h = fm.getHeight() - fm.getDescent();
		int x = 0;
		int y = 0;

		switch (horizontalAlignment) {
			case SwingConstants.CENTER: x = (r.width - w) / 2; break;
			case SwingConstants.RIGHT: x = r.width - w; break;
		}

		switch (verticalAlignment) {
			case SwingConstants.CENTER: y = (r.height - h) / 2; break;
			case SwingConstants.BOTTOM: y = r.height - h;
		}

//		Shape shape = g.getClip();
//		g.setClip(r.x, r.y, r.width, r.height);
		g.drawString(s, r.x + x, r.y + y + h);
//		g.setClip(shape);

	}

	/**
	 * Desenha uma string dentro do retângulo informado, utilizando o alinhamento,
	 * fonte e cor informados.
	 * @param g Graphics
	 * @param s String
	 * @param font Fonte
	 * @param color Cor
	 * @param r Retângulo com a área que deverá ser utilizada como referência para
	 * o alinhamento da string
	 * @param horizontalAlignment Alinhamento horizontal
	 * @param verticalAlignment Alinhamento vertical
	 */
	public static void drawString(Graphics g, String s, Font font, Color color,
		Rectangle r, int horizontalAlignment, int verticalAlignment) {
		g.setFont(font);
		g.setColor(color);
		drawString(g, s, r, horizontalAlignment, verticalAlignment);
	}

	/**
	 * Converte uma imagem fornecida em um array de bytes.
	 * @param img Referência para a imagem.
	 * @return <code>byte[]</code>
	 */
	public static byte[] getBytes(Image img) {
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "png", out);
			return out.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

}
