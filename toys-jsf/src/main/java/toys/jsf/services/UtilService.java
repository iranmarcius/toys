package toys.jsf.services;

import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class UtilService implements Serializable {
    private static final long serialVersionUID = -3650138754375217026L;
    private Boolean dispositivoMovel;

    public UtilService() {
        super();
    }

    /**
     * Retorna se a requisição foi feita a partir de um dispositivo móvel.
     * @return boolean
     */
    public boolean isDispositivoMovel() {
        if (dispositivoMovel == null) {
            String userAgent = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("User-Agent");
            dispositivoMovel = StringUtils.containsIgnoreCase(userAgent, "Mobile");
        }
        return dispositivoMovel;
    }

}