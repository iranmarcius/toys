/*
 * Criado em 31/08/2011 14:43:34 
 */

package toys.backend.hibernate;


/**
 * Este bean é utilizado commo chave no mapa de session factories da classe {@link HibernateSession}.
 * Ele possui os nomes do arquivo de configurações e mapeamento de POJOs. 
 * @author Iran
 */
public class SessionFactoryParams {
	private String properties;
	private String mappings;
	
	public SessionFactoryParams(String properties, String mappings) {
		super();
		this.properties = properties;
		this.mappings = mappings;
	}
	
	@Override
	public String toString() {
		return String.format("SessionFactoryParams [properties=%s, mappings=%s]", properties, mappings);
	}
	
	@Override
	public int hashCode() {
		final int prime = 619;
		int result = 1;
		result = prime * result + ((mappings == null) ? 0 : mappings.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SessionFactoryParams))
			return false;
		SessionFactoryParams other = (SessionFactoryParams) obj;
		if (mappings == null) {
			if (other.mappings != null)
				return false;
		} else if (!mappings.equals(other.mappings))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	public String getProperties() {
		return properties;
	}
	
	public String getMappings() {
		return mappings;
	}

}
