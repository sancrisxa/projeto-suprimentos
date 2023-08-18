 CREATE TABLE tb_item_nota_fiscal (
    id_item_nota_fiscal number(19,0) generated as identity,
    descricao varchar2(255 char),
    quantidade number(19,2),
    valor_unitario number(19,2),
    desconto number(19,2),
    valor_total number(19,2),
    nota_fiscal_id_nota_fiscal number(19,0),
    primary key (id_item_nota_fiscal),
    constraint FK_tb_item_nota_fiscal_nota_fiscal_id_nota_fiscal foreign key (nota_fiscal_id_nota_fiscal) references tb_nota_fiscal
 )