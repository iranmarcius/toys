package toys.constants;

/**
 * Biblioteca de constantes para operações relacionadas a servidores LDAP.
 * @author Iran
 */
public interface LDAPConsts {

	/**
	 * Porta padrão do servidor LDAP.
	 */
	int LDAP_PORT = 389;

	/**
	 * Porta segura do servidor LDAP.
	 */
	int LDAPS_PORT = 636;

	/**
	 * Conta desabilitada.
	 */
	int ADS_UF_ACCOUNTDISABLE = 0x00000002;

	/**
	 * Senha não requerida.
	 */
	int ADS_ADS_UF_PASSWD_NOTREQD = 0x00000020;

	/**
	 * Usuário não pode alterar a senha da conta.
	 */
	int ADS_UF_PASSWD_CANT_CHANGE = 0x00000040;

	/**
	 * Usuário pode enviar uma senha criptografada.
	 */
	int ADS_UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED = 0x00000080;

	/**
	 * Conta padrão de usuário.
	 */
	int ADS_UF_NORMAL_ACCOUNT = 0x00000200;

	/**
	 * Conta de computador do domínio.
	 */
	int ADS_UF_WORKSTATION_TRUST_ACCOUNT = 0x00001000;

	/**
	 * Senha da conta nunca expira.
	 */
	int ADS_UF_DONT_EXPIRE_PASSWD = 0x00010000;

	/**
	 * Senha expirada.
	 */
	int ADS_UF_PASSWORD_EXPIRED = 0x00800000;

	String IC_AD_INVALID_CREDENTIALS = "52e";
	String IC_USER_NOT_FOUND = "525";
	String IC_PASSWORD_EXPIRED = "532";
	String IC_ACCOUNT_DISABLED = "533";
	String IC_ACCOUNT_EXPIRED = "701";
	String IC_USER_MUST_RESET_PASSWORD = "773";

	// Nomes de atributos de contas
	String LA_ACC_NAME = "sAMAccountName";
	String LA_DN = "distinguishedName";
	String LA_CN = "cn";
	String LA_USER_ACC_CONTROL = "userAccountControl";
	String LA_PWD_LAST_SET = "pwdLastSet";
	String LA_CHANGED = "whenChanged";
	String LA_DISPLAY_NAME = "displayName";
	String LA_DESCRIPTION = "description";
	String LA_BAD_PWD_COUNT = "badPwdCount";
	String LA_BAD_PWD_TIME = "badPasswordTime";
	String LA_ACC_EXPIRES = "accountExpires";
	String LA_GIVEN_NAME = "givenName";
	String LA_PRINCIPAL_NAME = "userPrincipalName";
	String LA_UNICODE_PWD = "unicodePwd";

	/**
	 * Valor base para cálculos de conversão de datas entre valores do LDAP e do Java.
	 */
	long UNIXTS = (((1970-1601) * 365) - 3 + Math.round((1970-1601)/4)) * 86400l;

}
