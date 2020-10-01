package toys.enums;

public enum CrudOperation {
    CREATE("Criacao"),
    READ("Leitura"),
    UPDATE("Atualizacao"),
    DELETE("Exclusao");

    private final String descricao;

    CrudOperation(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
