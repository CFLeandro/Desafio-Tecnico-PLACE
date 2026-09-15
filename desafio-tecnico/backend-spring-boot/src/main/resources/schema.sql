
drop table if exists Cidade;

create table Cidade(
  id int not null AUTO_INCREMENT,
  nome varchar(100) not null,
  uf varchar(2) not null,
  capital boolean not null,  
  PRIMARY KEY ( ID )
);
