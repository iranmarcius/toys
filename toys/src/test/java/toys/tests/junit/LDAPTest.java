package toys.tests.junit;

import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import org.junit.BeforeClass;
import org.junit.Test;
import toys.LDAPUtils;
import toys.exceptions.ToysLDAPException;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testSearch() throws ToysLDAPException, LDAPException {
        assertNotNull(ldapUtilsAcad.query("teste-acad"));
        assertNull(ldapUtilsAcad.query("teste-corp"));
        assertNotNull(ldapUtilsCorp.query("teste-corp"));
        assertNull(ldapUtilsCorp.query("teste-acad"));
    }

    @Test
    public void testAuthAcad() throws LDAPException, ToysLDAPException {
        var entry = ldapUtilsAcad.query("teste-acad");
        String erro = ldapUtilsAcad.authenticate(entry, "sucesso");
        assertNull(erro);
    }

    @Test
    public void testAuthCorp() throws LDAPException, ToysLDAPException {
        var entry = ldapUtilsCorp.query("teste-corp");
        String erro = ldapUtilsCorp.authenticate(entry, "testecorp");
        assertNull(erro);
    }

}
