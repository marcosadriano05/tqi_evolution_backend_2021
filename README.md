# TQI Evolution - Desafio

## Sobre o desafio

Criar um sistema para cadastro de empréstimos, esse sistema da a possibilidade do cliente se cadastrar com os seus dados pessoais.
Ao se cadastrar, o client pode fazer o login e nele pedir empréstimos, esses empréstimos podem ser consultados pelo cliente.

## Requisitos exigidos

:heavy_check_mark: Cadastro de clientes

- :heavy_check_mark: O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.

:heavy_check_mark: Login

- :heavy_check_mark: A autenticação será realizada por e-mail e senha.

:heavy_check_mark: Solicitação de empréstimo

- :heavy_check_mark: Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas.

- :heavy_check_mark: O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.

:heavy_check_mark: Acompanhamento das solicitações de empréstimo

- :heavy_check_mark: O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.

- :heavy_check_mark: Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.

- :heavy_check_mark: No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.

## Descrição do que foi feito

- O sistema foi feito baseado em uma API Rest.
- O sistema de autenticação e autorização foi feito através do spring security.
- Para regras de negócio, como o pedido de empréstimo, foi utilizado a metodologia de desenvolvimento TDD para gerar mais confiabilidade nas operações.
- Banco de dados utilizado foi o Postgresql.
- Para operações no banco, foi usada a especificação JPA.
- Container Docker que utiliza as imagens do Postgres e Openjdk para rodar o projeto.

## Como rodar o projeto

### Localmente sem Docker

- Java 11 JDK.
- Postgres.
- Maven.
- No terminal, na pasta do projeto, rodar o comando:
```shell
./mvnw clean package -DskipTests
```
- Na pasta ./target será criado o arquivo com extensão jar.
- Entre na pasta ./target e execute o comando:
```shell
java -jar {nome_do_arquivo_jar}
```

### Utilizando o Docker no terminal Bash (Script)

- Maven para a build e geração do arquivo jar do projeto.
- Docker.
- Docker compose.
- No terminal, na pasta do projeto, execute o script init.sh com o comando:
```shell
bash init.sh
```

### Utilizando o Docker manualmente

- Maven para a build e geração do arquivo jar do projeto.
- Docker.
- Docker compose.
- No terminal, na pasta do projeto, execute o script init.sh com o comando:
```shell
./mvnw clean package -DskipTests
```
- Na pasta ./target será criado o arquivo jar do projeto, faça uma cópia para a pasta do projeto:
```shell
cp ./target/{nome_do_arquivo_jar} ./
```
- Execute o comando do docker compose para gerar a imagem do projeto e criar os containers necessários para a aplicação rodar:
```shell
docker-compose up
```

## Rotas

### Cadastro de cliente

```shell
POST /client
```
```json
{
  "name": "Nome do cliente",
  "email": "cliente@email.com",
  "password": "123",
  "cpf": "99999999999",
  "rg": "888888888",
  "address": "Rua 123",
  "rent": 500
}
```
- Resposta de sucesso (status 201)
```json
{
  "id": 1,
  "name": "Nome do cliente",
  "email": "cliente@email.com",
  "cpf": "99999999999",
  "rg": "888888888",
  "address": "Rua 123",
  "rent": 500.0
}
```
- Em caso de erro, status 500

### Login do cliente

```shell
POST /login
```
```json
{
  "email": "cliente@email.com",
  "password": "123"
}
```
- Resposta de sucesso (status 200)
```json
{
  "access_token": "any_token"
}
```
- Em caso de erro, status 401 ou 500

### Solicitar empréstimo

```shell
POST /client/borrow
Header: Authentication Bearer token
```
```json
{
  "value": 1000,
  "firstInstallmentDate": "2022-01-30",
  "numberOfInstallments": 4
}
```
- Resposta de sucesso (status 200)
- Em caso de erro, status 403 ou 500

### Acompanhar empréstimo

#### Lista de empréstimos

```shell
GET /client/borrow
Header: Authentication Bearer token
```
- Resposta de sucesso (status 200)
```json
[
  {
    "id": 7,
    "code": "574864ea-505d-4465-973f-9708ce9df85c",
    "value": 1000.0,
    "firstInstallmentDate": "2022-01-29T21:00:00-03:00",
    "numberOfInstallments": 4,
    "numberOfInstallmentsPayed": 0,
    "remainingValue": 1000.0
  }
]
```
- Resposta de erro, status 403 ou 500

#### Listar um empréstimo

```shell
GET /client/borrow/:id
Header: Authentication Bearer token
```
- Resposta de sucesso (status 200)
```json
{
  "id": 7,
  "code": "574864ea-505d-4465-973f-9708ce9df85c",
  "value": 1000.0,
  "firstInstallmentDate": "2022-01-29T21:00:00-03:00",
  "numberOfInstallments": 4,
  "numberOfInstallmentsPayed": 0,
  "remainingValue": 1000.0
}
```
- Resposta de erro, status 403, 404 ou 500

### Rotas extras

#### Listar um cliente

```shell
GET /client/:id
Header: Authentication Bearer token
```
- Resposta de sucesso (status 200)
```json
{
  "id": 1,
  "name": "Nome do cliente",
  "email": "cliente@email.com",
  "cpf": "99999999999",
  "rg": "888888888",
  "address": "Rua",
  "rent": 500.0,
  "roles": [
    {
      "id": 4,
      "name": "ROLE_CLIENT"
    }
  ],
  "borrows": []
}
```
- Resposta de erro, status 403, 404 ou 500

#### Listar todos os clientes

```shell
GET /client
Header: Authentication Bearer token
```
- Resposta de sucesso (status 200)
```json
[
  {
    "id": 1,
    "name": "Nome do cliente",
    "email": "cliente@email.com",
    "cpf": "99999999999",
    "rg": "888888888",
    "address": "Rua",
    "rent": 500.0
  },
  {
    "id": 2,
    "name": "Nome do cliente",
    "email": "cliente@email.com",
    "cpf": "99999999999",
    "rg": "888888888",
    "address": "Rua",
    "rent": 500.0
  },
  {
    "id": 3,
    "name": "Nome do cliente",
    "email": "cliente@email.com",
    "cpf": "99999999999",
    "rg": "888888888",
    "address": "Rua",
    "rent": 500.0
  }
]
```
- Resposta de erro, status 403, 404 ou 500

#### Adicionar perfil de usuário

```shell
POST /client/role
Header: Authentication Bearer token
```
```json
{
  "name": "ROLE_MANAGER"
}
```
- Resposta de sucesso (status 200)
```json
{
  "id": 6,
  "name": "ROLE_MANAGER"
}
```
- Resposta de erro, status 403 ou 500

#### Adicionar perfil para um usuário cadastrado

```shell
POST /client/roleto
Header: Authentication Bearer token
```
```json
{
  "email": "cliente@email.com",
  "roleName": "ROLE_ADMIN"
}
```
- Resposta de sucesso (status 200)
- Resposta de erro, status 403 ou 500
