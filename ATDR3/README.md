# API REST com Javalin - Controle de Usuários

Este projeto é uma API REST simples desenvolvida em Java com o framework [Javalin](https://javalin.io/), que gerencia usuários em memória e fornece endpoints para operações básicas.

## Funcionalidades
- **Endpoints RESTful**: Criar, consultar e recuperar tarefas via endpoints HTTP intuitivos.
- **Framework Javalin**: Framework leve e simples para lidar com requisições HTTP.
- **Testes Unitários**: Testes completos usando JUnit e REST Assured para garantir confiabilidade.
- **Suporte UTF-8**: Tratamento adequado da internacionalização com codificação consistente.
- **Documentação da API**: Endpoint de ajuda (`/`) que lista todos os endpoints disponíveis.

## Pré-requisitos
- **Java 21** ou superior
- **Gradle 8.5**



## Estrutura do projeto
```
├── .gitignore
├── build.gradle.kts
├── gradle
├── gradlew
├── gradlew.bat
├── README.md
├── settings.gradle.kts
├── src/
│   ├── main/
│   │   ├── java/ex/
│   │   │   ├── client/Cliente.java
│   │   │   ├── Controle.java
│   │   │   ├── App.java
│   │   │   ├── User.java
│   │  
│   │       
│   ├── test/
│   │   ├── java/ex/
│   │   │   ├── AppTest.java
```

## Endpoints
A API vai oferecer os seguintes endpoints, acessível por aqui:

| Método | Endpoint           | Descrição                                       |
|--------|--------------------|-------------------------------------------------|
| GET    | `/`                | Retorna uma pagina home onde os caminhos        |
| GET    | `/hello`           | Retorna uma mensagem simples "Hello, Javalin!". |
| GET    | `/status`          | Retorna o status da API e timestamp atual.      |
| POST   | `/echo`            | Retorna o mesmo JSON recebido (echo).           |
| GET    | `/saudacao/{nome}` | Retorna uma saudação personalizada.             |
| POST   | `/tarefas`         | Cria uma nova tarefa.                           |
| GET    | `/tarefas`         | Retorna todas as tarefas.                       |
| GET    | `/tarefas/{id}`    | Retorna uma tarefa pelo ID.                     |


**Exemplo Request (Create Task)**:

```bash
curl -Uri http://localhost:7000/tarefas -Method POST -Headers @{ "Content-Type" = "application/json" } -Body '{"nome":"Bagri","email":"BAgriPeixe@example.com","idade":2}'
```

**Exemplo de resposta**:
```json
{
  "id": "id gerado aleatoriamente",
  "nome": "Bagri",
  "email": "BAgriPeixe@example.com",
  "idade": 21
}
```

## Setup para rodar o projeto


1. **Build do Projeto**:
   ```bash
   ./gradlew build
   ```

2. **Rodar o projeto**:
   ```bash
   ./gradlew run
   ```

3. **Rodar teste**:
- lembre se que para rodar o teste execute build, não de run, porque os teste tem como iniciar o servidor automaticamente, então se você tentar rodar vai dar erro.
   ```bash
   ./gradlew test
   ```
  
4. **Rodar Cliente**
- Para rodar cliente execute run primeiro
   ```bash
   ./gradlew runCliente
   ```

## Tecnologias usadas
- **Java 21**: Core programming language.
- **Javalin 6.5.0**: Lightweight web framework.
- **Gradle 8.5**: Build automation tool.
- **JUnit 5.10.0 & REST Assured 5.5.0**: Testing frameworks.
- **Jackson 2.17.2**: JSON processing.

