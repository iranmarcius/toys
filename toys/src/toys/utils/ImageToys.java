package toys.utils;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Métodos utilitários para processamento de imagens.
 * @author Iran Marcius
 */
public class ImageToys {

	/**
	 * Retorna uma nova imagem com as dimens�es modificadas de acordo com as escalas.
	 * @param img Array de bytes da imagem
	 * @param ws Escala da largura
	 * @param hs Escala da altura
	 * @return byte[] Nova imagem com tamanho modificado
	 */
	public static byte[] setScale(byte[] img, double ws, double hs) throws IOException {
		Image in = new ImageIcon(img).getImage();
		int h = (int)(in.getHeight(null) * ws);
		int w = (int)(in.getWidth(null) * hs);
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		AffineTransform tx = new AffineTransform();
		tx.scale(ws, hs);
		Graphics2D g2d = out.createGraphics();
		g2d.drawImage(in, tx, null);
		g2d.dispose();

		// transfere a imagem para um array de bytes
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(ba);
		encoder.encode(out);

		return ba.toByteArray();
	}

	/**
	 * Codifica a imagem passada para o formado JPEG retornando um array de bytes.
	 * @param img Imagem a ser codificada
	 * @param qualidade Qualidade da imagem
	 * @return <code>byte[]</code>
	 * @throws IOException
	 */
	public static byte[] jpegEncode(Image img, float qualidade) throws IOException {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		try {
			g2d.drawImage(img, null, null);
		} finally {
			g2d.dispose();
		}
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(ba);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
			param.setQuality(qualidade, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(bi);
		} finally {
			ba.close();
		}
		return ba.toByteArray();
	}

	/**
	 * Desenha um texto nas coordenadas informadas fazendo quebra de linha na largura especificada.
	 * @param g Contexto gráfico onde o texto será desenhado
	 * @param texto Texto
	 * @param x Coluna inicial para desenhar o texto
	 * @param y Linha inicial para desenhar o texto
	 * @param largura Largura máxima
	 */
	public static void drawText(Graphics g, String texto, int x, int y, int largura) {
		String[] palavras = StringUtils.split(texto);
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D dimensoesEspaco = fm.getStringBounds(" ", g);
		int xpos = 0;
		int ypos = fm.getAscent();
		for (String palavra: palavras) {
			if (palavra.equals("\t")) {
				double w = dimensoesEspaco.getWidth() * 4;
				if (xpos + w < largura) {
					xpos += w;
				} else {
					xpos = 0;
					ypos += dimensoesEspaco.getHeight();
				}
			} else {
				if (palavra.equals("\n")) {
					xpos = 0;
					ypos += dimensoesEspaco.getHeight();
				} else {
					Rectangle2D r = fm.getStringBounds(palavra, g);
					if (xpos + r.getWidth() < largura) {
						g.drawString(palavra, x + xpos, y + ypos);
					} else {
						xpos = 0;
						ypos += dimensoesEspaco.getHeight();
						g.drawString(palavra, x + xpos, y + ypos);
					}
					xpos += r.getWidth();
				}
			}
		}
	}

}
