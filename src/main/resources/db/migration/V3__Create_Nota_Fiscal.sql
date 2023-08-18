 CREATE TABLE tb_nota_fiscal (
    id_nota_fiscal number(19,0) generated as identity,
    matricula_suprido varchar2(255 char),
    numero_processo_sei varchar2(255 char),
    nome_suprido varchar2(255 char),
    chave_acesso_nf_e varchar2(255 char),
    tipo_despesa varchar2(255 char),
    elemento_despesa varchar2(255 char),
    tipo_documento_fiscal varchar2(255 char),
    numero_documento_fiscal varchar2(255 char),
    valor_total number(19,2),
    data_aplicacao DATE,
    data_documento DATE,
    status varchar(255),
    fornecedor_id_fornecedor number(19,0),
    primary key (id_nota_fiscal),
    constraint FK_tb_nota_fiscal_fornecedor_id_fornecedor foreign key (fornecedor_id_fornecedor) references tb_fornecedor
 )