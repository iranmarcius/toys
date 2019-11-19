package toys.swt.forms;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import toys.application.ProgressNotifier;
import toys.swt.SWTUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação de uma janela de progresso genérica.
 *
 * @author Iran
 */
public class ProgressMonitor extends Dialog implements ProgressNotifier {
    private Shell dialogShell;
    private ProgressBar progressBar;
    private Label mensagem;
    private Button cancelarButton;
    private boolean cancelarPressinado;
    private boolean cancelado;
    private ProgressRunnable runnable;

    /**
     * Cria uma instância da janela de progresso.
     *
     * @param parent Referência para o pai.
     * @param titulo Título da dialog.
     */
    public ProgressMonitor(Shell parent, String titulo) {
        super(parent, SWT.NONE);
        dialogShell = new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);

        // previne o ESC de fechad a dialog
        dialogShell.addTraverseListener(event -> {
            if (event.detail == SWT.TRAVERSE_ESCAPE)
                event.doit = false;
        });

        GridLayout thisLayout = new GridLayout();
        thisLayout.marginWidth = 10;
        thisLayout.marginHeight = 10;
        dialogShell.setLayout(thisLayout);
        dialogShell.setText(titulo != null ? titulo : "Progresso");

        {
            GridData progressBarLData = new GridData();
            progressBarLData.widthHint = 270;
            progressBarLData.horizontalAlignment = GridData.END;
            progressBarLData.verticalAlignment = GridData.BEGINNING;
            progressBarLData.heightHint = 25;
            progressBar = new ProgressBar(dialogShell, SWT.NONE);
            progressBar.setLayoutData(progressBarLData);
        }
        {
            GridData mensagemLData = new GridData();
            mensagemLData.horizontalAlignment = GridData.FILL;
            mensagem = new Label(dialogShell, SWT.NONE);
            mensagem.setLayoutData(mensagemLData);
        }
        {
            cancelarButton = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
            GridData cancelarButtonLData = new GridData();
            cancelarButtonLData.horizontalAlignment = GridData.CENTER;
            cancelarButton.setLayoutData(cancelarButtonLData);
            cancelarButton.setText("&Cancelar");
            cancelarButton.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent event) {
                    cancelarPressinado = true;
                }
            });
        }
        dialogShell.pack();

        cancelado = false;
        cancelarPressinado = false;
        runnable = new ProgressRunnable();
    }

    public void open() {
        Display display = dialogShell.getDisplay();
        SWTUtils.center(dialogShell, display);
        dialogShell.open();
        while (!dialogShell.isDisposed())
            if (!display.readAndDispatch())
                display.sleep();
    }

    /**
     * Inicializa a janela de progresso setando o texto a ser exibido e o total. O mínimo
     * e a posição do progresso serão ajustados para zero.
     *
     * @param texto Título da janela
     * @param total Valor máximo da barra
     */
    public void start(String texto, int total) {
        if (texto != null)
            runnable.params.put("texto", texto);
        runnable.params.put("total", total);
        runnable.params.put("posicao", 0);
        dialogShell.getDisplay().syncExec(runnable);
    }

    /**
     * Atualiza a posição da barra de progresso e o texto.
     *
     * @param quantidade Quantidade de passos a serem somados à posição atual.
     * @param texto      Texto a ser exibido. Caso seja nulo será ignorado.
     */
    public void step(int quantidade, String texto) {
        runnable.params.put("passo", quantidade);
        if (texto != null)
            runnable.params.put("texto", texto);
        dialogShell.getDisplay().syncExec(runnable);
    }

    /**
     * Seta o texto abaixo da barra de progresso.
     *
     * @param texto Texto
     */
    public void text(String texto) {
        runnable.params.put("texto", texto);
        dialogShell.getDisplay().syncExec(runnable);
    }

    /**
     * Seta a flag de cancelamento.
     */
    public void cancel() {
        this.cancelado = true;
    }

    /**
     * Retorna o estado da flag de cancelamento.
     *
     * @return <code>boolean</code>
     */
    public boolean isCancelled() {
        if (cancelarPressinado) {
            cancelarPressinado = false;
            runnable.params.put("confirmarCancelamento", null);
            dialogShell.getDisplay().asyncExec(runnable);
        }
        return cancelado;
    }

    /**
     * Fecha o monitor de progresso.
     */
    public void end() {
        runnable.params.put("finalizar", null);
        dialogShell.getDisplay().syncExec(runnable);
    }

    /*
     * ============================================================================
     */

    /**
     * Esta é uma classe auxiliar interna utilizada para atualizar a interface a partir de
     * outra thread.
     */
    private class ProgressRunnable implements Runnable {
        public Map<String, Object> params;

        public ProgressRunnable() {
            super();
            params = new HashMap<>();
        }

        @Override
        public void run() {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getKey().equals("titulo")) {
                    dialogShell.setText((String) entry.getValue());
                } else if (entry.getKey().equals("total")) {
                    progressBar.setMaximum((Integer) entry.getValue());
                } else if (entry.getKey().equals("texto")) {
                    mensagem.setText((String) entry.getValue());
                } else if (entry.getKey().equals("posicao")) {
                    progressBar.setSelection((Integer) entry.getValue());
                } else if (entry.getKey().equals("passo")) {
                    progressBar.setSelection(progressBar.getSelection() + (Integer) entry.getValue());
                } else if (entry.getKey().equals("confirmarCancelamento")) {
                    cancelado = MessageDialog.openConfirm(dialogShell, "Confirmação", "Confirma o cancelamento da operação?");
                } else if (entry.getKey().equals("cancelamento")) {
                    cancelarButton.setEnabled((Boolean) entry.getValue());
                } else if (entry.getKey().equals("finalizar")) {
                    dialogShell.close();
                }
            }

            // esvazia o mapa
            params.clear();
        }
    }

}
