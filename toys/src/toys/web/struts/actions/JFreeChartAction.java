/*
 * Departamento de Desenvolvimento - ISIC Brasil 
 * Todos os direitos reservados
 * Criado em 15/12/2005
 */

package toys.web.struts.actions;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * Esta ação define a implementação básica de ações para criação de gráficos
 * com o JFreeChart.
 * @author Iran Marcius
 */
public abstract class JFreeChartAction extends StreamAction {
	private static final long serialVersionUID = 1827601223110193226L;

	/**
	 * Cria o stream com os dados do gráfico.
	 */
	@Override
	protected final void createStream() throws IOException {
		
		JFreeChart chart = createChart();
		BufferedImage bi = chart.createBufferedImage(getDimension().width, getDimension().height);
		
		// cria a imagem de acordo com o contentType especificado
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (contentType.equalsIgnoreCase("image/jpeg")) {
			ChartUtilities.writeBufferedImageAsJPEG(out, bi);
		} else if (contentType.equalsIgnoreCase("image/png")) {
			ChartUtilities.writeBufferedImageAsPNG(out, bi);
		}
		
		inputStream = new ByteArrayInputStream(out.toByteArray());
	}
	
	/**
	 * Cria e retorna o gráfico.
	 * @return <code>JFreeChart</code>
	 */
	protected abstract JFreeChart createChart();
	
	/**
	 * Retorna as dimensões do gráfico.
	 * @return <code>Dimension</code>
	 */
	protected abstract Dimension getDimension();
	
}
