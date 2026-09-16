# Desafio Técnico — Place TI

Resolução do desafio técnico do processo seletivo da Place TI: uma API em Spring Boot e uma tela
em Angular para cadastrar cidades e comércios.

## Tecnologias

**Backend:** Java 21, Spring Boot 3.2.5, Spring Data JPA, Spring Validation, MapStruct, Lombok, H2
**Testes:** JUnit 5, Mockito, AssertJ
**Frontend:** Angular 17 (standalone components), PrimeNG 17, PrimeFlex

## Rodando o projeto

Precisa de JDK 21, Maven e Node 18+.

**Backend** — sobe em `http://localhost:8080/placeti` (o `/placeti` vem do context path no
`application.yml`):

```bash
cd backend-spring-boot
mvn clean install
mvn spring-boot:run
```

**Frontend** — sobe em `http://localhost:4200`:

```bash
cd frontend-angular
npm install
npm start
```

O backend precisa estar compilado antes de abrir a tela. A URL da API fica em
`src/app/environments/environment.ts`, caso precise apontar para outro lugar.

## Modelo

```
┌─────────────────┐              ┌──────────────────────┐
│     Cidade      │  1        N  │      Comercio        │
├─────────────────┤◄─────────────├──────────────────────┤
│ id (PK)         │              │ id (PK)              │
│ nome            │              │ nome_comercio        │
│ uf              │              │ nome_responsavel     │
│ capital         │              │ tipo_comercio (enum) │
└─────────────────┘              │ id_cidade (FK)       │
                                 └──────────────────────┘
```

Uma cidade tem vários comércios, cada comércio fica em uma cidade só. O lado dono do
relacionamento é o `Comercio`, com um `@ManyToOne`.

O `TipoComercio` é um enum gravado como texto (`FARMACIA`, `PADARIA`, `POSTO_GASOLINA`,
`LANCHONETE`).

## Endpoints

Base: `http://localhost:8080/placeti`

### Cidades

| Método | Rota | O que faz | Retorno |
|---|---|---|---|
| `GET` | `/cidades` | Lista todas | `200` |
| `GET` | `/cidades/{id}` | Busca uma | `200` |
| `POST` | `/cidades` | Cadastra | `201` |
| `PUT` | `/cidades` | Altera (id vai no corpo) | `200` |
| `DELETE` | `/cidades/{id}` | Exclui | `204` |

```json
{
  "id": 11,
  "nome": "Blumenau",
  "uf": "SC",
  "capital": false
}
```

No POST o `id` não vai.

### Comércios

| Método | Rota | O que faz | Retorno |
|---|---|---|---|
| `GET` | `/comercios` | Lista todos | `200` |
| `GET` | `/comercios/{id}` | Busca um | `200` |
| `GET` | `/comercios/cidade/{idCidade}` | Lista os de uma cidade | `200` |
| `POST` | `/comercios` | Cadastra | `201` |
| `PUT` | `/comercios` | Altera (id vai no corpo) | `200` |
| `DELETE` | `/comercios/{id}` | Exclui | `204` |

```json
{
  "id": 1,
  "nomeComercio": "Padaria Central",
  "nomeResponsavel": "Maria Silva",
  "tipoComercio": "PADARIA",
  "idCidade": 1
}
```

A cidade entra só pelo `idCidade`, não pelo objeto inteiro.

## Como o código está organizado

O fluxo é sempre Controller → Service → Repository. O controller só cuida do HTTP; toda regra
(conferir se o registro existe, amarrar o comércio à cidade) mora no service.

```
controller/   endpoints REST
service/      regras de negócio
repository/   acesso ao banco via Spring Data
mapper/       conversão entidade <-> DTO (MapStruct)
dto/          o que realmente trafega na API
model/        entidades JPA
config/       CORS
exception/    tratamento de erro centralizado
```

### Por que DTO e não a entidade direto

Duas razões práticas. A primeira é não deixar o modelo de banco vazar para fora: mudar uma
coluna não deveria quebrar o contrato da API. A segunda é mais concreta — se eu devolvesse
`Comercio` direto, viria a `Cidade` inteira aninhada junto (e o proxy lazy do Hibernate no meio
do caminho). Com o DTO, vai só o `idCidade`.

