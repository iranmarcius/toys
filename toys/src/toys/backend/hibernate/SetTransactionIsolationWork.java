/*
 * Criado em 12/08/2010 09:15:26
 */

package toys.backend.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

/**
 * Este work seta o nível de isolamento da transação na conexão de uma sessão.
 * @author Iran
 */
public class SetTransactionIsolationWork implements Work {
	private int level;

	public SetTransactionIsolationWork(int level) {
		super();
		this.level = level;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		if (connection.getTransactionIsolation() != level)
			connection.setTransactionIsolation(level);
	}

}
