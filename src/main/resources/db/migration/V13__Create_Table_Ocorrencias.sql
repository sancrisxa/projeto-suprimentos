 CREATE TABLE tb_ocorrencias (
    id_ocorrencias number(19,0) generated as identity,
    descricao varchar2(255 char),
    prazo number(19,2),
    data_ocorrencia DATE,
    data_final DATE,
    status_ocorrencia varchar(255),
    fornecedor_id_fornecedor number(19,0),
    primary key (id_ocorrencia),
    constraint FK_tb_ocorrencias_fornecedor_id_fornecedor foreign key (fornecedor_id_fornecedor) references tb_fornecedor
 )