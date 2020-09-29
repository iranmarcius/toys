package toys.application.runnables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementação básica para suporte a runnables.
 *
 * @author Iran Marcius
 */
public abstract class ToysRunnable implements Runnable {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Flag indicando se a execução da thread deve ser finalizada. Enquanto
     * o valor desta flag for <code>TRUE</code> a thread estará em execução.
     */
    protected boolean executando;

    /**
     * Intervalo de execução da ação.
     */
    protected long intervalo;

    /**
     * Construtor default.
     */
    public ToysRunnable() {
        super();
        intervalo = 250;
        executando = false;
    }

    /**
     * Execução da thread.
     */
    public void run() {
        try {
            while (executando) {
                execute();
                Thread.sleep(intervalo);
            }
        } catch (InterruptedException e) {
            logger.error("Erro inesperado.", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Execução da opreração.
     */
    protected abstract void execute();

    public void setIntervalo(long intervalo) {
        this.intervalo = intervalo;
    }

    public long getIntervalo() {
        return intervalo;
    }

    public boolean isExecutando() {
        return executando;
    }

    public void setExecutando(boolean executando) {
        this.executando = executando;
    }

}
