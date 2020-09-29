package toys.application;

import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Este processo apaga arquivos no diretório temporário do sistema cujo nome corresponda à expressão regular informada e
 * cuja idade seja maior que o tempo especificado. O diretório temporário do sistema é obtido à partir da chamada ao método
 * {@link FileUtils#getTempDirectory()} do Apache Commons-IO. Na execução deste job dois valores devem ser informados no contexto:
 * <ul>
 * 	<li><b>regex:</b> a expressão regular que será utilizada para filtrar os nomes dos arquivos a serem removidos.</li>
 * 	<li><b>maxAge:</b> a idade máxima em milissegundos que um arquivo poderá ser mantido quando o processo for executado.
 * 	Qualquer arquivo com idade superior à informada será deletado.</li>
 * 	<li><b>debug:</b> este parâmetro é opcional. Se for informado como TRUE os arquivos não serão apagados, apenas listados no log.</li>
 * </ul>
 *
 * @author Iran
 */
public class DeleteTemporaryFilesJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String regex = data.getString("regex");
        long maxAge = data.getLongValueFromString("maxAge");
        boolean debug = data.getBooleanValueFromString("debug");

        logger.info("Iniciando exclusao de arquivos temporarios. regex{}, idade maxima={}ms", regex, maxAge);

        // Filtra os arquivos que se enquadrem no critério de deleção
        File[] tempFiles = FileUtils.getTempDirectory().listFiles(f ->
            f.isFile() && f.canWrite() && (System.currentTimeMillis() - f.lastModified() > maxAge) && f.getName().matches(regex)
        );

        if (tempFiles != null) {
            logger.info("{} arquivos temporarios encontrados.", tempFiles.length);
            for (File f : tempFiles)
                if (!debug) {
                    try {
                        Files.delete(f.toPath());
                        logger.info("{} deletado com sucesso.", f.getAbsolutePath());
                    } catch (IOException e) {
                        logger.warn("Problemas deletando arquivo {}.", f.getAbsolutePath(), e);
                    }
                } else {
                    logger.info("Arquivo temporario encontrado: {}", f.getAbsolutePath());
                }
        } else {
            logger.info("Nenhum arquivo temporario encontrado.");
        }
        logger.info("Exclusao de arquivos temporarios finalizada.");
    }

}
