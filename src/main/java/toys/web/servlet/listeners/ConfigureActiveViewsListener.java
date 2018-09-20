package toys.web.servlet.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Este listener configura o limite de views mantidas.
 * TODO Voltar a esta classe pois provavelmente ela não é mais necesária.
 * @author Iran
 */
//@WebListener
public class ConfigureActiveViewsListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
//		event.getSession().setAttribute(ViewScopeManager.ACTIVE_VIEW_MAPS_SIZE, 1);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// Nada precisa acontecer aqui.
	}

}
