package toys.application.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toys.BeanToys;
import toys.LocaleToys;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe utilitária para inicialização do Quartz Scheduler agendando tarefas.
 *
 * @author Iran Marcius
 * @since 11/2020
 */
public class QuartzUtils {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Properties environment;

    public QuartzUtils() {
    }

    public QuartzUtils(Properties environment) {
        this.environment = environment;
    }

    /**
     * Cria e inicializa o scheduler agendando os jobs informados.
     *
     * @param jobsConfigs Relação de objetos do tipo {@link QuartzJobConfig} com os jobs
     *                    que serão agendados no scheduler criado.
     * @return Retorna o scheduler criado.
     */
    public Scheduler init(Collection<QuartzJobConfig> jobsConfigs) {
        logger.info("Inicializando agendador para {} tarefa(s).", jobsConfigs.size());

        // Instancia e configura os jobs
        try {
            var scheduler = StdSchedulerFactory.getDefaultScheduler();
            for (QuartzJobConfig config : jobsConfigs)
                schedule(config, scheduler);
            scheduler.start();
            logger.info("Agendador de tarefas iniciado com sucesso.");
            return scheduler;
        } catch (SchedulerException e) {
            logger.error("Erro inicializando agendador de tarefas.", e);
            return null;
        }

    }

    /**
     * Método de conveniência para inicializar a partir de um objeto de propriedades.
     *
     * @param props Propriedades.
     * @see #init(Collection)
     * @see #configsFromProps(Properties)
     */
    public Scheduler init(Properties props) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return init(configsFromProps(props));
    }

    /**
     * Método de conveniência para inicilizar o scheduler lendo as configurações de um arquivo existente no classpath.
     *
     * @param filename Nome do arquivo.
     * @see #init(Properties)  
     */
    public Scheduler init(String filename) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var props = new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream(filename));
        return init(props);
    }

    /**
     * Método de conveniência para inicializar o scheduler lendo as tarefas do arquivo de configurações padrão
     * <code>jobs.properties</code>.
     * @see #init(String)
     */
    public Scheduler init() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        return init("quartz.properties");
    }

    /**
     * Acrescenta variáveis de ambiente que podem ser utilizadas como  valores para substituição
     * em parâmetros de tarefas.
     *
     * @param name  Nome.
     * @param value Valor.
     * @return Retorna a instância atual.
     */
    public QuartzUtils withEnv(String name, Object value) {
        if (environment == null)
            environment = new Properties();
        environment.put(name, value);
        return this;
    }

    /**
     * Agenda uma tarefa.
     */
    @SuppressWarnings("unchecked")
    private void schedule(QuartzJobConfig jobConfig, Scheduler scheduler) {
        logger.info("Agendando tarefa: {}.", jobConfig);
        try {

            // Obtém as configurações do job
            JobDataMap jobData = new JobDataMap();
            for (Map.Entry<String, String> entry : jobConfig.getParams().entrySet()) {
                String cfg = entry.getKey();
                String valor = entry.getValue();
                if (StringUtils.isNotBlank(valor)) {
                    Object value = processValue(valor);
                    jobData.put(cfg, value);
                    logger.info("\tparametro processado: {}={}", cfg, value);
                }
            }

            var jobClazz = (Class<? extends Job>) Class.forName(jobConfig.getJobClassName());
            JobBuilder jb = JobBuilder.newJob().ofType(jobClazz);
            if (!jobData.isEmpty())
                jb.usingJobData(jobData);
            JobDetail jobDetail = jb.build();

            // Configura a trigger
            var cronExpr = jobConfig.getCronSchedule();
            var delay = jobConfig.getOneTimeRunDelay();
            if (StringUtils.isBlank(cronExpr) && jobConfig.getOneTimeRunDelay() != null) {
                cronExpr = oneTimeRunCronExpr(delay);
                delay = null;
                logger.info("\tagenda de execucao unica={}", cronExpr);
            }
            Trigger trigger = null;
            if (StringUtils.isNotBlank(cronExpr)) {
                var tb = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cronExpr));
                if (delay != null)
                    tb.startAt(new Date(System.currentTimeMillis() + delay));
                trigger = tb.build();
            }

            // O agendamento será feito apenas se a trigger tiver um agendamento
            if (trigger != null)
                scheduler.scheduleJob(jobDetail, trigger);
            else
                logger.warn("Nao ha agenda para a tarefa {}.", jobConfig.getName());

        } catch (ClassNotFoundException e) {
            logger.error("Erro instanciando a tarefa {}.", jobConfig.getName(), e);
        } catch (SchedulerException e) {
            logger.error("Ocorreu um erro no agendamento da tarefa {}.", jobConfig.getName(), e);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            logger.error("Ocorreu um erro no processamento dos parametros", e);
        }

    }

    /**
     * Verifica se o valor é do tipo String e, caso seja, processa as substituições existentes, caso haja alguma.
     */
    private Object processValue(Object value) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {

        // Processa somente valores string
        if (!(value instanceof String))
            return value;

        StringBuilder sb = new StringBuilder((String) value);
        int i;
        int j;
        while ((i = sb.indexOf("${")) > -1 && (j = sb.indexOf("}")) > -1) {
            String expr = sb.substring(i + 2, j);
            String replacement;
            if (expr.startsWith("const:")) {
                String constPath = expr.substring(8, expr.length() - 1);
                int k = constPath.lastIndexOf('.');
                String className = constPath.substring(0, k);
                String fieldName = constPath.substring(k + 1);
                Class<?> clazz = Class.forName(className);
                replacement = clazz.getField(fieldName).get(clazz).toString();
            } else {
                replacement = environment.getProperty(expr);
            }

            if (replacement != null)
                sb.replace(i, j + 1, replacement);

        }

        return sb.toString();
    }

    /**
     * Converte um objeto de proprieddes em uma coleção de configurações de jobs.
     *
     * @param props Configurações dos jobs. Serão consideradas somente as propriedades iniciadas por
     *              <code>job.</code>, que deverão ser seguidas pelo nome do job como a seguir<br>
     *              <br>
     *              <code>job.&lt;jobName&gt;.&lt;configName&gt;=valor</code><br>
     *              <br>
     *              por exemplo: <code>job.nomeDoJob.jobClass=org.company.JobClass</code><br>
     *              <br>
     *              Os nomes das configurações consideradas são equivalentes às propriedades da classe
     *              {@link QuartzJobConfig}:
     *              <ul>
     *              <li><b>jobClassName:</b> classe de execução do job.</li>
     *              <li><b>cronSchedule:</b> expressão de agendamento no extilo do cron.</li>
     *              <li><b>oneTimeRunDelay:</b> delay em milisegundos para jobs que serão executados somente uma vez.</li>
     *              </ul>
     *              Demais configurações especificadas serão adicinados como parâmetros adicionais do job.
     * @return <code>Collection&lt;{@link QuartzJobConfig}&gt;</code>
     */
    public Collection<QuartzJobConfig> configsFromProps(Properties props) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var configs = new HashMap<String, QuartzJobConfig>();
        var cfgFieldNames = Arrays.stream(QuartzJobConfig.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            String propName = entry.getKey().toString();
            if (!propName.startsWith("job."))
                continue;
            var jobPropName = propName.substring(4); // remove o .job do início
            int i = jobPropName.indexOf('.');
            String jobName = jobPropName.substring(0, i);
            String cfgName = jobPropName.substring(i + 1);
            if (cfgName.equals("params"))
                continue;
            var config = configs.computeIfAbsent(jobName, QuartzJobConfig::new);
            if (cfgFieldNames.contains(cfgName)) {
                BeanToys.setValue(config, cfgName, entry.getValue());
            } else {
                if (config.getParams() == null)
                    config.setParams(new HashMap<>());
                config.getParams().put(cfgName, entry.getValue().toString());
            }
        }
        return configs.values();
    }

    /**
     * Cria um agendamento com execução única para disparar após decorridos os milissegundos especificados no offset.
     */
    private String oneTimeRunCronExpr(Integer offset) {
        java.util.Calendar c = java.util.Calendar.getInstance(LocaleToys.BRAZIL);
        c.add(java.util.Calendar.SECOND, offset);
        return String.format("%d %d %d * * ?", c.get(java.util.Calendar.SECOND), c.get(java.util.Calendar.MINUTE), c.get(Calendar.HOUR_OF_DAY));
    }
}
