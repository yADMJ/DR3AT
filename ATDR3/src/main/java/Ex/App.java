package Ex;

import io.javalin.Javalin;
import Ex.Controle;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.get("/", ctx -> {
            ctx.contentType("text/html");
            ctx.result(Controle.getHomePage());
        });
        app.get("/hello", Controle::getHello);
        app.get("/status", Controle::getStatus);
        app.post("/echo", Controle::postEcho);
        app.get("/saudacao/{nome}", Controle::getSaudacao);
        app.post("/tarefas", Controle::criarUsuario);
        app.get("/tarefas", Controle::getUsuarios);
        app.get("/tarefas/{id}", Controle::getIdUsuario);

        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).json(new ErrorResponse("Internal server error: " + e.getMessage()));
        });
    }

    private static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}