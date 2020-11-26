package toys;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Implementa métodos utilitários para operações com objetos.
 *
 * @author Iran Marcius
 */
public final class BeanToys {

    private BeanToys() {
    }

    /**
     * Pesquisa no array de objetos do tipo {@link Method} o método cujo nome corresponda
     * ao padrão informado.
     *
     * @param methods Array de métodos que será pesquisado.
     * @param regex   Expressão regular que será utilizada para localizar o método.
     * @return Retorna uma referência para o método procurado ou <code>null</code> caso nenhum
     * método seja encontrado ou não seja possível acessá-lo.
     */
    public static Method findMethod(Method[] methods, String regex) {
        for (Method m : methods)
            if (m.getName().matches(regex) && Modifier.isPublic(m.getModifiers()))
                return m;
        return null;
    }

    /**
     * Pesquisa dentro de um array com objetos do tipo {@link Field} um cujo nome seja
     * igual ao nome informado.
     *
     * @param fields Array de objetos do tipo {@link Field}.
     * @param nome   Nome do campo desejado.
     * @return Retorna o campo cujo nome corresponda ao informado ou <code>null</code> caso
     * não exista nenhum ou não seja possível ler seu valor.
     */
    public static Field findField(Field[] fields, String nome) {
        for (Field f : fields)
            if (f.getName().equals(nome) && Modifier.isPublic(f.getModifiers()))
                return f;
        return null;
    }

    /**
     * Retorna o tipo da propriedade do objeto informado.
     *
     * @param obj  Objeto
     * @param prop Nome da propriedade
     * @return Retorna o tipo da propriedade
     */
    public static Class<?> getPropType(Object obj, String prop) {
        String regex = getGetterRegExpr(prop);
        Method m = findMethod(obj.getClass().getMethods(), regex);
        if (m != null) {
            return m.getReturnType();
        } else {
            Field f = findField(obj.getClass().getFields(), prop);
            if (f != null)
                return f.getType();
            else
                return null;
        }
    }

    /**
     * Retorna o valor de uma propriedade de um objeto. O método tentará, primeiro, obter o valor
     * da propriedade através de um getter. Caso não exista ou não esteja acessível, o método
     * tentará obter o valor diretamente do campo declarado.
     *
     * @param obj  Objeto de onde será lido o valor da propriedade
     * @param prop Nome da propriedade desejada. Ex.:
     *             <ul>
     *             	<li><code>nome = obj.getNome()</code></li>
     *             	<li><code>situacao.descricao = obj.getSituacao().getDescricao()</code></li>
     *             </ul>
     * @return Retorna o valor da propriedade desejada ou <code>null</code> caso não seja possível
     * acessar o valor ou caso não exista nenhum método ou campo com o nome informado no objeto.
     */
    public static Object getValue(Object obj, String prop) throws IllegalAccessException, InvocationTargetException {

        String restante = null;
        int i = prop.indexOf('.');
        if (i > -1) {
            restante = prop.substring(i + 1);
            prop = prop.substring(0, i);
        }

        // Tenta obter o valor pelo método
        String reGetter = getGetterRegExpr(prop);
        Method m = findMethod(obj.getClass().getMethods(), reGetter);
        if (m != null) {
            Object o = m.invoke(obj);
            if (restante == null)
                return o;
            else
                return getValue(o, restante);
        }

        // Caso não tenha conseguido obter o valor através do método, tenta obter diretamente
        // do campo
        Field f = findField(obj.getClass().getFields(), prop);
        if (f != null) {
            Object o = f.get(obj);
            if (restante == null)
                return o;
            else
                return getValue(o, restante);
        }

        // Caso nenhum dos dois métodos tenha funcionado, retorna um valor nulo
        return null;
    }


