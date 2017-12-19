package toys.persistence.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import toys.CollectionToys;

/**
 * Este tipo armazena propriedades de entidades declaradas com tipo <code>List&lt;String&gt;</code> em colunas do tipo <code>VARCHAR</code> no banco de dados
 * separando os elementos por vírgula.
 * @author Iran
 */
public class StringListType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[] {StringType.INSTANCE.sqlType()};
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<List> returnedClass() {
        return List.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String columnName = names[0];
        String columnValue = rs.getString(columnName);
        if (columnValue != null) {
            String[] ss = columnValue.split(",");
            List<String> l = new ArrayList<>(ss.length);
            for (String s: ss)
                if (s != null && s.trim().length() > 0)
                    l.add(s);
            return l;
        } else {
            return null;
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value != null)
            st.setString(index, CollectionToys.toString((List<?>)value, ","));
        else
            st.setNull(index, Types.VARCHAR);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object deepCopy(Object value) throws HibernateException {
        if (value != null) {
            List<String> l1 = (List<String>)value;
            List<String> l2 = new ArrayList<>(l1.size());
            l2.addAll(l1);
            return l2;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

}
