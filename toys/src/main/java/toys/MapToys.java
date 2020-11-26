package toys;

import java.util.Map;

public class MapToys {

    private MapToys() {
    }

    /**
     * Retona o mapa informado como uma string com chaves e valores como no exemplo:
     * <code>{k1=v1, k2=v2, ..., kn=vn}</code>
     *
     * @param map Mapa a ser convertido.
     * @return <code>String</code>
     */
    public static String asString(Map<?, ?> map) {
        if (map == null)
            return null;
        var sb = new StringBuilder("{");
        for (Map.Entry<?, ?> entry : map.entrySet())
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        if (sb.length() > 2)
            sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

}
