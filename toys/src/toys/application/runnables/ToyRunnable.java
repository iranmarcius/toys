package toys.application.runnables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementação básica para suporte a threads.
 * @author Iran Marcius
 */
public abstract class ToyRunnable implements Runnable {
    private final Logger logger = LogManager.getLogger();

    /**
     * Flag indicando se a execução da thread deve ser finalizada. Enquanto
     * o valor desta flag for <code>TRUE</code> a thread estará em execução.
     */
    protected boolean finished;

    /**
     * Intervalo de execução da ação.
     */
    protected long interval;

    /**
     * Construtor default.
     */
    public ToyRunnable() {
        super();
        interval = 250;
        finished = false;
    }

    /**
     * Execução da thread.
     */
    public void run() {
        try {
            while (!isFinished()) {
                execute();
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    /**
     * Método a ser executado. As classes que estenderem esta, deverão implementar este
     * método.
     */
    protected abstract void execute();

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean cancelled) {
        this.finished = cancelled;
    }

}
