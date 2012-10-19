/*
 * Criado em 24/08/2004
 */

package toys.backend.hibernate;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.persistence.Entity;

import org.hibernate.JDBCException;
import org.hibernate.proxy.HibernateProxy;

/**
 * Esta classe possui métodos utilitários para trabalhar com o Hibernate.
 * @author Iran Marcius
 */
public class HibernateUtils {

	/**
	 * Retorna a mensagem de erro final gerada no JDBCException passado.
	 */
	public static String getMessage(JDBCException e) {
		SQLException lastException = e.getSQLException();
		while (lastException.getNextException() != null) {
			lastException = lastException.getNextException();
		}
		return lastException.getMessage();
	}

	/**
	 * Remove o proxy do Hibernate do objeto especificado. Caso o objeto possua propriedades
	 * do tipo {@link Collection} ou {@link Map}, o proxy também será removido de todos os objetos
	 * existentes nessas propriedades.
	 *
	 * @param obj Referência para o objeto cujo proxy será removido. Este objeto pode ser um
	 * bean, {@link Collection} ou {@link Map}.
	 *
	 * @param recursionLevel Nível de recursão para remoção do proxy. Indica, no caso de coleções
	 * ou propriedades com referências de coleções, quantos serão os níveis processados na remoção
	 * do proxy. <b>Zero</b> processa apenas o objeto ou a coleção de objetos do primeiro nível.
	 *
	 * @return Retorna uma referência para um novo objeto sem o proxy do Hibernate.
	 * @throws Exception
	 */
	public static Object removeProxy(Object obj, int recursionLevel) throws Exception {
		return removeProxyRecursive(obj, recursionLevel, 0);
	}

	/**
	 * Método recursivo utilizado pelo {@link #removeProxy(Object, int)}.
	 * @param obj
	 * @param recursionLevel
	 * @param currentLevel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object removeProxyRecursive(Object obj, int recursionLevel, int currentLevel) throws Exception {

		if (obj == null)
			return null;

		// tratamento para objetos que implementem a interface Collection
		if (obj instanceof Collection) {
			Collection nc = null;
			if (obj instanceof SortedSet)
				nc = new TreeSet();
			else if (obj instanceof Set)
				nc = new HashSet();
			else if (obj instanceof List)
				nc = new ArrayList();
			else
				nc = new Vector<Object>();

			Collection c = (Collection)obj;
			for (Object o: c)
				nc.add(removeProxyRecursive(o, recursionLevel, currentLevel));

			return nc;
		}

		// tratamento para objetos que implementem a interface Map
		if (obj instanceof Map) {
			Map<Object, Object> nm = null;
			if (obj instanceof SortedMap)
				nm = new TreeMap(((SortedMap)obj).comparator());
			else
				nm = new HashMap<Object, Object>();

			Map<Object, Object> m = (Map)obj;
			for (Object key: m.keySet())
				nm.put(key, removeProxyRecursive(m.get(key), recursionLevel, currentLevel));

			return nm;
		}

		// caso o objeto seja uma instância do HibernateProxy, remove o código e percorre seus campos
		// para determinar se existem coleções cujo proxy também deve ser removido
		Object o = obj;
		if (obj instanceof HibernateProxy) {
			HibernateProxy hp = (HibernateProxy)obj;
			o = hp.getHibernateLazyInitializer().getImplementation();
		}

		// incrementa o nível de recursão
		currentLevel++;

		// percorre todos métodos para determinar se existem collections
		Method[] metodos = o.getClass().getMethods();
		for (Method m: metodos) {

			// considera apenas os getters
			if (!m.getName().startsWith("get"))
				continue;

			// desconsidera os métodos que não retornem entidades ou collections
			Class<?> retType = m.getReturnType();
			if ((retType.getAnnotation(Entity.class) == null) &&
				!retType.isAssignableFrom(Set.class) &&
				!retType.isAssignableFrom(SortedSet.class) &&
				!retType.isAssignableFrom(List.class) &&
				!retType.isAssignableFrom(Map.class))
				continue;

			// obtém a relação para a collection e realiza a remoção do proxy
			Object oc = null;
			if (currentLevel <= recursionLevel) {
				oc = m.invoke(o);
				oc = removeProxyRecursive(oc, recursionLevel, currentLevel);
			}

			// obtém o método setter equivalente para atribuir a referência para a nova collection
			String setterName = "set" + m.getName().substring(3);
			Method setter = o.getClass().getMethod(setterName, retType);
			setter.invoke(o, oc);
		}

		return o;
	}

}
