
INSERT INTO Cidade (nome, uf, capital) VALUES ('Brasília', 'DF', 1);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Goiânia', 'GO', 1);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Salvador', 'BA', 1);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Fortaleza', 'CE', 1);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Curitiba', 'PR', 1);
INSERT INTO Cidade (nome, uf, capital) VALUES ('São Paulo', 'SP', 1);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Rio de Janeiro', 'RJ', 1);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Anápolis', 'GO', 0);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Pouso Alegre', 'MG', 0);
INSERT INTO Cidade (nome, uf, capital) VALUES ('Volta Redonda', 'RJ', 0);

-- Alguns comércios só para já ter o que testar nos endpoints.
-- Os ids de cidade seguem a ordem dos inserts acima (1 = Brasília, 6 = São Paulo, 7 = Rio).
INSERT INTO Comercio (nome_comercio, nome_responsavel, tipo_comercio, id_cidade) VALUES ('Farmácia Popular', 'Ana Souza', 'FARMACIA', 1);
INSERT INTO Comercio (nome_comercio, nome_responsavel, tipo_comercio, id_cidade) VALUES ('Padaria Pão Quente', 'João Lima', 'PADARIA', 1);
INSERT INTO Comercio (nome_comercio, nome_responsavel, tipo_comercio, id_cidade) VALUES ('Posto Ipiranga', 'Carlos Dias', 'POSTO_GASOLINA', 6);
INSERT INTO Comercio (nome_comercio, nome_responsavel, tipo_comercio, id_cidade) VALUES ('Lanchonete do Zé', 'José Pereira', 'LANCHONETE', 7);
