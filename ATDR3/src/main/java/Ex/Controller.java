package Ex;

import io.javalin.http.Context;
import Ex.User; // Importa a classe User

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Controller {
    // Lista estática para armazenar usuários em memória
    private static final List<User> users = new ArrayList<>();

    // Método que responde ao endpoint GET /hello com um texto simples
    public static void getHello(Context ctx) {
        ctx.status(200).result("Hello, Javalin!"); // Resposta HTTP 200 com texto simples
    }

    // Método que responde ao endpoint GET /status com um JSON contendo status e timestamp
    public static void getStatus(Context ctx) {
        ctx.json(new StatusResponse("ok", Instant.now().toString())); // Serializa objeto StatusResponse para JSON
    }

    // Método que recebe um JSON e retorna o mesmo objeto (echo)
    public static void postEcho(Context ctx) {
        try {
            EchoRequest request = ctx.bodyAsClass(EchoRequest.class); // Converte JSON da requisição para EchoRequest
            ctx.json(request); // Retorna o mesmo objeto em JSON
        } catch (Exception e) {
            ctx.status(400).json(new ErrorResponse("Invalid JSON format")); // Erro caso JSON inválido
        }
    }

    // Endpoint GET /saudacao/{nome} que retorna uma saudação personalizada
    public static void getSaudacao(Context ctx) {
        String nome = ctx.pathParam("nome"); // Obtém o parâmetro de caminho "nome"
        ctx.json(new SaudacaoResponse("Olá, " + nome + "!")); // Retorna mensagem de saudação no JSON
    }

    // Método para criar um usuário via POST /users
    public static void criarUsuario(Context ctx) {
        try {
            User user = ctx.bodyAsClass(User.class); // Converte JSON da requisição para User
            user.setId(UUID.randomUUID().toString()); // Gera um ID único para o usuário
            users.add(user); // Adiciona usuário na lista
            ctx.status(201).json(user); // Retorna usuário criado com status HTTP 201 Created
        } catch (Exception e) {
            ctx.status(400).json(new ErrorResponse("Invalid user data")); // Retorna erro para dados inválidos
        }
    }

    // Método que retorna todos os usuários cadastrados via GET /users
    public static void getUsuarios(Context ctx) {
        ctx.json(users); // Serializa lista de usuários para JSON
    }

    // Método para buscar usuário por ID via GET /users/{id}
    public static void getIdUsuario(Context ctx) {
        String id = ctx.pathParam("id"); // Obtém o parâmetro id da URL
        // Busca na lista o usuário com o id informado
        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (user != null) {
            ctx.json(user); // Retorna usuário encontrado
        } else {
            ctx.status(404).json(new ErrorResponse("User not found")); // Retorna erro 404 se não encontrado
        }
    }

    // Classe auxiliar para resposta de status
    private static class StatusResponse {
        private String status;
        private String timestamp;

        public StatusResponse(String status, String timestamp) {
            this.status = status;
            this.timestamp = timestamp;
        }

        public String getStatus() {
            return status;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }

    // Classe auxiliar para receber requisição echo
    private static class EchoRequest {
        private String mensagem;

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }

    // Classe auxiliar para resposta de saudação
    private static class SaudacaoResponse {
        private String mensagem;

        public SaudacaoResponse(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    // Classe auxiliar para resposta de erro
    private static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }

    public static String getHomePage() {
        return """
        <!DOCTYPE html>
        <html lang="pt-BR">
        <head>
            <meta charset="UTF-8">
            <title>API Javalin - Página Inicial</title>
        </head>
        <body>
            <h1>Bem-vindo à API Javalin</h1>
            <p>Veja os endpoints disponíveis abaixo:</p>
            <ul>
                <li><a href="/hello">GET /hello</a> - Retorna uma mensagem simples</li>
                <li><a href="/status">GET /status</a> - Mostra o status da API</li>
                <li>
                                 <form id="echoForm" onsubmit="enviarEcho(event)">
                                     <input type="text" id="mensagemInput" placeholder="Digite a mensagem" required>
                                     <button type="submit">POST /echo (enviar mensagem)</button>
                                 </form>   
                                 <script>
                                     function enviarEcho(event) {
                                         event.preventDefault(); // Evita o reload da página
                
                                         const mensagem = document.getElementById("mensagemInput").value;
                
                                         fetch("/echo", {
                                             method: "POST",
                                             headers: { "Content-Type": "application/json" },
                                             body: JSON.stringify({ mensagem })
                                         })
                                         .then(resp => resp.json())
                                         .then(data => alert("Mensagem recebida: " + data.mensagem))
                                         .catch(err => alert("Erro ao enviar echo"));
                                     }
                                 </script>
                             </li>
                
                <li><a href="/saudacao/Ademir">GET /saudacao/{nome}</a> - Saudação personalizada</li>       
                    <li>
                                       <button onclick="criarUsuario()">POST /tarefas (criar usuário padrão)</button>
                                       <script>
                                           function criarUsuario() {
                                               fetch("/tarefas", {
                                                   method: "POST",
                                                   headers: { "Content-Type": "application/json" },
                                                   body: JSON.stringify({
                                                       nome: "AdemirJR",
                                                       email: "Adm2Balas@example.com",
                                                       idade: 21
                                                   })
                                               })
                                               .then(resp => resp.json())
                                               .then(data => alert("Usuário criado: " + JSON.stringify(data)))
                                               .catch(err => alert("Erro ao criar usuário"));
                                           }
                                       </script>
                                   </li>
                <li><a href="/tarefas">GET /tarefas</a> - Lista todos os usuários</li>
                <li><a href="/tarefas/algum-id">GET /tarefas/{id}</a> - Busca usuário por ID</li>
            </ul>
        </body>
        </html>
        """;
    }

}
