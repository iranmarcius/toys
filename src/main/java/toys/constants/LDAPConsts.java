package toys.constants;

/**
 * Biblioteca de constantes para operações relacionadas a servidores LDAP.
 * @author Iran
 */
public class LDAPConsts {

	/**
	 * Porta padrão do servidor LDAP.
	 */
	public static final int LDAP_PORT = 389;

	/**
	 * Porta segura do servidor LDAP.
	 */
	public static final int LDAPS_PORT = 636;

	/**
	 * Conta desabilitada.
	 */
	public static final int ADS_UF_ACCOUNTDISABLE = 0x00000002;

	/**
	 * Senha não requerida.
	 */
	public static final int ADS_ADS_UF_PASSWD_NOTREQD = 0x00000020;

	/**
	 * Usuário não pode alterar a senha da conta.
	 */
	public static final int ADS_UF_PASSWD_CANT_CHANGE = 0x00000040;

	/**
	 * Usuário pode enviar uma senha criptografada.
	 */
	public static final int ADS_UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED = 0x00000080;

	/**
	 * Conta padrão de usuário.
	 */
	public static final int ADS_UF_NORMAL_ACCOUNT = 0x00000200;

	/**
	 * Conta de computador do domínio.
	 */
	public static final int ADS_UF_WORKSTATION_TRUST_ACCOUNT = 0x00001000;

	/**
	 * Senha da conta nunca expira.
	 */
	public static final int ADS_UF_DONT_EXPIRE_PASSWD = 0x00010000;

	/**
	 * Senha expirada.
	 */
	public static final int ADS_UF_PASSWORD_EXPIRED = 0x00800000;

	public static final String IC_AD_INVALID_CREDENTIALS = "52e";
	public static final String IC_USER_NOT_FOUND = "525";
	public static final String IC_PASSWORD_EXPIRED = "532";
	public static final String IC_ACCOUNT_DISABLED = "533";
	public static final String IC_ACCOUNT_EXPIRED = "701";
	public static final String IC_USER_MUST_RESET_PASSWORD = "773";

	// Nomes de atributos de contas
	public static final String LA_ACC_NAME = "sAMAccountName";
	public static final String LA_DN = "distinguishedName";
	public static final String LA_CN = "cn";
	public static final String LA_USER_ACC_CONTROL = "userAccountControl";
	public static final String LA_PWD_LAST_SET = "pwdLastSet";
	public static final String LA_CHANGED = "whenChanged";
	public static final String LA_DISPLAY_NAME = "displayName";
	public static final String LA_DESCRIPTION = "description";
	public static final String LA_BAD_PWD_COUNT = "badPwdCount";
	public static final String LA_BAD_PWD_TIME = "badPasswordTime";
	public static final String LA_ACC_EXPIRES = "accountExpires";
	public static final String LA_GIVEN_NAME = "givenName";
	public static final String LA_PRINCIPAL_NAME = "userPrincipalName";
	public static final String LA_UNICODE_PWD = "unicodePwd";

	/**
	 * Valor base para cálculos de conversão de datas entre valores do LDAP e do Java.
	 */
	public static final long UNIXTS = (((1970-1601) * 365) - 3 + Math.round((1970d-1601d) / 4d)) * 86400l;

	private LDAPConsts() {
	}

}
