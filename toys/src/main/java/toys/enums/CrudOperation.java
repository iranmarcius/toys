package toys.enums;

public enum CrudOperation {
    CREATE("Criação"),
    READ("Leitura"),
    UPDATE("Atualização"),
    DELETE("Exclusão");

    private final String descricao;

    CrudOperation(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
