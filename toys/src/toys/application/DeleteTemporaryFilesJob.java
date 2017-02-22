package toys.application;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Este processo apaga arquivos no diretório temporário do sistema cujo nome corresponda à expressão regular informada e
 * cuja idade seja maior que o tempo especificado. Na execução deste job dois valores devem ser informados no contexto:
 * <ul>
 * 	<li><b>regex:</b> a expressão regular que será utilizada para filtrar os nomes dos arquivos a serem removidos.</li>
 * 	<li><b>maxAge:</b> a idade máxima em milissegundos que um arquivo poderá ser mantido quando o processo for executado.
 * 	Qualquer arquivo com idade superior à informada será deletado.</li>
 * 	<li><b>debug:</b> este parâmetro é opcional. Se for informado como TRUE os arquivos não serão apagados, apenas listados no log.</li>
 * </ul>
 * @author Iran
 */
public class DeleteTemporaryFilesJob implements Job {
	private static final Log log = LogFactory.getLog(DeleteTemporaryFilesJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		String regex = data.getString("regex");
		Long maxAge = data.getLong("maxAge");
		Boolean debug = data.getBoolean("debug");

		log.info(String.format("Iniciando exclusao de arquivos temporarios. regex=%s, idade maxima=%dms", regex, maxAge));

		// Filtra os arquivos que se enquadrem no critério de deleção
		File[] tempFiles = FileUtils.getTempDirectory().listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.isFile() && f.canWrite() && (System.currentTimeMillis() - f.lastModified() > maxAge) && f.getName().matches(regex);
			}
		});

		log.info(String.format("%d arquivos temporarios encontrados.", tempFiles.length));
		for (File f: tempFiles)
			if (debug == null || !debug)
				if (f.delete())
					log.info(String.format("%s deletado com sucesso.", f.getAbsolutePath()));
				else
					log.warn(String.format("Problemas deletando arquivo %s.", f.getAbsolutePath()));
			else
				log.info(String.format("Arquivo temporario encontrado: %s", f.getAbsolutePath()));

		log.info("Exclusao de arquivos temporarios finalizada.");
	}

}
