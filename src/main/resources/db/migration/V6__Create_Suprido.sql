CREATE TABLE tb_suprido (
    id_suprido number(19,0) generated as identity,
    nome varchar2(255 char) not null,
    matricula varchar2(255 char) not null,
    primary key (id_suprido),
    constraint UK_tb_suprido_matricula unique (matricula)
 )