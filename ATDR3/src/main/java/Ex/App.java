package Ex;

import io.javalin.Javalin;
import Ex.Controller;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.get("/", ctx -> {
            ctx.contentType("text/html");
            ctx.result(Controller.getHomePage());
        });
        app.get("/hello", Controller::getHello);
        app.get("/status", Controller::getStatus);
        app.post("/echo", Controller::postEcho);
        app.get("/saudacao/{nome}", Controller::getSaudacao);
        app.post("/tarefas", Controller::criarUsuario);
        app.get("/tarefas", Controller::getUsuarios);
        app.get("/tarefas/{id}", Controller::getIdUsuario);

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
