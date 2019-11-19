package toys.swing.listeners;

import java.awt.*;
import java.util.EventListener;

/**
 * Evento utilizado em operações de autenticação e validações para troca de senha.
 *
 * @author Iran
 * @since 05/2009
 */
public interface AuthListener extends EventListener {

    /**
     * Realiza a operação de autenticação.
     *
     * @param owner    Referência para a janela que gerou o evento
     * @param username Nome do usuário
     * @param senha    Senha do usuário
     * @return Retorna se a operação de autenticação teve sucesso ou não.
     */
    boolean autenticar(Window owner, String username, String senha);

    /**
     * Faz a troca da senha podendo ainda realizar validações da senha atual e da nova senha.
     *
     * @param owner            Referência para a janela que gerou o evento
     * @param senhaAtual       Senha atual.
     * @param novaSenha        Nova senha.
     * @param confirmacaoSenha Confirmação da nova senha.
     * @return Retorna se a nova senha pode ser utilizada.
     */
    boolean trocarSenha(Window owner, String senhaAtual, String novaSenha, String confirmacaoSenha);

}
