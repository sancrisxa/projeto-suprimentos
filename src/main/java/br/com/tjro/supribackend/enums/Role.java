package br.com.tjro.supribackend.enums;

public enum Role {
    GESTOR_UNIDADE("GESTOR_UNIDADE"),
    SERVIDOR_SOF("SERVIDOR_SOF"),
    SERVIDOR_SGP("SERVIDOR_SGP"),
    SUPRIDO("SUPRIDO");

    private String descricao;

    Role(String descricao) {

        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
