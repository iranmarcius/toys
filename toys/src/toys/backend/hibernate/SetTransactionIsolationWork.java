/*
 * Criado em 12/08/2010 09:15:26
 */

package toys.backend.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

/**
 * Este work seta o nível de isolamento da transação na conexão de uma sessão.
 * o nível de isolamento anterior é armazenado na propriedade <code>oldLevel</code>.
 * @author Iran
 */
public class SetTransactionIsolationWork implements Work {
	private int level;
	private int oldLevel;

	public SetTransactionIsolationWork() {
		super();
	}

	public SetTransactionIsolationWork(int level) {
		super();
		this.level = level;
	}

	@Override
	public void execute(Connection connection) throws SQLException {
		oldLevel = connection.getTransactionIsolation();
		if (level != oldLevel)
			connection.setTransactionIsolation(level);
	}

	public SetTransactionIsolationWork change(int level) {
		setLevel(level);
		return this;
	}

	public SetTransactionIsolationWork restore() {
		setLevel(oldLevel);
		return this;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public int getOldLevel() {
		return oldLevel;
	}

}
