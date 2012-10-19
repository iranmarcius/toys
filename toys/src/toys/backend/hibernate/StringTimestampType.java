/*
 * Criado em 23/03/2010 14:50:56
 */

package toys.backend.hibernate;


import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * Este é um tipo de usuário para persistir campos de timestamp como strings.
 * @author Iran
 */
public class StringTimestampType implements UserType {
	private int[] sqlTypes = {Types.VARCHAR};

	@Override
	public int[] sqlTypes() {
		return sqlTypes;
	}

	@Override
	public Class<?> returnedClass() {
		return Timestamp.class;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		String s = rs.getString(names[0]);
		return StringUtils.isNotBlank(s) ? Timestamp.valueOf(s) : null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if (value == null)
			st.setNull(index, Types.VARCHAR);
		else
			st.setString(index, value.toString());
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return null;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return null;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x != null && y != null && x instanceof Timestamp && y instanceof Timestamp)
			return ((Timestamp)x).getTime() == ((Timestamp)y).getTime();
		else
			return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return null;
	}

}
