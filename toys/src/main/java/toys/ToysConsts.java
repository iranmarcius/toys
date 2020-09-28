package toys;

/**
 * Constantes de propósitos gerais.
 */
public class ToysConsts {

    // Constantes relacionadas a segurança.
    public static final String SECURITY_ALGORITHM = "security.algorithm";
    public static final String SECURITY_USERNAME_MINLENGTH = "security.username.minLength";
    public static final String SECURITY_CREDENTIAL_MINLENGTH = "security.password.minLength";
    public static final String SECURITY_AUTHORITIES = "security.authorities";

    // Claims utilizadas em tokens JWT
    public static final String JWT_CLAIM_AUTHORITIES = "aut";
    public static final String JWT_CONTEXT = "ctx";

    // Constantes relacionadas a configurações de e-mail
    public static final String EMAIL_SMTP_ADDRESS = "email.smtp.address";
    public static final String EMAIL_STORE_ADDRESS = "email.store.address";
    public static final String EMAIL_STORE_PROTOCOL = "email.store.protocol";

    public static final String CHARSET_ENCODING = "charset.encoding";
    public static final String APPLICATION_DEBUG = "application.debug";

    // Bancos de dados e JDBC

    public static final long JTDS_MINTS = -6847793999296L;
    public static final long JTDS_MAXTS = 253402307999704L;
    public static final String MSSQL_GETDATE_FUNC = "getdate";

    // Nomes de atributos para fins diversos

    public static final String ATTR_PREFERENCES = "session.user.preferences";
    public static final String ATTR_OWNER = "session.user.name";
    public static final String ATTR_USER = "session.user.authenticationData";
    public static final String ATTR_MENU = "application.menu";
    public static final String ATTR_SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String ATTR_SERVLET_ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ATTR_SESSION_MESSAGE = "toys.web.sessionMessage";
    public static final String ATTR_XSTREAM = "application.xstream";
    public static final String ATTR_PAGINA_TROCA_SENHA = "webapp.pagina.alteracaoSenha";
    public static final String ATTR_SENHA_TROCADA = "toys.security.senhaTrocada";
    public static final String ATTR_CONTENT = "toys.web.servlet.content";
    public static final String ATTR_CONTENT_TYPE = "toys.web.servlet.contentType";
    public static final String ATTR_CONTENT_FILENAME = "toys.web.servlet.contentFilename";
    public static final String ATTR_LDAP_INVALID_CREDENTIALS_DETAIL = "ldap.invalidCredentials.errorCode";
    public static final String ATTR_LOGIN_MODULE_MESSAGE = "loginModule.message";
    public static final String ATTR_MOBILE = "session.dispositivoModel";

    // Formatação

    public static final String DATE_BR = "%1$td/%1$tm/%1$tY";
    public static final String DATE_SQL = "%1$tY-%1$tm-%1$td";
    public static final String TIME_HM = "%1$tH:%1$tM";
    public static final String TIME_HMS = "%1$tH:%1$tM:%1$tS";
    public static final String TIME_HMSL = "%1$tH:%1$tM:%1$tS.%1$tL";
    public static final String TIMESTAMP = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL";

    // Máscaras
    public static final String MASK_CPF = "###.###.###-##";
    public static final String MASK_CNPJ = "##.###.###/####-##";
    public static final String MASK_IPTE = "#####.##### #####.###### #####.###### # ##############";

    // Padrões para funções de conversão
    public static final String PATTERN_DATE_BR = "dd/MM/yyyy";
    public static final String PATTERN_DATETIME_BR = "dd/MM/yyyy HH:mm";
    public static final String PATTERN_DATE_SQL = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm";
    public static final String PATTERN_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

    // Expressões regulares
    public static final String RE_EMAIL = "^\\w[\\w\\-.]+[\\w\\-]@[\\w]+(\\.\\w+)+$";
    public static final String RE_NUMBER = "^\\d+$";
    public static final String RE_DECIMAL_NUMBER = "^\\d+([,.]\\d+)?$";
    public static final String RE_CURRENCY_BR = "^(\\d{1,3}\\.)*\\d{1,3},\\d+$";
    public static final String RE_ZIP = "^\\d{5}\\-?\\d{3}$";
    public static final String RE_TIME = "^\\d{1,3}(:\\d{1,2}){1,2}(\\.\\d{1,3})?$";
    public static final String RE_TIME_SEC = "^\\d{1,3}(:\\d{1,2}){2,2}(\\.\\d{1,3})?$";
    public static final String RE_TIME_MS = "^\\d{1,3}(:\\d{1,2}){2,2}\\.\\d{1,3}$";
    public static final String RE_SIMPLE_TIME = "^\\d{1,2}(:\\d{1,2}){0,2}$";
    public static final String RE_IPV4 = "^(\\d{1,3}\\.){3,3}\\d{1,3}$";
    public static final String RE_SEPARADORES_PONTUACAO = "[.\\-/() ]";
    public static final String RE_MANTER_SOMENTE_NUMEROS = "[^0-9]+";
    public static final String RE_DATE_BR = "^\\d{1,2}/\\d{1,2}/\\d{4}$";
    public static final String RE_DATE_SQL = "^\\d{4}\\-\\d{2}\\-\\d{2}$";
    public static final String RE_TIMESTAMP = "^\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:{2}\\.\\d{3}$";
    public static final String RE_CPF = "^\\d{3}(\\.?\\d{3}){2}-?\\d{2}$";
    public static final String RE_CNPJ = "^\\d{2}(\\.?\\d{3}){2}/\\d{4}\\-?\\d{2}$";

