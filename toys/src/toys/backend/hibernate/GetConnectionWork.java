/*
 * Criado em 12/01/2011 16:57:03
 */

package toys.backend.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 * Work para recuperar a conexão jdbc com o banco de dados à partir da sessão.
 * @author Iran
 */
public class GetConnectionWork implements Work {
	public Connection connection;

	public GetConnectionWork(Session hs) {
		super();
		hs.doWork(this);
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		this.connection = connection;
	}

}
