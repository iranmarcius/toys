/*
 * Criado em 12/08/2010 09:15:26
 */

package toys.persistence.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.ReturningWork;

/**
 * Este work seta o nível de isolamento da transação na conexão de uma sessão.
 * @author Iran
 */
public class SetTransactionIsolationWork implements ReturningWork<Integer> {
	private int level;

	public SetTransactionIsolationWork() {
		super();
	}

	public SetTransactionIsolationWork(int level) {
		this();
		this.level = level;
	}

	@Override
	public Integer execute(Connection connection) throws SQLException {
		int oldLevel = connection.getTransactionIsolation();
		connection.setTransactionIsolation(level);
		return oldLevel;
	}

}
