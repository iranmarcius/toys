package toys.tests.junit;

import com.unboundid.ldap.sdk.LDAPException;
import org.junit.BeforeClass;
import org.junit.Test;
import toys.LDAPUtils;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LDAPTest {
    private static LDAPUtils ldapUtilsAcad;
    private static LDAPUtils ldapUtilsCorp;
    private static final String USERNAME = "testeacad";
    private static final String PASSWORD = "";

    @BeforeClass
    public static void inicializar() {

        ldapUtilsAcad = new LDAPUtils(
            "adele-2.unitoledo.acad",
            "Administrador@unitoledo.acad",
            "KLyz+ami1AsVCROZUhJjaw==",
            "DC=unitoledo,DC=acad",
            "(sAMAccountName=%s)"
        );

        ldapUtilsCorp = new LDAPUtils(
            "adnet.unitoledo.corp",
            "Administrador@unitoledo.corp",
            "KLyz+ami1AsVCROZUhJjaw==",
            "DC=unitoledo,DC=corp",
            "(sAMAccountName=%s)"
        );

    }

    @Test
    public void testAuthAcad() throws LDAPException {
        var entry = ldapUtilsAcad.pesquisar("teste-acad");
        String erro = ldapUtilsAcad.autenticar(entry, "testeacad");
        assertNull(erro);
    }

    @Test
    public void testAuthCorp() throws LDAPException {
        var entry = ldapUtilsCorp.pesquisar("teste-corp");
        String erro = ldapUtilsCorp.autenticar(entry, "testecorp");
        assertNull(erro);
    }

}
