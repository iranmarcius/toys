package toys.tests.junit;

import com.unboundid.ldap.sdk.LDAPException;
import org.junit.BeforeClass;
import org.junit.Test;
import toys.LDAPUtils;
import toys.exceptions.ToysLDAPException;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.*;

public class LDAPTest {
    private static final String USERNAME = "testeacad";
    private static final String PASSWORD = "";
    private static LDAPUtils ldapUtilsAcad;
    private static LDAPUtils ldapUtilsCorp;

    @BeforeClass
    public static void inicializar() {

        ldapUtilsAcad = new LDAPUtils(
            "adele-2.unitoledo.acad",
            "Administrador@unitoledo.acad",
            "KLyz+ami1AsVCROZUhJjaw==",
            "DC=unitoledo,DC=acad",
            null
        );

        ldapUtilsCorp = new LDAPUtils(
            "adnet.unitoledo.corp",
            "Administrador@unitoledo.corp",
            "KLyz+ami1AsVCROZUhJjaw==",
            "DC=unitoledo,DC=corp",
            null
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
        String erro = ldapUtilsCorp.authenticate(entry, "senhacorp");
        assertNull(erro);
    }

    @Test
    public void testChangePasswordAcad() throws LDAPException, GeneralSecurityException, ToysLDAPException {
        ldapUtilsAcad.changePassword("teste-acad", "senhaacad", false);
        assertTrue(true);
    }

    @Test
    public void testChangePasswordCorp() throws LDAPException, GeneralSecurityException, ToysLDAPException {
        ldapUtilsCorp.changePassword("teste-corp", "senhacorp", false);
        assertTrue(true);
    }

}
