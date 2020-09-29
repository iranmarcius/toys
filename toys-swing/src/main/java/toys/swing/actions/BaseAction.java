package toys.swing.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * Esta classe implementa uma ação básica com alguns métodos utilitários.
 *
 * @author Iran Marcius
 */
public abstract class BaseAction extends AbstractAction {
    private static final long serialVersionUID = 7506031976549858989L;
    protected final transient Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * Referência para o objeto que sofrerá a ação.
     */
    protected transient Object target;

    /**
     * Cria uma instância da classe setando suas propriedades.
     *
     * @param target Referência para o objeto sobre o qual a ação irá funcionar.
     */
    public BaseAction(Object target) {
        super();
        this.target = target;
    }

    /**
     * Cria uma instância da classe setando suas propriedades.
     *
     * @param target Referência para o objeto sobre o qual a ação irá funcionar.
     * @param button Referência para um {@link AbstractButton} que irá disparar a ação. Consulte
     *               o método {@link #setButton(AbstractButton)} para obter maiores detalhes sobre os processos
     *               executados quando essa referência é fornecida.
     */
    public BaseAction(Object target, AbstractButton button) {
        this(target);
        setButton(button);
    }

    /**
     * Retorna o valor de {@link #target target}.
     */
    public Object getTarget() {
        return target;
    }

    /**
     * Seta a referência para o {@link AbstractButton} que irá disparar a ação. Quando essa
     * referência é setada, o método {@link AbstractButton#setAction(Action) setAction} do botão
     * é invocado, mas quando isso acontece, propriedades do botão como texto, ícones e aceleradores,
     * são modificadas com as propriedades da ação. Por esse motivo, este método copia para a ação
     * essas propriedades do botão.
     *
     * @param button Referência para o botão que irá disparar a ação.
     */
    public void setButton(AbstractButton button) {
        putValue(Action.NAME, button.getText());
        putValue(Action.SMALL_ICON, button.getIcon());
        putValue(Action.MNEMONIC_KEY, button.getMnemonic());
        putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, button.getDisplayedMnemonicIndex());
        button.setAction(this);
    }

}
