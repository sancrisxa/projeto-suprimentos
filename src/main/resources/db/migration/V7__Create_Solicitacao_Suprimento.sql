 CREATE TABLE tb_solicitacao_suprimento (
    id_solicitacao_suprimento number(19,0) generated as identity,
    numero_sei varchar2(255 char),
    data_solicitacao DATE,
    matricula_suprido varchar2(255 char),
    valor_total number(19,2),
    status varchar(255),
    suprido_id_suprido number(19,0),
    primary key (id_solicitacao_suprimento),
    constraint FK_tb_solicitacao_suprimento_suprido_id_suprido foreign key (suprido_id_suprido) references tb_suprido
 )