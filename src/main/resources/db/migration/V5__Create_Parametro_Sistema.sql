 CREATE TABLE tb_parametro_sistema (
    id_parametro_sistema number(19,0) generated as identity,
    prazo_prestacao_conta number(19,2),
    prazo_aplicacao number(19,2),
    data_limite_prestacao_conta DATE,
    data_limite_concessao DATE,
    data_limite_aplicacao_anual DATE,
    teto_nota_fiscal number(19,2),
    teto_suprimento number(19,2),
    status_sistema varchar(255),
    teto_suprimento_DIMAP_DEA_SA number(19,2),
    primary key (id_parametro_sistema)
 )