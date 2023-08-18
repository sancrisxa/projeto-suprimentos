 CREATE TABLE tb_elemento_despesa (
    id_elemento_despesa number(19,0) generated as identity,
    descricao varchar2(255 char),
    valor number(19,2),
    solicitacao_suprimento_id_solicitacao_suprimento number(19,0),
    primary key (id_elemento_despesa),
    constraint FK_tb_elemento_despesa_solicitacao_suprimento_id_solicitacao_suprimento foreign key (solicitacao_suprimento_id_solicitacao_suprimento) references tb_solicitacao_suprimento
 )