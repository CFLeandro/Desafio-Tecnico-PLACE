
-- O H2 é em memória, então as tabelas são recriadas a cada start da aplicação.
-- Comercio tem que cair antes de Cidade por causa da foreign key.
drop table if exists Comercio;
drop table if exists Cidade;

create table Cidade(
  id int not null AUTO_INCREMENT,
  nome varchar(100) not null,
  uf varchar(2) not null,
  capital boolean not null,  
  PRIMARY KEY ( ID )
);

create table Comercio(
  id int not null AUTO_INCREMENT,
  nome_comercio varchar(100) not null,
  nome_responsavel varchar(100) not null,
  tipo_comercio varchar(20) not null,
  id_cidade int not null,
  PRIMARY KEY ( ID ),
  CONSTRAINT fk_comercio_cidade FOREIGN KEY (id_cidade) REFERENCES Cidade(id)
);