    /**
     * Seta o valor de uma propriedade de um objeto. O método tentará setar o valor primeiramente
     * através do método setter. Caso não exista ou não seja acessível, tentará setar o valor diretamente
     * no campo declarado.
     *
     * @param obj      Objeto com a propriedade que será modificada.
     * @param propName Nome da propriedade que será modificada.
     * @param value    Valor que será atribuído
     */
    public static void setValue(Object obj, String propName, Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        String restante = null;
        int i = propName.indexOf('.');
        if (i > -1) {
            restante = propName.substring(i + 1);
            propName = propName.substring(0, i);
        }

        // tenta setar o valor através do setter
        String setter = getSetterName(propName);
        Method m = findMethod(obj.getClass().getMethods(), "^" + setter + "$");
        if (m != null && m.getParameterCount() == 1) {
            if (restante == null) {
                Object valueToSet;
                Class<?> paramType = m.getParameterTypes()[0];
                if (value.getClass().equals(paramType)) {
                    valueToSet = value;
                } else {
                    if (value instanceof String) {
                        if (paramType.equals(Integer.class))
                            valueToSet = Integer.parseInt(value.toString());
                        else
                            throw new ClassCastException();
                    } else {
                        throw new ClassCastException();
                    }
                }
                m.invoke(obj, valueToSet);
            } else {
                Class<?> clazz = getPropType(obj, propName);
                if (clazz != null) {
                    Object o = clazz.getConstructor().newInstance();
                    setValue(o, restante, value);
                    m.invoke(obj, o);
                }
            }
        }

    }

    /**
     * Popula as propriedades de um bean com os valores fornecidos no array.
     *
     * @param bean       Objeto cujos valores serão preenchidos
     * @param properties Nome das propriedades que serão setadas no objeto
     * @param values     Array com os valores que serão atribuídos às propriedades do bean
     * @param startIndex Índice a partir do qual os valores do array serão lidos
     */
    public static void populate(Object bean, String[] properties, Object[] values, int startIndex) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        for (int i = 0; i < properties.length; i++)
            setValue(bean, properties[i], values[startIndex + i]);
    }

    /**
     * Formata uma string utilizando o formato e os valores das propriedades informadas.
     *
     * @param obj     Objeto com os valores
     * @param formato Formato
     * @param props   Propriedades do objeto que serão lidas.
     * @return <code>String</code>
     */
    public static String format(Object obj, String formato, String... props) throws IllegalAccessException, InvocationTargetException {
        Object[] valores = new Object[props.length];
        for (int i = 0; i < props.length; i++)
            valores[i] = getValue(obj, props[i]);
        return String.format(formato, valores);
    }

    /**
     * Verifica se o objeto passado é nulo. Se for, retorna uma nova instância da classe informada.
     *
     * @param o     Objeto
     * @param clazz Classe que será instanciada caso o objeto seja nulo
     * @return <code>Object</code>
     */
    public static Object instanceIfNull(Object o, Class<?> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return o == null ? clazz.getConstructor().newInstance() : o;
    }

    /**
     * Retorna se dois objetos são iguals realizando a comparação através do método
     * <code>equals</code> destes, mesmo sendo valores nulos.
     *
     * @param o1 Primeiro objeto
     * @param o2 Segundo objeto
     * @return Retorna uma flag indicando se os elementos são iguais ou não,
     * considerando-os como iguais caso ambos sejam nulos.
     */
    public static boolean isEqual(Object o1, Object o2) {
        if (o1 != null)
            return o1.equals(o2);
        else
            return o2 == null;
    }

    /**
     * Converte a caixa de todas as propriedades informadas considerando que elas sejam do tipo String.
     */
    public static void toUpperCase(Object o, String... props) {
        var logger = LoggerFactory.getLogger(BeanToys.class);
        if (o == null || props == null)
            return;
        for (String prop : props) {
            String getterName = "get" + StringUtils.capitalize(prop);
            String setterName = "set" + StringUtils.capitalize(prop);
            try {
                String s = (String) o.getClass().getMethod(getterName).invoke(o);
                o.getClass().getMethod(setterName, String.class).invoke(o, s.toUpperCase());
            } catch (Exception e) {
                logger.warn("Erro setando valor para a propriedade {} do objeto.", prop, e);
            }

        }
    }

    /**
     * Retorna uma regular expression do método getter para a propriedade informada.
     */
    private static String getGetterRegExpr(String prop) {
        return "^(get|is)" + StringUtils.capitalize(prop) + "$";
    }

    /**
     * Retorna uma regular expression do método setter para a propriedade informada.
     */
    private static String getSetterName(String prop) {
        return "set" + StringUtils.capitalize(prop);
    }

}
