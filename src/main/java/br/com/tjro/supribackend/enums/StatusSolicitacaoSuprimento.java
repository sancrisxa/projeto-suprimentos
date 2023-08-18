package br.com.tjro.supribackend.enums;

public enum StatusSolicitacaoSuprimento {
    CONCLUIDO("Concluído"),
    SOLICITADO( "Solicitado"),
    PRESTACAO( "Prestação"),
    EM_APROVACAO("Em Aprovação"),
    EM_ANALISE("Em Análise"),
    CADASTRADO("Cadastrado"),
    APROVADO("Aprovado");

    private String descricao;
    StatusSolicitacaoSuprimento(String descricao) {

        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

}
