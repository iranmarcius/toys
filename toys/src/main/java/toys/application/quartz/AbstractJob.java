package toys.application.quartz;


import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implenentação básica de um Job do Quartz contendo algumas propriedades e métodos úteis.
 *
 * @author Iran Marcius
 * @since 11/2020
 */
public abstract class AbstractJob implements Job {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Método de conveniência para obter valores do contexto da execução do job.
     *
     * @param key Nome do parâmetro.
     * @return Retorna o valor encontrado para o tipo informado.
     */
    @SuppressWarnings("unchecked")
    protected <T> T getParamValue(String key, JobExecutionContext ctx, Class<T> type) {
        T value;
        var data = ctx.getJobDetail().getJobDataMap();
        if (type.equals(String.class))
            value = (T) data.getString(key);
        else if (type.equals(Integer.class))
            value = (T) data.getIntegerFromString(key);
        else if (type.equals(Long.class))
            value = (T) data.getLongFromString(key);
        else if (type.equals(Boolean.class))
            value = (T) data.getBooleanFromString(key);
        else if (type.equals(Double.class))
            value = (T) data.getDoubleFromString(key);
        else if (type.equals(Float.class))
            value = (T) data.getFloatFromString(key);
        else
            value = (T) data.get(key);
        return value;
    }

    /**
     * Retorna uma lista de valores inteiros que deve ser informada como números separados por vírgula.
     *
     * @see #getParamValue(String, JobExecutionContext, Class)
     */
    protected List<Integer> getIntList(String key, JobExecutionContext ctx) {
        String s = getParamValue(key, ctx, String.class);
        List<Integer> l = null;
        if (StringUtils.isNotBlank(s)) {
            var ss = s.split(" *, *");
            l = new ArrayList<>();
            for (String i : ss)
                l.add(Integer.valueOf(i));
        }
        return l;
    }

}