    // Postas de servidor LDAP
    public static final int LDAP_PORT = 389;
    public static final int LDAPS_PORT = 636;

    // Códigos de erro LDAP
    public static final int ADS_UF_ACCOUNTDISABLE = 0x00000002;
    public static final int ADS_ADS_UF_PASSWD_NOTREQD = 0x00000020;
    public static final int ADS_UF_PASSWD_CANT_CHANGE = 0x00000040;
    public static final int ADS_UF_ENCRYPTED_TEXT_PASSWORD_ALLOWED = 0x00000080;
    public static final int ADS_UF_NORMAL_ACCOUNT = 0x00000200;
    public static final int ADS_UF_WORKSTATION_TRUST_ACCOUNT = 0x00001000;
    public static final int ADS_UF_DONT_EXPIRE_PASSWD = 0x00010000;
    public static final int ADS_UF_PASSWORD_EXPIRED = 0x00800000;

    // Códigos de detalhes do erro 49 (Invalid Credentials) de servidores LDAP
    public static final String IC_USER_NOT_FOUND = "525";
    public static final String IC_AD_INVALID_CREDENTIALS = "52e";
    public static final String IC_INVALID_TIME = "530";
    public static final String IC_INVALID_WORKSTATION = "531";
    public static final String IC_CREDENTIAL_EXPIRED = "532";
    public static final String IC_ACCOUNT_DISABLED = "533";
    public static final String IC_LOGON_REQUEST_NOT_GRANTED = "534";
    public static final String IC_ACCOUNT_EXPIRED = "701";
    public static final String IC_USER_MUST_RESET_CREDENTIAL = "773";
    public static final String IC_ACCOUNT_LOCKED = "775";

    // Nomes de atributos de contas LDAP
    public static final String LA_ACC_NAME = "sAMAccountName";
    public static final String LA_IS_DELETED = "isDeleted";
    public static final String LA_DN = "distinguishedName";
    public static final String LA_CN = "cn";
    public static final String LA_USER_ACC_CONTROL = "userAccountControl";
    public static final String LA_PW_LAST_SET = "pwdLastSet";
    public static final String LA_CHANGED = "whenChanged";
    public static final String LA_DISPLAY_NAME = "displayName";
    public static final String LA_DESCRIPTION = "description";
    public static final String LA_BAD_PW_COUNT = "badPwdCount";
    public static final String LA_BAD_PW_TIME = "badPasswordTime";
    public static final String LA_ACC_EXPIRES = "accountExpires";
    public static final String LA_GIVEN_NAME = "givenName";
    public static final String LA_PRINCIPAL_NAME = "userPrincipalName";
    public static final String LA_UNICODE_PW = "unicodePwd";

    // Bits para serem utilizado no user account control
    public static final int LDAP_UACC_DISABLED =             0x000002;
    public static final int LDAP_UACC_BLOCK =                0x000010;
    public static final int LDAP_UACC_PASSWD_NOTREQD =       0x000020;
    public static final int LDAP_UACC_PASSWD_CANT_CHANGE =   0x000040;
    public static final int LDAP_UACC_NORMAL_ACCOUNT =       0x000200;
    public static final int LDAP_UACC_DONT_EXPIRE_PASSWORD = 0x010000;
    public static final int LDAP_UACC_PASSWORD_EXPIRED =     0x800000;

    // Valor base para cálculos de conversão de datas entre valores do LDAP e do Java.
    public static final long LDAP_UNIXTS = (((1970 - 1601) * 365) - 3 + Math.round((1970d - 1601d) / 4d)) * 86400L;

    // Nomes de cabeçalhos HTTP
    public static final String HTTP_HEADER_REFERER = "Referer";
    public static final String HTTP_HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
    public static final String HTTP_HEADER_ACCESS_TOKEN = "Access-Token";
    public static final String HTTP_HEADER_AUTH_OPTIONS = "Auth-Options";
    public static final String HTTP_HEADER_USER_AGENT = "User-Agent";

    private ToysConsts() {
    }

}
