CREATE TABLE tb_usuario (
    id_usuario number(19,0) generated as identity,
    nome varchar2(255 char) not null,
    matricula varchar2(255 char) not null,
    cpf varchar2(255 char),
    primary key (id_usuario),
    constraint UK_tb_usuario_matricula unique (matricula)
 );