package toys;

import java.util.List;

/**
 * Esta classe é uma implementação simples de um par chave/valor para utilização geral.
 * Como ela armazena referências para o objeto pai e uma lista de objetos filhos, é
 * particularmente útil para armazenamento de estruturas de árvores.
 * @author Iran Marcius
 */
public class KeyValue<K, V> {
    private K key;
    private V value;
    private KeyValue<K, V> parent;
    private List<KeyValue<K, V>> children;

    public KeyValue() {
        super();
    }

    public KeyValue(K key, V value) {
        this();
        setKey(key);
        setValue(value);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public K setKey(K key) {
        K oldValue = this.key;
        this.key = key;
        return oldValue;
    }

    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public List<KeyValue<K, V>> getChildren() {
        return children;
    }

    public void setChildren(List<KeyValue<K, V>> children) {
        this.children = children;
    }

    public KeyValue<K, V> getParent() {
        return parent;
    }

    public void setParent(KeyValue<K, V> parent) {
        this.parent = parent;
    }

}
