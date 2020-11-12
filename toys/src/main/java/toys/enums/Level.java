package toys.enums;

public enum Level {
    SUCCESS("Sucesso"),
    INFO("Informação"),
    WARN("Aviso"),
    ERROR("Erro"),
    FATAL("Erro fatal");

    private final String descricao;

    Level(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
