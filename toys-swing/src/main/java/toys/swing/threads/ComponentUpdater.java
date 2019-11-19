package toys.swing.threads;

import toys.application.runnables.ToysRunnable;

/**
 * Runnable para atualização de componentes de tela em uma thread própria.
 *
 * @author Iran Marcius
 */
public class ComponentUpdater extends ToysRunnable implements Runnable {

    /**
     * Referência para o component que será atualizado.
     */
    protected ComponentUpdate component;

    /**
     * Construtor default.
     */
    public ComponentUpdater() {
        super();
    }

    /**
     * Construtor com inicialização de propriedades.
     *
     * @param component Componente a ser verificado
     */
    public ComponentUpdater(ComponentUpdate component) {
        this();
        setComponent(component);
    }

    /**
     * Executa a ação de atualização do componente.
     */
    @Override
    protected void execute() {
        component.updateComponents();
    }

    /**
     * Seta a referência para o componente que será atualizado.
     *
     * @param component Componente
     */
    public void setComponent(ComponentUpdate component) {
        this.component = component;
    }

    /**
     * Retorna a referência do componente atualizado.
     *
     * @return <code>{@link ComponentUpdate ComponentUpdate}</code>
     */
    public ComponentUpdate getComponent() {
        return component;
    }

}