Os DTOs são `record`, que já resolvem imutabilidade e boilerplate.

### MapStruct

Gera o código de conversão em tempo de compilação, evitando aquele monte de `get`/`set`
repetido.

No `ComercioMapper` a conversão DTO → entidade ignora o campo `cidade` de propósito. Mapper não
deveria conhecer repositório, então quem busca a cidade pelo id é o `ComercioService` — e é lá
também que ele estoura exceção se a cidade não existir, evitando gravar comércio órfão.

Um detalhe do `pom.xml`: Lombok e MapStruct precisam ser declarados juntos no
`annotationProcessorPaths`, com o `lombok-mapstruct-binding`. Sem isso os dois processadores
brigam — o MapStruct roda antes do Lombok gerar os getters e acaba produzindo mapper vazio.

### CORS

O front está na 4200 e a API na 8080. Sem liberar CORS o navegador barra tudo, e o pior é que a
tela fica vazia sem erro nenhum na aplicação — só aparece no console do browser. A liberação
está em `config/CorsConfig.java`.

## Validações

Feitas com Spring Validation nos DTOs, acionadas pelo `@Valid` no controller.

| Campo | Regra |
|---|---|
| `nome` (cidade) | obrigatório, até 100 caracteres |
| `uf` | obrigatório, exatamente 2 caracteres |
| `capital` | obrigatório |
| `nomeComercio` | obrigatório, até 100 caracteres |
| `nomeResponsavel` | obrigatório, até 100 caracteres |
| `tipoComercio` | obrigatório |
| `idCidade` | obrigatório |

## Tratamento de erro

O `GlobalExceptionHandler` traduz as exceções em resposta HTTP decente, em vez de devolver
stack trace:

- Registro não encontrado → `404` com `{ "mensagem": "Cidade não encontrada para o id: 99" }`
- Validação falhou → `400` com um mapa campo/erro, tipo `{ "nome": "O nome da cidade é obrigatório" }`

O segundo formato é proposital: assim o front consegue destacar exatamente o campo que veio
errado.

## Testes

```bash
cd backend-spring-boot
mvn test
```

Testes de service com JUnit 5 e Mockito. Repositório e mapper são mockados, então os testes
rodam rápido e validam só a regra — sem subir Spring nem encostar no banco.

Cobrem busca por id (achando e não achando), listagem, inclusão, alteração e exclusão. Nos
casos de erro a verificação é dupla: além de checar que a exceção foi lançada, confere que o
`save`/`delete` não chegou a ser chamado.

No `ComercioServiceTest` tem ainda o cenário de tentar cadastrar comércio apontando para cidade
inexistente, que precisa falhar antes de gravar qualquer coisa.

## Banco

H2 em memória, recriado a cada start pelo `schema.sql` e populado pelo `data.sql` (10 cidades e
4 comércios de exemplo).

Console em `http://localhost:8080/placeti/h2-console`:

| | |
|---|---|
| JDBC URL | `jdbc:h2:mem:database` |
| Usuário | `admin` |
| Senha | `admin` |

## Postman

O `Desafio.postman_collection.json` na raiz tem todas as requisições, separadas em duas pastas
(Cidades e Comércios). Só importar com o backend rodando.

## Checklist do desafio

**Backend**

- [x] Entidade `Comercio` com id, nome, responsável e tipo
- [x] Relacionamento Cidade 1 → N Comercio
- [x] Enum com os quatro tipos pedidos
- [x] Controller → Service → Repository
- [x] Métodos do `CidadeController` implementados
- [x] Métodos do `ProjetoService` implementados
- [x] Spring Validation (`@NotNull`, `@Size`, `@NotBlank`)
- [x] Testes unitários de service com JUnit + Mockito
- [x] Comércio completo: controller, service e repository

**Frontend**

- [x] DTOs criados, sem expor a entidade de banco
- [x] Métodos do `ProjetoService` implementados
- [x] Tabela mostrando os dados vindos da API
- [x] Adicionar, alterar e excluir funcionando
