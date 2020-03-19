package toys.tests.junit;

import com.unboundid.ldap.sdk.LDAPException;
import org.junit.BeforeClass;
import org.junit.Test;
import toys.LDAPUtils;

import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LDAPTest {
    private static LDAPUtils ldapUtils;
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    @BeforeClass
    public static void inicializar() {
        ldapUtils = new LDAPUtils(
            "adele-2.unitoledo.acad",
            "Administrador@unitoledo.acad",
            "KLyz+ami1AsVCROZUhJjaw==",
            "DC=unitoledo,DC=acad",
            "(sAMAccountName=%s)"
        );
    }

    @Test
    public void ldapAuthTest() throws LDAPException {
        var entry = ldapUtils.pesquisar(USERNAME);
        String erro = ldapUtils.autenticar(entry, PASSWORD);
        assertNull(erro);
    }

    @Test
    public void ldapChangePasswordTest() throws GeneralSecurityException, LDAPException {
        ldapUtils.alterarSenha(USERNAME, PASSWORD);
        assertTrue(Boolean.TRUE);
    }

}
