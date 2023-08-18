package br.com.tjro.supribackend.enums;

public enum StatusOcorrencia {

    ADVERTENCIA("Advertência"),
    MULTA("Multa"),
    IMPEDIMENTO_LICITAR("Impedimento licitar");

    private String descricao;
    StatusOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
